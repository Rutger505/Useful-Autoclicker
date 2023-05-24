package fileUtilities;

import errorHandeling.Error;
import settings.Settings;

import javax.swing.filechooser.FileSystemView;
import java.io.*;

public class ClickerData {

   private static final String FILE_NAME = "clickerData.txt";
   public static final File WINDOWS_DRIVE = getWindowsDrive();
   public static String USER = System.getProperty("user.name");
   private static String dataPath = null;
   private static final int TEST_BOOLEAN = 0;
   private static final int TEST_INT = 1;
   private FileReader reader;

   /**
    * Makes data file if it doesn't exist
    * and gets settings from a file.
    */
   public ClickerData() {
      // get data path
      dataPath = WINDOWS_DRIVE + "Users/" + USER + "/AppData/Roaming/Useful-Autoclicker/";


      makeFolder();
      makeFile();

      getSettings();
   }

   /**
    * Writes the file in the data folder with the settings from settings class.
    */
   public static void writeFile() {
      try {
         FileWriter writer = new FileWriter(dataPath + FILE_NAME);
         String[] timeNames = {"ms", "s", "m", "h"};

         for (int i = 0; i < Settings.getClickDelayArray().length; i++) {
            writer.write("clickDelay_" + timeNames[i] + " " + Settings.getClickDelayArray()[i] + "\n");
         }
         for (int i = 0; i < Settings.getHoldDelayArray().length; i++) {
            writer.write("holdTime_" + timeNames[i] + " " + Settings.getHoldDelayArray()[i] + "\n");
         }

         writer.write("shouldRandomize_clickDelay " + Settings.shouldRandomizeClick() + "\n");
         writer.write("shouldRandomize_holdTime " + Settings.shouldRandomizeHold() + "\n");

         writer.write("randomizeRange_clickDelay " + Settings.getClickRandomizeRange() + "\n");
         writer.write("randomizeRange_holdTime " + Settings.getHoldRandomizeRange() + "\n");

         writer.write("hotkeyCode " + Settings.getHotkey() + "\n");
         writer.write("buttonNumber " + Settings.getButtonNumber() + "\n");
         writer.write("clicks " + Settings.getClicks() + "\n");
         writer.write("autoclickOnHold " + Settings.shouldAutoclickOnMouseHold() + "\n");

         writer.close();
      } catch (IOException e) {
         Error.showError("<html>There was an error while saving your settings.<br>Try restarting the Autoclicker or deleting the following folder:<br>" + dataPath + "</html>", "(Saving data to file) Error saving settings", "(Save settings) Error writing to file");
      }
   }

   /**
    * Gets data from file
    */
   private void getSettings() {
      try {
         reader = new FileReader(dataPath + FILE_NAME);
      } catch (FileNotFoundException e) {
         Error.showError("(Finding data file) Error finding settings file", "<html>There was an error while loading your settings.<br>Try deleting the following folder:<br>" + dataPath + "</html>", "(File read) File not found");
      }
      int[] temp = new int[Settings.getClickDelayArray().length];
      for (int i = 0; i < Settings.getClickDelayArray().length; i++) {
         temp[i] = Integer.parseInt(processValue(TEST_INT));
      }
      Settings.setClickDelay(temp);

      int[] temp2 = new int[Settings.getHoldDelayArray().length];
      for (int i = 0; i < Settings.getHoldDelayArray().length; i++) {
         temp2[i] = Integer.parseInt(processValue(TEST_INT));
      }
      Settings.setHoldDelay(temp2);

      Settings.setShouldRandomizeClick(Boolean.parseBoolean(processValue(TEST_BOOLEAN)));
      Settings.setShouldRandomizeHold(Boolean.parseBoolean(processValue(TEST_BOOLEAN)));

      Settings.setClickRandomizeRange(Integer.parseInt(processValue(TEST_INT)));
      Settings.setHoldRandomizeRange(Integer.parseInt(processValue(TEST_INT)));

      Settings.setHotkey(Integer.parseInt(processValue(TEST_INT)));

      Settings.setButtonNumber(Integer.parseInt(processValue(TEST_INT)));

      Settings.setClicks(Integer.parseInt(processValue(TEST_INT)));

      Settings.setAutoclickOnMouseHold(Boolean.parseBoolean(processValue(TEST_BOOLEAN)));
   }

   /**
    * Gets value from the config file and checks if it is parsable to desired type.
    *
    * @return String value of line.
    */
   private String processValue(int type) {
      String value = readValue();
      if (type == TEST_BOOLEAN) {
         if(value == null) {
            Error.unidentifiedChangeError();
            return "false";
         }
         if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            return value;
         } else {
            Error.unidentifiedChangeError();
            return "false";
         }
      }

      if (type == TEST_INT) {
         try {
            Integer.parseInt(value);
            return value;
         } catch (NumberFormatException e) {
            Error.unidentifiedChangeError();
            return "0";
         }
      }
      throw new IllegalArgumentException("Type must be either TEST_BOOLEAN or TEST_INT.");
   }

   /**
    * Reads one line from file
    *
    * @return String value of line.
    */
   private String readValue() {
      try {
         StringBuilder value = new StringBuilder();
         int charNum = 0;
         boolean shouldSave = false;
         while ((char) charNum != '\n' && charNum != -1) {
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
         writeFile();
         Error.showError("(Reading data) Error loading settings", "<html>There was an error while loading your settings.<br>Try restarting the Autoclicker and deleting the following folder:<br>" + dataPath + "</html>", "(File read) Error reading file");
         return null;
      }
   }

   /**
    * Gets windows drive
    *
    * @return windows drive
    */
   private static File getWindowsDrive() {
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

   /**
    * Makes folder
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
            writeFile();
         } catch (IOException e) {
            Error.showError("(make file) file not could not be created", "<html>There was an error while saving your settings.</html>", "(make file) file not could not be created");
         }
      }
   }
}