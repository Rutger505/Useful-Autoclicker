package utils;

/**
 * Presses mouse buttons without AWT's Robot, which on Wayland only reaches XWayland windows.
 */
public interface VirtualMouse {
    String WAYLAND_IMPLEMENTATION = "wayland.WaylandPointer";

    /**
     * @param buttonNumber button in GUI order: left, right, middle, side front, side back
     */
    void press(int buttonNumber);

    void release(int buttonNumber);

    /**
     * @return a Wayland virtual pointer, or null when not in a Wayland session or the compositor doesn't support it
     */
    static VirtualMouse createForSession() {
        if (System.getenv("WAYLAND_DISPLAY") == null) {
            return null;
        }
        // compiled for Java 17 (Unix domain sockets), so only load it by name to keep the rest running on Java 8
        if (System.getProperty("java.specification.version").startsWith("1.")) {
            Logger.warn("Java 17 or newer is needed to click in Wayland apps");
            return null;
        }
        try {
            return (VirtualMouse) Class.forName(WAYLAND_IMPLEMENTATION).getMethod("connect").invoke(null);
        } catch (ReflectiveOperationException e) {
            Logger.warn("Wayland virtual pointer unavailable, clicks only reach XWayland windows: " + e.getCause());
            return null;
        }
    }
}
