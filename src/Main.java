import GUI.GUI;
import settings.SaveSettings;
import utils.AppPaths;
import utils.Logger;
import utils.OneInstance;
import utils.StartMenuShortcut;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    private static final String TOGGLE_ARGUMENT = "--toggle";

    /**
     * Starts the program, or with --toggle toggles the clicker of the already running instance.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            System.exit(runCommand(args[0]));
        }

        OneInstance.Activate();
        SaveSettings.initialize();
        StartMenuShortcut.createStartMenuShortcut();
        extractNativeLibraryToCache();

        GUI gui = new GUI();

        InputListener inputListener = new InputListener(gui);
        OneInstance.listen(inputListener::handleCommand);
    }

    private static int runCommand(String argument) {
        if (!TOGGLE_ARGUMENT.equals(argument)) {
            System.err.println("Usage: java -jar Useful-Autoclicker.jar [" + TOGGLE_ARGUMENT + "]");
            return 2;
        }
        if (!OneInstance.sendCommand(InputListener.TOGGLE_COMMAND)) {
            System.err.println("Useful Autoclicker is not running");
            return 1;
        }
        return 0;
    }

    /**
     * JNativeHook extracts its native library next to the jar by default, which may not be writable.
     */
    private static void extractNativeLibraryToCache() {
        Path cacheDir = AppPaths.cacheDir();
        try {
            Files.createDirectories(cacheDir);
            System.setProperty("jnativehook.lib.path", cacheDir.toString());
        } catch (IOException e) {
            Logger.error("Could not create cache directory " + cacheDir + " " + e);
        }
    }
}
