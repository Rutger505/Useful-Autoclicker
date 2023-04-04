package main;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;
import fileHider.FileHider;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputListener implements NativeKeyListener, NativeMouseListener, ActionListener {
   private final GUI gui;
   private Autoclicker clicker;
   private final JButton newHotkeyButton;

   private boolean newHotkey = false;
   private int hotkey = 59;
   private String hotkeyText = "F1";
   private int mouseButton = 1;
   private String mouseButtonText = "Left";


   public InputListener(GUI gui) {
      this.gui = gui;
      clicker = new Autoclicker(gui);

      // add key and mouse listener
      try {
         GlobalScreen.registerNativeHook();
      } catch (NativeHookException e) {
         e.printStackTrace();
      }
      GlobalScreen.addNativeKeyListener(this);
      GlobalScreen.addNativeMouseListener(this);

      // hide JNativeHook file
      new FileHider("JNativeHook.x86_64.dll");

      // new hotkey button
      newHotkeyButton = gui.buttonFactory("Select Hotkey(" + hotkeyText + ")", null, this, new int[]{20, 210, 165, 30});
      gui.addComponent(newHotkeyButton, gui);
   }

   @Override
   public void nativeKeyPressed(NativeKeyEvent e) {
      int keyPressed = e.getKeyCode();
      boolean autoclickOnHold = gui.getAutoClickOnMouseHold().isSelected();

      if (newHotkey) {
         newHotkey(e);
      } else if (keyPressed == hotkey && !autoclickOnHold) {
         toggleClicker();
      }
   }

   @Override
   public void nativeMousePressed(NativeMouseEvent e) {
//      int mouseButton = e.getButton();
//      System.out.println("mouse pressed: " + mouseButton);
//      System.out.println();
   }

   @Override
   public void nativeMouseReleased(NativeMouseEvent e) {
//      int mouseButton = e.getButton();
//      System.out.println("mouse released: " + mouseButton);
//      System.out.println();
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      newHotkeyButton.setText("Press new hotkey");
      newHotkey = true;
   }

   /**
    * Toggles the clicker
    */
   private void toggleClicker() {
      if (!clicker.isRunning()) {
         clicker = new Autoclicker(gui);
         clicker.start();
      } else {
         clicker.stopClicker();
      }
   }

   /**
    * Saves the new hotkey
    *
    * @param e the key event
    */
   private void newHotkey(NativeKeyEvent e) {
      hotkey = e.getKeyCode();
      hotkeyText = NativeKeyEvent.getKeyText(hotkey);
      newHotkeyButton.setText("Select Hotkey(" + hotkeyText + ")");
      newHotkey = false;
   }
}
