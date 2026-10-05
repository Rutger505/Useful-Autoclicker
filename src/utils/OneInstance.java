package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

/**
 * Keeps a single running instance and lets later invocations send it commands,
 * so a compositor keybind (e.g. on Hyprland) can control the autoclicker.
 */
public class OneInstance {
    private static final int PORT = 1324;
    private static final int TIMEOUT_MS = 1000;
    private static ServerSocket server;

    private OneInstance() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Checks if the program is already running. Exits if it is.
     */
    public static void Activate() {
        Logger.trace("Activating OneInstance");
        if (isRunning()) {
            Logger.showError("Program is already running. Exiting...");
            Logger.fatal("Program is already running. Exiting...");
            System.exit(1);
        }
        Logger.info("Socket opened");
    }

    private static boolean isRunning() {
        try {
            server = new ServerSocket(PORT, 50, InetAddress.getLoopbackAddress());
            return false;
        } catch (IOException e) {
            return true;
        }
    }

    /**
     * Handles commands sent by {@link #sendCommand(String)} from other invocations.
     */
    public static void listen(Consumer<String> commandHandler) {
        Thread listener = new Thread(() -> {
            while (!server.isClosed()) {
                try (Socket client = server.accept()) {
                    client.setSoTimeout(TIMEOUT_MS);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8));
                    String command = reader.readLine();
                    if (command != null) {
                        Logger.info("Received command: " + command.trim());
                        commandHandler.accept(command.trim());
                    }
                } catch (IOException e) {
                    Logger.error("Failed reading command " + e);
                }
            }
        }, "command-listener");
        listener.setDaemon(true);
        listener.start();
    }

    /**
     * Sends a command to the running instance.
     *
     * @return false if no instance is running
     */
    public static boolean sendCommand(String command) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(InetAddress.getLoopbackAddress(), PORT), TIMEOUT_MS);
            OutputStream out = socket.getOutputStream();
            out.write((command + "\n").getBytes(StandardCharsets.UTF_8));
            out.flush();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
