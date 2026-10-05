package utils;

import java.util.Locale;

public class Platform {
    private static final String OS_NAME = System.getProperty("os.name").toLowerCase(Locale.ROOT);

    private Platform() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isWindows() {
        return OS_NAME.startsWith("windows");
    }

    public static boolean isMac() {
        return OS_NAME.startsWith("mac");
    }

    /**
     * @return true for Linux and other X11 based systems
     */
    public static boolean isUnix() {
        return !isWindows() && !isMac();
    }
}
