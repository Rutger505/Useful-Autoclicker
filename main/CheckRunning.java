package main;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;

public class CheckRunning {
   private final int port = 1324;

   /**
    * Checks if the program is already running. Exits if it is.
    */
   public CheckRunning() {
      if (isRunning()){
         System.out.println("Program is already running. Exiting...");
         String message = "<html>Program is already running. Exiting...</html>";
         String title = "Already running";
         JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
         System.exit(1);
      }
   }

   /**
    * Checks if the program is already running by using a internet port.
    */
   private boolean isRunning() {
      try {
         new ServerSocket(port);
         return false;
      } catch (IOException e) {
         return true;
      }
   }
}
