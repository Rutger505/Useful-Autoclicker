import GUI.GUI;
import settings.SaveSettings;
import utils.Logger;
import utils.OneInstance;
import utils.StartMenuShortcut;

import static utils.ApplicationDirectory.getApplicationDirectory;

public class Main {
    /**
     * Starts the program
     *
     * @param args command line arguments?
     */
    public static void main(String[] args) {

        OneInstance.Activate();
        SaveSettings.initialize();
        StartMenuShortcut.createStartMenuShortcut();

        GUI gui = new GUI();

        new InputListener(gui);
    }
}