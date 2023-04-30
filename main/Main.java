package main;

import fileUtilities.ClickerData;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;

public class Main {
   public static void main(String[] args) {
      checkRunning();

      GUI gui = new GUI();
      new InputListener(gui);

      ClickerData data = new ClickerData();
   }

   /**
    * Checks if the program is already running by using internet port 1324.
    */
   private static void checkRunning() {
      try {
         new ServerSocket(1324);
      } catch (IOException e) {
         System.out.println("Program is already running. Exiting...");
         String message = "<html>Program is already running. Exiting...</html>";
         String title = "Already running";
         JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
         System.exit(1);
      }
   }
}