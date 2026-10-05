package settings;

import utils.AppPaths;
import utils.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

public class SaveSettings {
    private static final Path SETTINGS_FILE = AppPaths.configDir().resolve("settings.properties");
    private static final Path TEMPORARY_FILE = SETTINGS_FILE.resolveSibling("settings.properties.tmp");

    private SaveSettings() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Loads settings from the settings file, keeping defaults for anything missing.
     */
    public static void initialize() {
        Logger.trace("Getting Settings from " + SETTINGS_FILE);
        if (!Files.exists(SETTINGS_FILE)) {
            Logger.info("No settings file yet, using defaults");
            return;
        }

        try (InputStream in = Files.newInputStream(SETTINGS_FILE)) {
            Properties properties = new Properties();
            properties.load(in);
            Settings.getInstance().load(properties);
            Logger.info("Settings loaded");
        } catch (IOException | IllegalArgumentException e) {
            Logger.warn("Settings file could not be read, using defaults " + e);
        }
    }

    /**
     * Saves settings to the settings file.
     */
    public static synchronized void saveSettings() {
        try {
            Files.createDirectories(SETTINGS_FILE.getParent());
            try (OutputStream out = Files.newOutputStream(TEMPORARY_FILE)) {
                Settings.getInstance().toProperties().store(out, "Useful Autoclicker settings");
            }
            // write-then-rename so a crash mid-write never leaves a truncated settings file behind
            Files.move(TEMPORARY_FILE, SETTINGS_FILE, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);

            Logger.info("Settings saved");
        } catch (IOException e) {
            Logger.error("Settings could not be saved " + e);
        }
    }
}
