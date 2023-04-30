package fileUtilities;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;

public class ClickerData {

   private final String FILE_NAME = "clickerData.txt";
   private final String DATA_PATH;

   private int[] clickDelay = new int[4];
   private int[] holdDelay = new int[4];
   private boolean[] randomizeDelay = new boolean[2];
   private int[] randomizeRange = new int[2];
   private int hotkeyCode;
   private int button;
   private int clicks;
   private boolean autoclickOnMouseHold;

   public ClickerData() {
      // get data path
      File windowsDrive = getWindowsDriveFile();
      String user = System.getProperty("user.name");
      DATA_PATH = windowsDrive + "Users/" + user + "/AppData/Roaming/Useful Autoclicker/";

      makeFolder();
      makeFile();

      getData();
   }

   public int[] getClickDelay() {
      return clickDelay;
   }

   public int[] getHoldDelay() {
      return holdDelay;
   }

   public boolean[] getRandomizeDelay() {
      return randomizeDelay;
   }

   public int[] getRandomizeRange() {
      return randomizeRange;
   }

   public int getHotkeyCode() {
      return hotkeyCode;
   }

   public int getButton() {
      return button;
   }

   public int getClicks() {
      return clicks;
   }

   public boolean getAutoclickOnMouseHold() {
      return autoclickOnMouseHold;
   }

   /*
    *  Sets default settings
    */
   private void setDefaultSettings() {
      saveData(new int[]{100, 0, 0, 0}, new int[]{10, 0, 0, 0}, new boolean[]{false, false}, new int[]{20, 20}, 59, 1, 0, false);
   }

   /*
    * Gets data from file
    */
   private void getData() {
      FileReader reader = null;
      try {
         reader = new FileReader(DATA_PATH + FILE_NAME);
      } catch (FileNotFoundException e) {
         System.out.println("(File read) File not found");
         String message = "<html>There was an error while loading your settings.<br>Try deleting the following folder:<br>" + DATA_PATH + "</html>";
         String title = "(Finding data file) Error finding settings file";
         JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
      }

      for (int i = 0; i < clickDelay.length; i++) {
         clickDelay[i] = Integer.parseInt(readValue(reader));
      }
      for (int i = 0; i < holdDelay.length; i++) {
         holdDelay[i] = Integer.parseInt(readValue(reader));
      }
      for (int i = 0; i < randomizeDelay.length; i++) {
         randomizeDelay[i] = Boolean.parseBoolean(readValue(reader));
      }
      for (int i = 0; i < randomizeRange.length; i++) {
         randomizeRange[i] = Integer.parseInt(readValue(reader));
      }
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
         int charNum = reader.read();
         while ((char) charNum != '\n') {
            value.append((char) charNum);
            charNum = reader.read();
         }
         return value.toString();
      } catch (IOException e) {
         setDefaultSettings();
         System.out.println("(File read) Error reading file");
         String message = "<html>There was an error while loading your settings.<br>Try restarting the Autoclicker and deleting the following folder:<br>" + DATA_PATH + "</html>";
         String title = "(Reading data) Error loading settings";
         JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
      }
      return null;
   }

   /*
    * Saves data to file.
    * @param clickDelay array of click delay
    * @param holdDelay array of hold delay
    * @param randomizeDelay should randomize delay
    * @param randomizeRange randomize range
    * @param hotkeyCode hotkey code
    * @param button int button
    * @param clicks amount clicks
    * @param autoclickOnMouseHold autoclick on mouse hold.
    */
   public void saveData(int[] clickDelay, int[] holdDelay, boolean[] randomizeDelay, int[] randomizeRange, int hotkeyCode, int button, int clicks, boolean autoclickOnMouseHold) {
      try {
         FileWriter writer = new FileWriter(DATA_PATH + FILE_NAME);

         for (int delay : clickDelay) {
            writer.write(delay + "\n");
         }
         for (int delay : holdDelay) {
            writer.write(delay + "\n");
         }
         for (boolean randomize : randomizeDelay) {
            writer.write(randomize + "\n");
         }
         for (int range : randomizeRange) {
            writer.write(range + "\n");
         }
         writer.write(hotkeyCode + "\n");
         writer.write(button + "\n");
         writer.write(clicks + "\n");
         writer.write(autoclickOnMouseHold + "\n");

         writer.close();
      } catch (IOException e) {
         System.out.println("(Save settings) Error writing to file");
         String message = "<html>There was an error while saving your settings.<br>Try deleting the following folder:<br>" + DATA_PATH + "</html>";
         String title = "(Saving data to file) Error saving settings";
         JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
      }
   }

   /*
    * Gets windows drive
    * @return windows drive
    */
   private File getWindowsDriveFile() {
      FileSystemView fileSystemView = FileSystemView.getFileSystemView();
      File[] drives = File.listRoots();
      File windowsDrive = null;
      for (File drive : drives) {
         // if drive is local disk (windows disk)
         if (fileSystemView.getSystemTypeDescription(drive).equals("Local Disk")) {
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
      File folder = new File(DATA_PATH);
      if (!folder.exists()) {
         folder.mkdir();
      }
   }

   /*
    * Makes file
    * @param filePath path of file to be made
    */
   private void makeFile() {
      File file = new File(DATA_PATH + FILE_NAME);
      if (!file.exists()) {
         try {
            file.createNewFile();
            setDefaultSettings();
         } catch (Exception e) {
            System.out.println("(make file) file not could not be created");
            String message = "<html>There was an error while saving your settings.</html>";
            String title = "(make file) file not could not be created";
            JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
         }
      }
   }
}