package settings;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import fileUtilities.ClickerData;

import java.awt.event.InputEvent;

public class Settings {
   // Autoclicker
   private static int clicks = 0;

   private static long clickDelay = 100;
   private static long clickDelayOriginal = 100;
   private static int[] clickDelayArray = {0, 0, 0, 100};


   private static long holdDelay = 10;
   private static long holdDelayOriginal = 10;
   private static int[] holdDelayArray = {0, 0, 0, 10};

   private static boolean shouldRandomizeClick = false;
   private static boolean shouldRandomizeHold = false;

   private static int clickRandomizeRange = 20;

   private static int holdRandomizeRange = 20;

   private static int buttonNumber = 0;
   private static int button = InputEvent.getMaskForButton(buttonNumber + 1);

   // inputListener
   private static int hotkey = 59;
   private static String hotkeyText = NativeKeyEvent.getKeyText(hotkey);
   private static boolean autoclickOnMouseHold = false;

   public static int getClicks() {
      return clicks;
   }

   public static void setClicks(int clicks) {
      Settings.clicks = clicks;
      ClickerData.writeFile();
   }

   public static long getClickDelay() {
      return clickDelay;
   }

   public static void setClickDelay(long clickDelay) {
      Settings.clickDelay = clickDelay;
      ClickerData.writeFile();
   }

   public static void setClickDelay(int[] clickDelayRaw) {
      clickDelay = 0;
      clickDelay += clickDelayRaw[3] * 3_600_000L;
      clickDelay += clickDelayRaw[2] * 60_000L;
      clickDelay += clickDelayRaw[1] * 1_000L;
      clickDelay += clickDelayRaw[0];
      // prevent lagging
      if (clickDelay < 1) {
         clickDelay = 1;
      }
      clickDelayOriginal = clickDelay;
      clickDelayArray = clickDelayRaw;
      ClickerData.writeFile();
   }

   public static void setClickDelay(int element, int index){
      clickDelayArray[index] = element;
      setClickDelay(clickDelayArray);
   }

   public static long getClickDelayOriginal() {
      return clickDelayOriginal;
   }

   public static int[] getClickDelayArray() {
      return clickDelayArray;
   }

   public static long getHoldDelay() {
      return holdDelay;
   }

   public static void setHoldDelay(int[] holdDelayRaw) {
      holdDelay = 0;
      holdDelay += holdDelayRaw[3] * 3_600_000L;
      holdDelay += holdDelayRaw[2] * 60_000L;
      holdDelay += holdDelayRaw[1] * 1_000L;
      holdDelay += holdDelayRaw[0];
      holdDelayOriginal = holdDelay;
      holdDelayArray = holdDelayRaw;
      ClickerData.writeFile();
   }

   public static void setHoldDelay(int element, int index){
      holdDelayArray[index] = element;
      setHoldDelay(holdDelayArray);
   }

   public static void setHoldDelay(long holdDelay) {
      Settings.holdDelay = holdDelay;
      ClickerData.writeFile();
   }

   public static long getHoldDelayOriginal() {
      return holdDelayOriginal;
   }

   public static int[] getHoldDelayArray() {
      return holdDelayArray;
   }

   public static boolean shouldRandomizeClick() {
      return shouldRandomizeClick;
   }

   public static boolean shouldRandomizeHold() {
      return shouldRandomizeHold;
   }

   public static int getHoldRandomizeRange() {
      return holdRandomizeRange;
   }

   public static void setHoldRandomizeRange(int holdRandomizeRange) {
      Settings.holdRandomizeRange = holdRandomizeRange;
      ClickerData.writeFile();
   }

   public static int getClickRandomizeRange() {
      return clickRandomizeRange;
   }

   public static void setClickRandomizeRange(int clickRandomizeRange) {
      Settings.clickRandomizeRange = clickRandomizeRange;
      ClickerData.writeFile();
   }

   public static void setRandomizeRange(int element, int index){
      if (index == 0){
         setClickRandomizeRange(element);
      } else if (index == 1){
         setHoldRandomizeRange(element);
      }
   }

   public static int getButtonNumber() {
      return buttonNumber;
   }

   public static void setButtonNumber(int buttonNumber) {
      Settings.buttonNumber = buttonNumber;
      ClickerData.writeFile();
   }

   public static int getButton() {
      return button;
   }

   public static void setButton(int button) {
      Settings.button = button;
      ClickerData.writeFile();
   }

   public static int getHotkey() {
      return hotkey;
   }

   public static void setHotkey(int hotkey) {
      Settings.hotkey = hotkey;
      Settings.hotkeyText = NativeKeyEvent.getKeyText(hotkey);
      ClickerData.writeFile();
   }

   public static String getHotkeyText() {
      return hotkeyText;
   }

   public static boolean shouldAutoclickOnMouseHold() {
      return autoclickOnMouseHold;
   }

   public static void setShouldRandomizeClick(boolean shouldRandomizeClick) {
      Settings.shouldRandomizeClick = shouldRandomizeClick;
      ClickerData.writeFile();
   }

   public static void setShouldRandomizeHold(boolean shouldRandomizeHold) {
      Settings.shouldRandomizeHold = shouldRandomizeHold;
      ClickerData.writeFile();
   }

   public static void setAutoclickOnMouseHold(boolean autoclickOnMouseHold) {
      Settings.autoclickOnMouseHold = autoclickOnMouseHold;
      ClickerData.writeFile();
   }

   private Settings(){
      throw new IllegalStateException("Utility class");
   }
}
