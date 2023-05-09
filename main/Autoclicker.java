package main;

import GUI.GUI;
import fileUtilities.ClickerData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Random;

import static java.lang.Math.abs;

public class Autoclicker extends Thread {
   // settings
   private int clicks;
   private long clickDelay;
   private long holdDelay;

   private boolean shouldRandomizeClick;
   private boolean shouldRandomizeHold;

   private int clickRandomizeRange;
   private int holdRandomizeRange;

   private int button;

   // functional vars
   private long holdDelayOriginal;
   private long clickDelayOriginal;
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

      if (clicks == 0) {
         while (!Thread.interrupted()) {
            randomizeDelay();

            clickCycle();
         }
      } else {
         for (int i = 0; i < clicks && !Thread.interrupted(); i++) {
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
      if (shouldRandomizeClick) {
         clickDelay = abs(clickDelayOriginal + random.nextInt(clickRandomizeRange * 2) - clickRandomizeRange);
      }
      if (shouldRandomizeHold) {
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
      waitMs(holdDelay);
      mouseRelease();
      waitMs(clickDelay);
   }

   /**
    * Press mouse button
    */
   private void mousePress() {
      try {
         robot.mousePress(button);
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
         robot.mouseRelease(button);
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
      JTextField[] clickDelayTF = gui.getClickDelay();
      JTextField[] holdDelayTF = gui.getHoldDelay();
      int[] clickDelayRaw = new int[clickDelayTF.length];
      int[] holdDelayRaw = new int[holdDelayTF.length];
      for (int i = 0; i < clickDelayRaw.length; i++) {
         clickDelayRaw[i] = advancedParseInt(clickDelayTF[i].getText());
         holdDelayRaw[i] = advancedParseInt(holdDelayTF[i].getText());
      }
      clickDelay = 0;
      clickDelay += clickDelayRaw[3] * 3_600_000L;
      clickDelay += clickDelayRaw[2] * 60_000L;
      clickDelay += clickDelayRaw[1] * 1_000L;
      clickDelay += clickDelayRaw[0];
      clickDelayOriginal = clickDelay;
      holdDelay = 0;
      holdDelay += holdDelayRaw[3] * 3_600_000L;
      holdDelay += holdDelayRaw[2] * 60_000L;
      holdDelay += holdDelayRaw[1] * 1_000L;
      holdDelay += holdDelayRaw[0];
      holdDelayOriginal = holdDelay;

      // prevent crashing or lagging
      if (clickDelay < 1) {
         clickDelay = 1;
      }

      JCheckBox[] shouldRandomizeRaw = gui.getShouldRandomize();
      shouldRandomizeClick = shouldRandomizeRaw[0].isSelected();
      shouldRandomizeHold = shouldRandomizeRaw[1].isSelected();

      JTextField[] randomizeRangeRaw = gui.getRandomizeRange();
      clickRandomizeRange = advancedParseInt(randomizeRangeRaw[0].getText());
      holdRandomizeRange = advancedParseInt(randomizeRangeRaw[1].getText());

      clicks = advancedParseInt(gui.getClickAmount().getText());

      int buttonNumber = gui.getButtonSelect().getSelectedIndex() + 1;
      if (buttonNumber == 2) {
         buttonNumber = 3;
      } else if (buttonNumber == 3) {
         buttonNumber = 2;
      }
      button = InputEvent.getMaskForButton(buttonNumber);

      new ClickerData().saveClickerSettings(clickDelayRaw, holdDelayRaw, shouldRandomizeClick, shouldRandomizeHold, clickRandomizeRange, holdRandomizeRange, buttonNumber , clicks);
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