package main;

import GUI.GUI;

public class Main {
   /**
    * Starts the program
    * @param args command line arguments?
    */
   public static void main(String[] args) {
      new CheckRunning();

      GUI gui = new GUI();
      new InputListener(gui);
   }
}