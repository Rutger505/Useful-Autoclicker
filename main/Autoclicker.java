package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Random;

import static java.lang.Math.abs;

public class Autoclicker extends Thread {
   // ## settings ##
   private int clicks = 0;
   private long clickDelay = 0;
   private long holdDelay = 0;

   private boolean clickShouldRandomize = false;
   private boolean holdShouldRandomize = false;

   private int clickRandomizeRange = 0;
   private int holdRandomizeRange = 0;

   private int button;

   // functional vars
   private long holdDelayOriginal;
   private long clickDelayOriginal;
   private final GUI gui;
   private final Random random = new Random();
   private Robot robot;
   private boolean running = false;

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
    * gets settings from GUI
    */
   private void getSettings() {
      JTextField[] clickDelayRaw = gui.getClickDelay();
      clickDelay = 0;
      clickDelay += advancedParseLong(clickDelayRaw[3].getText()) * 3_600_000;
      clickDelay += advancedParseLong(clickDelayRaw[2].getText()) * 60_000;
      clickDelay += advancedParseLong(clickDelayRaw[1].getText()) * 1_000;
      clickDelay += advancedParseLong(clickDelayRaw[0].getText());
      clickDelayOriginal = clickDelay;

      JTextField[] holdDelayRaw = gui.getHoldDelay();
      holdDelay = 0;
      holdDelay += advancedParseLong(holdDelayRaw[3].getText()) * 3_600_000;
      holdDelay += advancedParseLong(holdDelayRaw[2].getText()) * 60_000;
      holdDelay += advancedParseLong(holdDelayRaw[1].getText()) * 1_000;
      holdDelay += advancedParseLong(holdDelayRaw[0].getText());
      holdDelayOriginal = holdDelay;

      JCheckBox[] shouldRandomizeRaw = gui.getShouldRandomize();
      clickShouldRandomize = shouldRandomizeRaw[0].isSelected();

      holdShouldRandomize = shouldRandomizeRaw[1].isSelected();

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

      button = MouseEvent.getMaskForButton(buttonNumber);
   }


   /**
    * Stop Autoclicker
    */
   public void stopClicker() {
      this.interrupt();
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
         while (true) {
            randomizeDelay();

            try {
               clickCycle();
            } catch (InterruptedException e) {
               this.interrupt();
               break;
            }
         }
      } else {
         for (int i = 0; i < clicks; i++) {
            randomizeDelay();

            try {
               clickCycle();
            } catch (InterruptedException e) {
               this.interrupt();
               break;
            }
         }
      }
      running = false;
      gui.setTitle(GUI.MAIN_FRAME_TITLE + "  -  Stopped");
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
   private void clickCycle() throws InterruptedException {
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
    * Parses string to long returns 0 if not possible
    *
    * @param input String to parse
    * @return a parsed long or 0 depending on if input is parsable.
    */
   private long advancedParseLong(String input) {
      try {
         return Long.parseLong(input);
      } catch (NumberFormatException e) {
         return 0;
      }
   }

   /**
    * Sleep
    *
    * @param ms how many ms to sleep if less than 0 set to 0.
    */
   public void waitMs(long ms) throws InterruptedException {
      Thread.sleep(ms);
   }
}