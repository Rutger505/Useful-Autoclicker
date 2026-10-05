package utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class StartMenuShortcut {
    private static final String ICON_RESOURCE = "/resources/icon.png";
    private static final String DESKTOP_ENTRY = "applications/useful-autoclicker.desktop";

    private StartMenuShortcut() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Creates a start menu shortcut on Windows or a desktop entry on Linux pointing at the running jar.
     */
    public static void createStartMenuShortcut() {
        Logger.trace("Making start menu shortcut");

        Path jar = AppPaths.jarFile();
        if (jar == null) {
            Logger.info("Not running from a jar, skipping start menu shortcut");
            return;
        }

        try {
            if (Platform.isWindows()) {
                createWindowsShortcut(jar);
            } else if (Platform.isUnix()) {
                createDesktopEntry(jar);
            }
        } catch (Exception e) {
            Logger.error("Failed making start menu shortcut " + e);
        }
    }

    private static void createWindowsShortcut(Path jar) throws IOException {
        String shortcutLocation = System.getProperty("user.home") + "\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Useful-Autoclicker.lnk";
        ShortcutFactory.createShortcut(jar.toString(), shortcutLocation);
        Logger.info("Created shortcut in start menu");
    }

    private static void createDesktopEntry(Path jar) throws IOException {
        for (Path dataDir : AppPaths.xdgDataDirs()) {
            if (Files.exists(dataDir.resolve(DESKTOP_ENTRY))) {
                Logger.info("Desktop entry installed by package manager, skipping");
                return;
            }
        }

        Path icon = AppPaths.dataDir().resolve("icon.png");
        Files.createDirectories(icon.getParent());
        try (InputStream in = StartMenuShortcut.class.getResourceAsStream(ICON_RESOURCE)) {
            Files.copy(in, icon, StandardCopyOption.REPLACE_EXISTING);
        }

        String entry = "[Desktop Entry]\n"
                + "Type=Application\n"
                + "Name=Useful Autoclicker\n"
                + "Comment=A versatile Autoclicker for various applications\n"
                + "Exec=java -jar " + quoteExecArgument(jar.toString()) + "\n"
                + "Icon=" + icon + "\n"
                + "Categories=Utility;\n"
                + "Terminal=false\n";

        Path desktopEntry = AppPaths.xdgDataHome().resolve(DESKTOP_ENTRY);
        Files.createDirectories(desktopEntry.getParent());
        Files.write(desktopEntry, entry.getBytes(StandardCharsets.UTF_8));
        Logger.info("Created desktop entry " + desktopEntry);
    }

    /**
     * Quotes an argument for the Exec key as described in the Desktop Entry spec.
     */
    private static String quoteExecArgument(String argument) {
        String escaped = argument.replaceAll("([\"`$\\\\])", "\\\\$1");
        return ("\"" + escaped + "\"").replace("\\", "\\\\").replace("%", "%%");
    }
}
