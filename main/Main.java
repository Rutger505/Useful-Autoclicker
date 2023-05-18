package main;

import GUI.GUI;
import fileUtilities.ClickerData;

public class Main {
   /**
    * Starts the program
    * @param args command line arguments?
    */
   public static void main(String[] args) {
      new CheckRunning();
      new ClickerData();

      GUI gui = new GUI();
      new InputListener(gui);
   }
}