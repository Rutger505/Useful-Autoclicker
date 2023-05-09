package main;

import GUI.GUI;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;
import fileUtilities.ClickerData;
import fileUtilities.FileHider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputListener implements NativeKeyListener, NativeMouseListener, ActionListener {
   private final ClickerData clickerData = new ClickerData();
   private final GUI gui;
   private Autoclicker clicker;
   private final JButton newHotkeyButton;
   private final JCheckBox autoclickOnMouseHoldCheckBox;

   private boolean newHotkey;
   private int hotkey;
   private String hotkeyText;
   private int mouseButtonSelected;

   private int registeredPressing;
   private boolean autoclickOnMouseHold;

   /**
    * Setup input listener(JNativeHook) and makes components for GUI.
    * @param gui GUI for making components
    */
   public InputListener(GUI gui) {
      this.gui = gui;
      clicker = new Autoclicker(gui);

      // add key and mouse listener
      try {
         GlobalScreen.registerNativeHook();
      } catch (NativeHookException e) {
         System.out.println("(InputListener) JNativeHook could not be started");
         e.printStackTrace();
      }
      GlobalScreen.addNativeKeyListener(this);
      GlobalScreen.addNativeMouseListener(this);

      // hide JNativeHook file
      new FileHider("JNativeHook.x86_64.dll");

      // GUI components
      // new hotkey button
      hotkey = clickerData.getHotkeyCode();
      hotkeyText = NativeKeyEvent.getKeyText(hotkey);
      newHotkeyButton = gui.buttonFactory("Select Hotkey(" + hotkeyText + ")", null, this, new int[]{20, 210, 165, 30});
      newHotkeyButton.setName(String.valueOf(hotkey));

      // autoclick on hold
      JLabel autoclickOnMouseHoldLabel = gui.labelFactory("Autoclick on button hold:", false, false, new int[]{135, 255, 190, 20});
      autoclickOnMouseHoldCheckBox = gui.checkBoxFactory(this, clickerData.shouldAutoclickOnMouseHold(), new int[]{275, 255, 15, 20});
      JPanel autoclickOnMouseHoldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 9));
      autoclickOnMouseHoldPanel.setBounds(125, 250, 175, 30);
      autoclickOnMouseHoldPanel.add(autoclickOnMouseHoldLabel);
      autoclickOnMouseHoldPanel.add(autoclickOnMouseHoldCheckBox);

      gui.add(newHotkeyButton);
      gui.add(autoclickOnMouseHoldPanel);
   }

   @Override
   public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
      int keyPressed = nativeEvent.getKeyCode();

      if (newHotkey) {
         newHotkey(nativeEvent);
      } else if (keyPressed == hotkey && !autoclickOnMouseHold) {
         toggleClicker();
      }
   }

   @Override
   public void nativeMousePressed(NativeMouseEvent nativeEvent) {
      if (!autoclickOnMouseHold) {
         return;
      }
      mouseButtonSelected = gui.getButtonSelect().getSelectedIndex() + 1;
      int buttonPressed = nativeEvent.getButton();

      if (buttonPressed == mouseButtonSelected) {
         if (registeredPressing == 0) {
            toggleClicker(true);
         }
         registeredPressing++;
      }
   }

   @Override
   public void nativeMouseReleased(NativeMouseEvent nativeEvent) {
      if (!autoclickOnMouseHold) {
         return;
      }
      int buttonPressed = nativeEvent.getButton();

      if (buttonPressed == mouseButtonSelected) {
         registeredPressing--;

         if (registeredPressing == 0) {
            toggleClicker(false);
         }
      }
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == newHotkeyButton) {
         toggleClicker(false);
         newHotkey = true;
         newHotkeyButton.setText("Press new hotkey");
      } else if (e.getSource() == autoclickOnMouseHoldCheckBox) {
         autoclickOnMouseHold = autoclickOnMouseHoldCheckBox.isSelected();

         if (!autoclickOnMouseHold) {
            toggleClicker(false);
         }
      } else {
         System.out.println("Unknown action performed");
      }
   }

   /**
    * Toggles the clicker
    */
   private void toggleClicker() {
      if (!clicker.isRunning()) {
         clickerData.saveInputListenerSettings(hotkey, autoclickOnMouseHold);
         clicker = new Autoclicker(gui);
         clicker.start();
      } else {
         clicker.stopClicker();
      }
   }

   /**
    * Toggles the clicker to a specific state
    *
    * @param activate true to activate, false to deactivate clicker
    */
   private void toggleClicker(boolean activate) {
      if (activate && !clicker.isRunning()) {
         clickerData.saveInputListenerSettings(hotkey, autoclickOnMouseHold);
         clicker = new Autoclicker(gui);
         clicker.start();
      } else if (!activate && clicker.isRunning()) {
         clicker.stopClicker();
         registeredPressing = 0;
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
