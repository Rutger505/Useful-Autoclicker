package utils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Per-user directories following each platform's conventions:
 * %APPDATA% / %LOCALAPPDATA% on Windows, ~/Library on macOS and the XDG Base Directory spec elsewhere.
 */
public class AppPaths {
    private static final String WINDOWS_APP_NAME = "Useful-Autoclicker";
    private static final String UNIX_APP_NAME = "useful-autoclicker";
    private static final Path HOME = Paths.get(System.getProperty("user.home"));

    private AppPaths() {
        throw new IllegalStateException("Utility class");
    }

    public static Path configDir() {
        if (Platform.isWindows()) {
            return windowsDir("APPDATA", "AppData\\Roaming");
        }
        if (Platform.isMac()) {
            return HOME.resolve("Library/Application Support").resolve(WINDOWS_APP_NAME);
        }
        return xdgDir("XDG_CONFIG_HOME", ".config");
    }

    public static Path cacheDir() {
        if (Platform.isWindows()) {
            return windowsDir("LOCALAPPDATA", "AppData\\Local");
        }
        if (Platform.isMac()) {
            return HOME.resolve("Library/Caches").resolve(WINDOWS_APP_NAME);
        }
        return xdgDir("XDG_CACHE_HOME", ".cache");
    }

    public static Path xdgDataHome() {
        return xdgBaseDir("XDG_DATA_HOME", ".local/share");
    }

    public static Path dataDir() {
        return xdgDataHome().resolve(UNIX_APP_NAME);
    }

    /**
     * @return the jar the application is running from, or null when running from a class directory (e.g. the IDE)
     */
    public static Path jarFile() {
        try {
            File location = new File(AppPaths.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            return location.isFile() ? location.toPath() : null;
        } catch (Exception e) {
            Logger.error("Failed getting application path " + e);
            return null;
        }
    }

    private static Path windowsDir(String envVariable, String fallbackRelativeToHome) {
        String value = System.getenv(envVariable);
        Path base = value == null || value.isEmpty() ? HOME.resolve(fallbackRelativeToHome) : Paths.get(value);
        return base.resolve(WINDOWS_APP_NAME);
    }

    private static Path xdgDir(String envVariable, String fallbackRelativeToHome) {
        return xdgBaseDir(envVariable, fallbackRelativeToHome).resolve(UNIX_APP_NAME);
    }

    private static Path xdgBaseDir(String envVariable, String fallbackRelativeToHome) {
        String value = System.getenv(envVariable);
        // The spec says relative paths are invalid and must be ignored
        if (value != null && Paths.get(value).isAbsolute()) {
            return Paths.get(value);
        }
        return HOME.resolve(fallbackRelativeToHome);
    }
}
