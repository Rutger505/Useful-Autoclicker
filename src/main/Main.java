package main;

import GUI.GUI;
import fileUtilities.ClickerData;
import fileUtilities.FileVisibility;
import fileUtilities.RegisterApp;

public class Main {
    /**
     * Starts the program
     *
     * @param args command line arguments?
     */
    public static void main(String[] args) {

        OneInstance.Activate();
        ClickerData.initialize();

        RegisterApp.RegisterApp();

        GUI gui = new GUI();
        new InputListener(gui);
    }
}