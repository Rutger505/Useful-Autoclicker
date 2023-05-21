package main;

import settings.Settings;
import errorHandeling.Error;

import java.awt.*;
import java.util.Random;

public class Autoclicker extends Thread {
   private final Random random = new Random();
   private Robot robot;
   private boolean running;

   /**
    * Sets up robot
    */
   public Autoclicker() {
      try {
         robot = new Robot();
      } catch (AWTException e) {
         Error.showError("Error starting Autoclicker", "Error starting Autoclicker try restarting the autoclicker", "(Autoclicker) Error creating robot");
      }
   }

   /**
    * @return if program is running
    */
   public boolean isRunning() {
      return running;
   }

   /**
    * Stop Autoclicker
    */
   public void stopClicker() {
      this.interrupt();
   }

   /**
    * Driver method
    */
   @Override
   public void run() {
      running = true;

      if (Settings.getClicks() == 0) {
         while (!Thread.interrupted()) {
            randomizeDelay();

            clickCycle();
         }
      } else {
         for (int i = 0; i < Settings.getClicks() && !Thread.interrupted(); i++) {
            randomizeDelay();

            clickCycle();
         }
      }
      running = false;
   }

   /**
    * Randomizes delay of click and hold delay.
    */
   private void randomizeDelay() {
      try {
         if (Settings.shouldRandomizeClick()) {
            Settings.setClickDelay(Math.abs(Settings.getClickDelayOriginal() + random.nextInt(Settings.getClickRandomizeRange() * 2) - Settings.getClickRandomizeRange()));
         }
      } catch (IllegalArgumentException e) {
         Settings.setShouldRandomizeClick(false);
      }

      try {
         if (Settings.shouldRandomizeHold()) {
            Settings.setHoldDelay(Math.abs(Settings.getHoldDelayOriginal() + random.nextInt(Settings.getHoldRandomizeRange() * 2) - Settings.getHoldRandomizeRange()));
         }
      } catch (IllegalArgumentException e) {
         Settings.setShouldRandomizeHold(false);
      }

   }

   /**
    * Does a full click cycle:
    * <p>
    * mouse press<br>
    * hold delay <br>
    * mouse release <br>
    * click delay
    */
   private void clickCycle() {
      mousePress();
      waitMs(Settings.getHoldDelay());
      mouseRelease();
      waitMs(Settings.getClickDelay());
   }

   /**
    * Press mouse button
    */
   private void mousePress() {
      try {
         robot.mousePress(Settings.getButton());
      } catch (RuntimeException e) {
         try {
            robot = new Robot();
         } catch (AWTException ignored) {
         }
         System.out.println("error in mouse press");
      }
   }

   /**
    * Release mouse button
    */
   private void mouseRelease() {
      try {
         robot.mouseRelease(Settings.getButton());
      } catch (RuntimeException e) {
         try {
            robot = new Robot();
         } catch (AWTException ignored) {
         }
         System.out.println("error in mouse release");
      }
   }

   /**
    * Sleep
    *
    * @param ms how many ms to sleep if less than 0 set to 0.
    */
   private void waitMs(long ms) {
      try {
         Thread.sleep(ms);
      } catch (InterruptedException e) {
         interrupt();
      }
   }
}