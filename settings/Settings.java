package settings;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

import java.awt.event.InputEvent;

public class Settings {
   // Autoclicker
   public static int clicks = 0;

   public static long clickDelay = 100;
   public static long clickDelayOriginal = 100;
   public static int[] clickDelayArray = {0, 0, 0, 100};


   public static long holdDelay = 10;
   public static long holdDelayOriginal = 10;
   public static int[] holdDelayArray = {0, 0, 0, 10};

   public static boolean shouldRandomizeClick = false;
   public static boolean shouldRandomizeHold = false;

   public static int clickRandomizeRange = 20;
   public static int holdRandomizeRange = 20;

   public static int buttonNumber;
   public static int button = InputEvent.getMaskForButton(buttonNumber + 1);

   // inputListener
   public static int hotkey = 59;
   public static String hotkeyText = NativeKeyEvent.getKeyText(hotkey);
   public static boolean autoclickOnMouseHold;


   public static void toMsClickDelay(int[] clickDelayRaw) {
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
   }

   public static void toMsHoldDelay(int[] holdDelayRaw) {
      holdDelay = 0;
      holdDelay += holdDelayRaw[3] * 3_600_000L;
      holdDelay += holdDelayRaw[2] * 60_000L;
      holdDelay += holdDelayRaw[1] * 1_000L;
      holdDelay += holdDelayRaw[0];
      holdDelayOriginal = holdDelay;
      holdDelayArray = holdDelayRaw;
   }
}
