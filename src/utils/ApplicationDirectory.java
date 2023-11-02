package utils;

import java.io.File;

public class ApplicationDirectory {

    private ApplicationDirectory() {
        throw new IllegalStateException("Utility class");
    }

    private static String applicationDirectory = null;

    public static String getApplicationDirectory() {
        if (applicationDirectory == null) {
            try {
                applicationDirectory = new File(StartMenuShortcut.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath() + "\\";
                Logger.info("Application directory: " + applicationDirectory);
            } catch (Exception e) {
                Logger.error("Failed getting application path " + e);
            }
        }

        return applicationDirectory;
    }
}
