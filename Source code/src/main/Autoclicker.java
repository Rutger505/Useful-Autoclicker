package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Random;

import static java.lang.Math.abs;

public class Autoclicker extends Thread {
   // ## settings ##
   private int clicks = 0;
   private int clickDelay = 0;
   private int holdDelay = 0;

   private boolean clickShouldRandomize = false;
   private boolean holdShouldRandomize = false;

   private int clickRandomizeRange = 0;
   private int holdRandomizeRange = 0;

   private int button;

   // functional vars
   private final GUI gui;
   private final Random random = new Random();
   private Robot robot;
   private boolean running = false;
   private boolean shouldStop = false;
   private int holdDelayOriginal;
   private int clickDelayOriginal;

   public Autoclicker(GUI gui) {
      this.gui = gui;
      try {
         robot = new Robot();
      } catch (AWTException ignored) {
      }
   }

   @Override
   public void run() {
      running = true;
      gui.setTitle(GUI.MAIN_FRAME_TITLE + "  -  Clicking");

      getSettings();

      // prevent crashing or lagging
      if (clickDelay < 1) {
         clickDelay = 1;
      }

      if (clicks == 0) {
         do {
            randomizeDelay();

            clickCycle();
         } while (!shouldStop);
      } else {
         for (int i = 0; i < clicks && !shouldStop; i++) {
            randomizeDelay();

            clickCycle();
         }
      }
      running = false;
      gui.setTitle(GUI.MAIN_FRAME_TITLE + "  -  Stopped");
   }

   /**
    * Stop Autoclicker
    */
   public void stopClicker() {
      shouldStop = true;
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
    * gets settings from GUI
    */
   private void getSettings() {
      JTextField[] clickDelayRaw = gui.getClickDelay();
      clickDelay = 0;
      clickDelay += advancedParseInt(clickDelayRaw[3].getText()) * 3_600_000;
      clickDelay += advancedParseInt(clickDelayRaw[2].getText()) * 60_000;
      clickDelay += advancedParseInt(clickDelayRaw[1].getText()) * 1_000;
      clickDelay += advancedParseInt(clickDelayRaw[0].getText());
      clickDelayOriginal = clickDelay;

      JTextField[] holdDelayRaw = gui.getHoldDelay();
      holdDelay = 0;
      holdDelay += advancedParseInt(holdDelayRaw[3].getText()) * 3_600_000;
      holdDelay += advancedParseInt(holdDelayRaw[2].getText()) * 60_000;
      holdDelay += advancedParseInt(holdDelayRaw[1].getText()) * 1_000;
      holdDelay += advancedParseInt(holdDelayRaw[0].getText());
      holdDelayOriginal = holdDelay;

      JCheckBox[] shouldRandomizeRaw = gui.getShouldRandomize();
      clickShouldRandomize = shouldRandomizeRaw[0].isSelected();

      holdShouldRandomize = shouldRandomizeRaw[1].isSelected();

      JTextField[] randomizeRangeRaw = gui.getRandomizeRange();
      clickRandomizeRange = advancedParseInt(randomizeRangeRaw[0].getText());
      holdRandomizeRange = advancedParseInt(randomizeRangeRaw[1].getText());

      clicks = advancedParseInt(gui.getClickAmount().getText());

      int buttonNumber = gui.getButtonSelect().getSelectedIndex() + 1;
      button = MouseEvent.getMaskForButton(buttonNumber);
   }

   /**
    * Randomizes delay of click and hold delay.
    */
   private void randomizeDelay() {
      if (clickShouldRandomize) {
         clickDelay = abs(clickDelayOriginal + random.nextInt(clickRandomizeRange * 2) - clickRandomizeRange);
      }
      if (holdShouldRandomize) {
         holdDelay = abs(holdDelayOriginal + random.nextInt(holdRandomizeRange * 2) - holdRandomizeRange);
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
      wait(holdDelay);
      mouseRelease();
      wait(clickDelay);
   }

   /**
    * Press mouse button
    */
   private void mousePress() {
      try {
         robot.mousePress(button);
      } catch (Exception e) {
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
         robot.mouseRelease(button);
      } catch (Exception e) {
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
   private void wait(int ms) {
      try {
         Thread.sleep(ms);
      } catch (InterruptedException ignored) {
      }

   }
}