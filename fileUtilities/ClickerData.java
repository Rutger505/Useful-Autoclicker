package fileUtilities;

import errorHandeling.Error;
import settings.Settings;

import javax.swing.filechooser.FileSystemView;
import java.io.*;

public class ClickerData {

   private static final String FILE_NAME = "clickerData.txt";
   private static String dataPath = null;

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
    * Gets data from file
    */
   private void getSettings() {
      FileReader reader = null;
      try {
         reader = new FileReader(dataPath + FILE_NAME);
      } catch (FileNotFoundException e) {
         Error.showError("(Finding data file) Error finding settings file", "<html>There was an error while loading your settings.<br>Try deleting the following folder:<br>" + dataPath + "</html>", "(File read) File not found");
      }
      int[] temp = new int[Settings.getClickDelayArray().length];
      for (int i = 0; i < Settings.getClickDelayArray().length; i++) {
         temp[i] = Integer.parseInt(readValue(reader));
      }
      Settings.setClickDelay(temp);

      int[] temp2 = new int[Settings.getHoldDelayArray().length];
      for (int i = 0; i < Settings.getHoldDelayArray().length; i++) {
         temp2[i] = Integer.parseInt(readValue(reader));
      }
      Settings.setHoldDelay(temp2);

      Settings.setShouldRandomizeClick(Boolean.parseBoolean(readValue(reader)));
      Settings.setShouldRandomizeHold(Boolean.parseBoolean(readValue(reader)));

      Settings.setClickRandomizeRange(Integer.parseInt(readValue(reader)));
      Settings.setHoldRandomizeRange(Integer.parseInt(readValue(reader)));

      Settings.setHotkey(Integer.parseInt(readValue(reader)));

      Settings.setButtonNumber(Integer.parseInt(readValue(reader)));

      Settings.setClicks(Integer.parseInt(readValue(reader)));

      Settings.setAutoclickOnMouseHold(Boolean.parseBoolean(readValue(reader)));
   }

   /**
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
         writeFile();
         Error.showError("(Reading data) Error loading settings", "<html>There was an error while loading your settings.<br>Try restarting the Autoclicker and deleting the following folder:<br>" + dataPath + "</html>", "(File read) Error reading file");
         return null;
      }
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
         Error.showError("<html>There was an error while saving your settings.<br>Try deleting the following folder:<br>" + dataPath + "</html>", "(Saving data to file) Error saving settings", "(Save settings) Error writing to file");
      }
   }

   /**
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