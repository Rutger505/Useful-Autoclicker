package wayland;

import utils.Logger;
import utils.VirtualMouse;

import java.io.EOFException;
import java.io.IOException;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

/**
 * Minimal Wayland client for the wlr-virtual-pointer protocol, supported by Hyprland, Sway and other wlroots compositors.
 * Speaks the wire protocol directly, it only needs a few requests and no file descriptor passing.
 */
public final class WaylandPointer implements VirtualMouse {
    private static final String MANAGER_INTERFACE = "zwlr_virtual_pointer_manager_v1";

    private static final int DISPLAY_ID = 1;

    private static final int DISPLAY_SYNC = 0;
    private static final int DISPLAY_GET_REGISTRY = 1;
    private static final int DISPLAY_ERROR_EVENT = 0;
    private static final int REGISTRY_BIND = 0;
    private static final int REGISTRY_GLOBAL_EVENT = 0;
    private static final int MANAGER_CREATE_VIRTUAL_POINTER = 0;
    private static final int POINTER_BUTTON = 2;
    private static final int POINTER_FRAME = 4;

    // linux/input-event-codes.h, in GUI order: left, right, middle, side front, side back
    private static final int[] BUTTONS = {0x110, 0x111, 0x112, 0x113, 0x114};
    private static final int RELEASED = 0;
    private static final int PRESSED = 1;

    private final SocketChannel channel;
    // libwayland rejects new ids that skip ahead, so every object takes the next one in creation order
    private int nextId = DISPLAY_ID + 1;
    private int registryId;
    private int pointerId;
    private volatile boolean broken;

    private WaylandPointer(SocketChannel channel) {
        this.channel = channel;
    }

    public static WaylandPointer connect() throws IOException {
        SocketChannel channel = SocketChannel.open(StandardProtocolFamily.UNIX);
        try {
            channel.connect(UnixDomainSocketAddress.of(socketPath()));
            WaylandPointer pointer = new WaylandPointer(channel);
            pointer.createPointer();
            pointer.startEventReader();
            Logger.info("Clicking through the Wayland virtual pointer");
            return pointer;
        } catch (IOException | RuntimeException e) {
            channel.close();
            throw e;
        }
    }

    private static Path socketPath() {
        String display = System.getenv("WAYLAND_DISPLAY");
        Path path = Path.of(display);
        return path.isAbsolute() ? path : Path.of(System.getenv("XDG_RUNTIME_DIR"), display);
    }

    @Override
    public void press(int buttonNumber) {
        sendButton(buttonNumber, PRESSED);
    }

    @Override
    public void release(int buttonNumber) {
        sendButton(buttonNumber, RELEASED);
    }

    private synchronized void sendButton(int buttonNumber, int state) {
        if (broken) {
            return;
        }
        int time = (int) (System.nanoTime() / 1_000_000);
        try {
            write(new Message(pointerId, POINTER_BUTTON).uint(time).uint(BUTTONS[buttonNumber]).uint(state),
                    new Message(pointerId, POINTER_FRAME));
        } catch (IOException e) {
            markBroken("Lost the Wayland connection " + e);
        }
    }

    private void createPointer() throws IOException {
        registryId = nextId++;
        write(new Message(DISPLAY_ID, DISPLAY_GET_REGISTRY).uint(registryId));
        int managerName = findGlobal(MANAGER_INTERFACE);
        if (managerName < 0) {
            throw new IOException("the compositor doesn't support " + MANAGER_INTERFACE);
        }
        int managerId = nextId++;
        pointerId = nextId++;
        write(new Message(registryId, REGISTRY_BIND).uint(managerName).string(MANAGER_INTERFACE).uint(1).uint(managerId),
                // a null seat lets the compositor pick its default seat
                new Message(managerId, MANAGER_CREATE_VIRTUAL_POINTER).uint(0).uint(pointerId));
        // round trip so protocol errors surface here instead of on the first click
        roundTrip(null);
    }

    /**
     * @return the registry name of the global, or -1 if the compositor doesn't advertise it
     */
    private int findGlobal(String wantedInterface) throws IOException {
        int[] found = {-1};
        roundTrip(event -> {
            if (event.objectId == registryId && event.opcode == REGISTRY_GLOBAL_EVENT) {
                int name = event.body.getInt();
                if (wantedInterface.equals(readString(event.body))) {
                    found[0] = name;
                }
            }
        });
        return found[0];
    }

    private void roundTrip(EventHandler handler) throws IOException {
        int callbackId = nextId++;
        write(new Message(DISPLAY_ID, DISPLAY_SYNC).uint(callbackId));
        while (true) {
            Event event = readEvent();
            throwIfError(event);
            if (event.objectId == callbackId) {
                return;
            }
            if (handler != null) {
                handler.handle(event);
            }
        }
    }

    /**
     * Keeps reading so the socket buffer never fills up and protocol errors get logged.
     */
    private void startEventReader() {
        Thread reader = new Thread(() -> {
            try {
                while (true) {
                    throwIfError(readEvent());
                }
            } catch (IOException e) {
                markBroken("Wayland virtual pointer stopped working " + e.getMessage());
            }
        }, "wayland-events");
        reader.setDaemon(true);
        reader.start();
    }

    private void markBroken(String message) {
        if (!broken) {
            broken = true;
            Logger.error(message);
        }
    }

    private static void throwIfError(Event event) throws IOException {
        if (event.objectId == DISPLAY_ID && event.opcode == DISPLAY_ERROR_EVENT) {
            int objectId = event.body.getInt();
            int code = event.body.getInt();
            throw new IOException("Wayland error on object " + objectId + " code " + code + ": " + readString(event.body));
        }
    }

    private void write(Message... messages) throws IOException {
        int size = 0;
        for (Message message : messages) {
            size += message.size();
        }
        ByteBuffer buffer = ByteBuffer.allocate(size).order(ByteOrder.nativeOrder());
        for (Message message : messages) {
            message.writeTo(buffer);
        }
        buffer.flip();
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
    }

    private Event readEvent() throws IOException {
        ByteBuffer header = readFully(8);
        int objectId = header.getInt();
        int sizeAndOpcode = header.getInt();
        return new Event(objectId, sizeAndOpcode & 0xffff, readFully((sizeAndOpcode >>> 16) - 8));
    }

    private ByteBuffer readFully(int size) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(size).order(ByteOrder.nativeOrder());
        while (buffer.hasRemaining()) {
            if (channel.read(buffer) < 0) {
                throw new EOFException("compositor closed the connection");
            }
        }
        return buffer.flip();
    }

    private static String readString(ByteBuffer body) {
        int length = body.getInt();
        byte[] bytes = new byte[length - 1];
        body.get(bytes);
        body.position(body.position() + padding(length) + 1);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private static int padding(int length) {
        return (4 - length % 4) % 4;
    }

    private interface EventHandler {
        void handle(Event event);
    }

    private record Event(int objectId, int opcode, ByteBuffer body) {
    }

    private static final class Message {
        private final int objectId;
        private final int opcode;
        private final ByteBuffer body = ByteBuffer.allocate(256).order(ByteOrder.nativeOrder());

        Message(int objectId, int opcode) {
            this.objectId = objectId;
            this.opcode = opcode;
        }

        Message uint(int value) {
            body.putInt(value);
            return this;
        }

        Message string(String value) {
            byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
            int length = bytes.length + 1;
            body.putInt(length).put(bytes).put((byte) 0);
            body.position(body.position() + padding(length));
            return this;
        }

        int size() {
            return 8 + body.position();
        }

        void writeTo(ByteBuffer buffer) {
            buffer.putInt(objectId).putInt(size() << 16 | opcode).put(body.duplicate().flip());
        }
    }
}
