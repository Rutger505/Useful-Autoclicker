package settings;

import utils.ApplicationDirectory;
import utils.FileVisibility;
import utils.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveSettings {
    private static final String SETTING_FILE_NAME = "settings";
    private static final String SETTING_FILE_ABSOLUTE_PATH = ApplicationDirectory.getApplicationDirectory() + SETTING_FILE_NAME;

    private SaveSettings() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Tries to load data from settings file.
     */
    public static void initialize() {
        Logger.trace("Getting Settings from file");
        getSettings();
        saveSettings(); // so the settings file is created if it doesn't exist yet.
    }


    /**
     * Gets settings from the settings file.
     */
    private static void getSettings() {
        try {
            FileVisibility.changeVisibility(SETTING_FILE_ABSOLUTE_PATH, false);

            FileInputStream fileIn = new FileInputStream(SETTING_FILE_ABSOLUTE_PATH);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Settings loadedSettings = (Settings) in.readObject();

            Settings.setNewInstance(loadedSettings);
            Logger.info("Settings loaded");
        } catch (Exception e) {
            Logger.warn("No compatible settings file found " + e);
        } finally {
            FileVisibility.changeVisibility(SETTING_FILE_ABSOLUTE_PATH, true);
        }
    }

    /**
     * Saves settings to the settings file.
     */
    public static void saveSettings() {
        try {
            FileVisibility.changeVisibility(SETTING_FILE_ABSOLUTE_PATH, false);

            FileOutputStream fileOut = new FileOutputStream(SETTING_FILE_ABSOLUTE_PATH);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(Settings.getInstance());

            Logger.info("Settings saved");
        } catch (Exception e) {
            Logger.error("Settings could not be saved " + e);
        } finally {
            FileVisibility.changeVisibility(SETTING_FILE_ABSOLUTE_PATH, true);
        }
    }
}
