package fileUtilities;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;

public class ClickerData {

   private static final String FILE_NAME = "clickerData.txt";
   private final String dataPath;

   private int[] clickDelay = new int[4];
   private int[] holdDelay = new int[4];
   private boolean shouldRandomizeClickDelay;
   private boolean shouldRandomizeHoldDelay;
   private int randomizeClickDelayRange;
   private int randomizeHoldDelayRange;
   private int hotkeyCode;
   private int button;
   private int clicks;
   private boolean autoclickOnMouseHold;

   /**
    * Makes data file if it doesn't exist
    * and gets settings from a file.
    */
   public ClickerData() {
      // get data path
      File windowsDrive = getWindowsDrive();
      String user = System.getProperty("user.name");
      dataPath = windowsDrive + "Users/" + user + "/AppData/Roaming/Useful Autoclicker/";

      makeFolder();
      makeFile();

      getSettings();
   }


   /**
    * @return click delay
    */
   public int[] getClickDelay() {
      return clickDelay;
   }

   /**
    * @return hold delay
    */
   public int[] getHoldDelay() {
      return holdDelay;
   }

   /**
    * @return should randomize delay
    */
   public boolean[] getRandomizeDelay() {
      return new boolean[]{shouldRandomizeClickDelay, shouldRandomizeHoldDelay};
   }

   /**
    * @return randomize range
    */
   public int[] getRandomizeRange() {
      return new int[]{randomizeClickDelayRange, randomizeHoldDelayRange};
   }

   /**
    * @return hotkey code
    */
   public int getHotkeyCode() {
      return hotkeyCode;
   }

   /**
    * @return buttonNumber
    */
   public int getButton() {
      return button;
   }

   /**
    * @return clicks
    */
   public int getClicks() {
      return clicks;
   }

   /**
    * @return autoclick on mouse hold
    */
   public boolean shouldAutoclickOnMouseHold() {
      return autoclickOnMouseHold;
   }

   /**
    * Sets default settings
    */
   private void setDefaultSettings() {
      clickDelay = new int[]{0, 0, 0, 100};
      holdDelay = new int[]{0, 0, 0, 10};
      shouldRandomizeClickDelay = false;
      shouldRandomizeHoldDelay = false;
      randomizeClickDelayRange = 20;
      randomizeHoldDelayRange = 20;
      hotkeyCode = 59;
      button = 0;
      clicks = 0;
      autoclickOnMouseHold = false;
      writeFile();
   }

   /*
    * Gets data from file
    */
   private void getSettings() {
      FileReader reader = null;
      try {
         reader = new FileReader(dataPath + FILE_NAME);
      } catch (FileNotFoundException e) {
         System.out.println("(File read) File not found");
         String message = "<html>There was an error while loading your settings.<br>Try deleting the following folder:<br>" + dataPath + "</html>";
         String title = "(Finding data file) Error finding settings file";
         JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
      }

      for (int i = 0; i < clickDelay.length; i++) {
         clickDelay[i] = Integer.parseInt(readValue(reader));
      }
      for (int i = 0; i < holdDelay.length; i++) {
         holdDelay[i] = Integer.parseInt(readValue(reader));
      }
      shouldRandomizeClickDelay = Boolean.parseBoolean(readValue(reader));
      shouldRandomizeHoldDelay = Boolean.parseBoolean(readValue(reader));
      randomizeClickDelayRange = Integer.parseInt(readValue(reader));
      randomizeHoldDelayRange = Integer.parseInt(readValue(reader));
      hotkeyCode = Integer.parseInt(readValue(reader));
      button = Integer.parseInt(readValue(reader));
      clicks = Integer.parseInt(readValue(reader));
      autoclickOnMouseHold = Boolean.parseBoolean(readValue(reader));
   }

   /*
    * Reads one line from file
    * @param reader FileReader to read where reader left off
    * @return String value of line.
    */
   private String readValue(FileReader reader) {
      try {
         StringBuilder value = new StringBuilder();
         int charNum = -1;
         boolean shouldSave = false;
         while ((char) charNum != '\n') {
            if (shouldSave) {
               value.append((char) charNum);
            }
            if ((char) charNum == ' ') {
               shouldSave = true;
            }
            charNum = reader.read();
         }
         return value.toString();

      } catch (IOException e) {
         setDefaultSettings();
         System.out.println("(File read) Error reading file");
         String message = "<html>There was an error while loading your settings.<br>Try restarting the Autoclicker and deleting the following folder:<br>" + dataPath + "</html>";
         String title = "(Reading data) Error loading settings";
         JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
         return null;
      }
   }

   /**
    * Saves data to file.
    *
    * @param clickDelay                array of click delay
    * @param holdDelay                 array of hold delay
    * @param shouldRandomizeClickDelay boolean should randomize click delay
    * @param shouldRandomizeHoldDelay  boolean should randomize hold delay
    * @param randomizeClickDelayRange  int randomize click delay range
    * @param randomizeHoldDelayRange   int randomize hold delay range
    * @param button                    int button
    * @param clicks                    amount clicks
    */
   public void saveClickerSettings(int[] clickDelay, int[] holdDelay, boolean shouldRandomizeClickDelay, boolean shouldRandomizeHoldDelay, int randomizeClickDelayRange, int randomizeHoldDelayRange, int button, int clicks) {
      this.clickDelay = clickDelay;
      this.holdDelay = holdDelay;
      this.shouldRandomizeClickDelay = shouldRandomizeClickDelay;
      this.shouldRandomizeHoldDelay = shouldRandomizeHoldDelay;
      this.randomizeClickDelayRange = randomizeClickDelayRange;
      this.randomizeHoldDelayRange = randomizeHoldDelayRange;
      this.button = button;
      this.clicks = clicks;
      writeFile();
   }

   /**
    * Saves data to file.
    *
    * @param hotkeyCode           hotkey code
    * @param autoclickOnMouseHold autoclick on mouse hold.
    */
   public void saveInputListenerSettings(int hotkeyCode, boolean autoclickOnMouseHold) {
      this.hotkeyCode = hotkeyCode;
      this.autoclickOnMouseHold = autoclickOnMouseHold;
      writeFile();
   }

   private void writeFile() {
      try {
         FileWriter writer = new FileWriter(dataPath + FILE_NAME);
         String[] timeNames = {"ms", "s", "m", "h"};

         for (int i = 0; i < clickDelay.length; i++) {
            writer.write("clickDelay_" + timeNames[i] + " " + clickDelay[i] + "\n");
         }
         for (int i = 0; i < holdDelay.length; i++) {
            writer.write("holdTime_" + timeNames[i] + " " + holdDelay[i] + "\n");
         }

         writer.write("shouldRandomize_clickDelay " + shouldRandomizeClickDelay + "\n");
         writer.write("shouldRandomize_holdTime " + shouldRandomizeHoldDelay + "\n");

         writer.write("randomizeRange_clickDelay " + randomizeClickDelayRange + "\n");
         writer.write("randomizeRange_holdTime " + randomizeHoldDelayRange + "\n");

         writer.write("hotkeyCode " + hotkeyCode + "\n");
         writer.write("button " + button + "\n");
         writer.write("clicks " + clicks + "\n");
         writer.write("autoclickOnHold " + autoclickOnMouseHold + "\n");

         writer.close();
      } catch (IOException e) {
         System.out.println("(Save settings) Error writing to file");
         String message = "<html>There was an error while saving your settings.<br>Try deleting the following folder:<br>" + dataPath + "</html>";
         String title = "(Saving data to file) Error saving settings";
         JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
      }
   }

   /*
    * Gets windows drive
    * @return windows drive
    */
   private File getWindowsDrive() {
      FileSystemView fileSystemView = FileSystemView.getFileSystemView();
      File[] drives = File.listRoots();
      File windowsDrive = null;
      for (File drive : drives) {
         // if drive is local disk (windows disk)
         String systemDriveDescription = fileSystemView.getSystemTypeDescription(drive);
         if (systemDriveDescription.equals("Local Disk")) {
            // set this drive to Windows Drive
            windowsDrive = drive;
            break;
         }
      }
      return windowsDrive;
   }

   /*
    * Makes folder
    * @param folderPath path of folder to be made
    */
   private void makeFolder() {
      new File(dataPath).mkdir();
   }

   /*
    * Makes file
    * @param filePath path of file to be made
    */
   private void makeFile() {
      File file = new File(dataPath + FILE_NAME);
      if (!file.exists()) {
         try {
            file.createNewFile();
            setDefaultSettings();
         } catch (IOException e) {
            System.out.println("(make file) file not could not be created");
            String message = "<html>There was an error while saving your settings.</html>";
            String title = "(make file) file not could not be created";
            JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
         }

      }

   }
}