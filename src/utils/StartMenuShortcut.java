package utils;

public class StartMenuShortcut {

    private StartMenuShortcut() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Creates a shortcut in the start menu
     */
    public static void createStartMenuShortcut() {
        Logger.trace("Making start menu shortcut");

        try {
            String applicationFileName = "Useful-Autoclicker.jar";
            String shortcutFileName = "Useful-Autoclicker.lnk";

            String shortcutTarget = ApplicationDirectory.getApplicationDirectory() + applicationFileName;
            String shortcutLocation = System.getProperty("user.home") + "\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\" + shortcutFileName;


            ShortcutFactory.createShortcut(shortcutTarget, shortcutLocation);

            Logger.info("Created shortcut in start menu");
        } catch (Exception e) {
            Logger.error("Failed making start menu shortcut " + e);
        }
    }
}
