package main;

import errorHandeling.Error;

import java.io.IOException;
import java.net.ServerSocket;

public class CheckRunning {
   private static final int port = 1324;

   /**
    * Checks if the program is already running. Exits if it is.
    */
   public CheckRunning() {
      if (isRunning()){
         Error.showError("Aleady running", "Program is already running. Exiting...", "Program is already running. Exiting...");
         System.exit(1);
      }
   }

   /**
    * Checks if the program is already running by using an internet port.
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
