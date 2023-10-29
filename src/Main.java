import GUI.GUI;
import settings.SaveSettings;
import utils.Logger;
import utils.OneInstance;
import utils.StartMenuShortcut;

public class Main {
    /**
     * Starts the program
     *
     * @param args command line arguments?
     */
    public static void main(String[] args) {

        // CLASSES REORDER

        Logger.trace("Activating OneInstance");
        OneInstance.Activate();
        Logger.trace("Getting Settings from file");
        SaveSettings.initialize();

        Logger.trace("Making start menu shortcut");
        new StartMenuShortcut();

        Logger.trace("Starting GUI");
        GUI gui = new GUI();

        Logger.trace("Starting InputListener");
        new InputListener(gui);
    }
}