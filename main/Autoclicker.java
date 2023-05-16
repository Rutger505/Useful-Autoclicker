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

      getSettings();

      if (Settings.clicks == 0) {
         while (!Thread.interrupted()) {
            randomizeDelay();

            clickCycle();
         }
      } else {
         for (int i = 0; i < Settings.clicks && !Thread.interrupted(); i++) {
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
      if (Settings.shouldRandomizeClick) {
         Settings.clickDelay = abs(Settings.clickDelayOriginal + random.nextInt(Settings.clickRandomizeRange * 2) - Settings.clickRandomizeRange);
      }
      if (Settings.shouldRandomizeHold) {
         Settings.holdDelay = abs(Settings.holdDelayOriginal + random.nextInt(Settings.holdRandomizeRange * 2) - Settings.holdRandomizeRange);
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
      waitMs(Settings.holdDelay);
      mouseRelease();
      waitMs(Settings.clickDelay);
   }

   /**
    * Press mouse button
    */
   private void mousePress() {
      try {
         robot.mousePress(Settings.button);
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
         robot.mouseRelease(Settings.button);
      } catch (RuntimeException e) {
         try {
            robot = new Robot();
         } catch (AWTException ignored) {
         }
         System.out.println("error in mouse release");
      }
   }

   /**
    * Gets settings from GUI and saves them
    */
   private void getSettings() {
      int[] clickDelayRaw = new int[4];
      int[] holdDelayRaw = new int[4];
      for (int i = 0; i < clickDelayRaw.length; i++) {
         clickDelayRaw[i] = advancedParseInt(gui.getClickDelay()[i].getText());
         holdDelayRaw[i] = advancedParseInt(gui.getHoldDelay()[i].getText());
      }
      Settings.toMsClickDelay(clickDelayRaw);
      Settings.toMsHoldDelay(holdDelayRaw);

      Settings.clickRandomizeRange = advancedParseInt(gui.getRandomizeRange()[0].getText());
      Settings.holdRandomizeRange = advancedParseInt(gui.getRandomizeRange()[1].getText());

      Settings.clicks = advancedParseInt(gui.getClickAmount().getText());
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