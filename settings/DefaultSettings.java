package settings;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

import java.awt.event.InputEvent;

public class DefaultSettings {
   // Autoclicker
   public static final int clicks = 0;

   public static final long clickDelay = 100;
   public static final long clickDelayOriginal = 100;
   public static final int[] clickDelayArray = {100, 0, 0, 0};


   public static final long holdDelay = 10;
   public static final long holdDelayOriginal = 10;
   public static final int[] holdDelayArray = {10, 0, 0, 0};

   public static final boolean shouldRandomizeClick = false;
   public static final boolean shouldRandomizeHold = false;

   public static final int clickRandomizeRange = 20;

   public static final int holdRandomizeRange = 20;

   public static final int buttonNumber = 0;
   public static final int button = InputEvent.getMaskForButton(buttonNumber + 1);

   // inputListener
   public static final int hotkey = 59;
   public static final String hotkeyText = NativeKeyEvent.getKeyText(hotkey);
   public static final boolean autoclickOnMouseHold = false;
}
