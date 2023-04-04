package main;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
   public static void main(String[] args) {
      checkRunning();

      GUI gui = new GUI();
      new InputListener(gui);
   }

   /**
    * Checks if the program is already running by using internet port 1324.
    */
   private static void checkRunning() {
      try {
         new ServerSocket(1324);
      } catch (IOException e) {
         System.exit(0);
      }
   }
}