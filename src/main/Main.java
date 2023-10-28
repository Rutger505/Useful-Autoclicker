package main;

import GUI.GUI;
import fileUtilities.ClickerData;
import fileUtilities.RegisterApp;

import java.util.Arrays;

public class Main {
    /**
     * Starts the program
     *
     * @param args command line arguments?
     */
    public static void main(String[] args) {

        Logger.trace("Activating OneInstance");
        OneInstance.Activate();
        Logger.trace("Getting Settings from file");
        ClickerData.initialize();

        Logger.trace("Making start menu shortcut");
        new RegisterApp();

        Logger.trace("Starting GUI");
        GUI gui = new GUI();
        
        Logger.trace("Starting InputListener");
        new InputListener(gui);
    }
}