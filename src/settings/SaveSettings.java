package settings;

import utils.FileVisibility;
import utils.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveSettings {
    private static final String SETTING_FILE_NAME = "settings";


    /**
     * Tries to load data from settings file.
     */
    public static void initialize() {
        getSettings();
    }


    /**
     * Gets settings from the settings file.
     */
    private static void getSettings() {
        try {
            FileVisibility.changeVisibility(SETTING_FILE_NAME, false);

            FileInputStream fileIn = new FileInputStream(SETTING_FILE_NAME);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            SettingsObject settings = (SettingsObject) in.readObject();

            in.close();
            fileIn.close();
            settings.transferSettings();
            Logger.info("Settings loaded");
        } catch (Exception e) {
            Logger.warn("No compatible settings file found " + e);
        } finally {
            FileVisibility.changeVisibility(SETTING_FILE_NAME, true);
        }
    }

    /**
     * Saves settings to the settings file.
     */
    public static void saveSettings() {
        try {
            FileVisibility.changeVisibility(SETTING_FILE_NAME, false);

            FileOutputStream fileOut = new FileOutputStream(SETTING_FILE_NAME);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(new SettingsObject());

            out.close();
            fileOut.close();
            Logger.info("Settings saved");
        } catch (Exception e) {
            Logger.error("Settings could not be saved " + e);
        } finally {
            FileVisibility.changeVisibility(SETTING_FILE_NAME, true);
        }
    }
}
