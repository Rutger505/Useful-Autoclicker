package main;

import GUI.GUI;
import settings.Settings;

import java.awt.*;
import java.util.Random;

import static java.lang.Math.abs;

public class Autoclicker extends Thread {
   private final GUI gui;
   private final Random random = new Random();
   private Robot robot;
   private boolean running;

   /**
    * Sets up robot
    *
    * @param gui GUI object for getting settings
    */
   public Autoclicker(GUI gui) {
      this.gui = gui;
      try {
         robot = new Robot();
      } catch (AWTException ignored) {
      }
   }

   /**
    * Return if program is running
    *
    * @return if program is running
    */
   public boolean isRunning() {
      return running;
   }

   /**
    * Stop Autoclicker
    */
   public void stopClicker() {
      interrupt();
   }

   /**
    * Driver method
    */
   @Override
   public void run() {
      running = true;
      gui.setTitle(GUI.MAIN_FRAME_TITLE + "  -  Clicking");

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
      gui.setTitle(GUI.MAIN_FRAME_TITLE + "  -  Stopped");
   }

   /**
    * Randomizes delay of click and hold delay.
    */
   private void randomizeDelay() {
      if (Settings.shouldRandomizeClick()) {
         Settings.setClickDelay(abs(Settings.getClickDelayOriginal() + random.nextInt(Settings.getClickRandomizeRange() * 2) - Settings.getClickRandomizeRange()));
      }
      if (Settings.shouldRandomizeHold()) {
         Settings.setHoldDelay(abs(Settings.getHoldDelayOriginal() + random.nextInt(Settings.getHoldRandomizeRange() * 2) - Settings.getHoldRandomizeRange()));
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
    * Parses string to int returns 0 if not possible
    *
    * @param input String to parse
    * @return a parsed int or 0 depending on if input is parsable.
    */
   private int advancedParseInt(String input) {
      try {
         return Integer.parseInt(input);
      } catch (NumberFormatException e) {
         return 0;
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