package main;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;
import fileHider.FileHider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputListener implements NativeKeyListener, NativeMouseListener, ActionListener {
   private final GUI gui;
   private Autoclicker clicker;
   private final JButton newHotkeyButton;
   private final JCheckBox autoclickOnMouseHoldCheckBox;

   private boolean newHotkey = false;
   private int hotkey = 59;
   private String hotkeyText = "F1";
   private int mouseButton = 1;

   private int registeredPressing = 0;
   private boolean autoclickOnMouseHold = false;

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


      JLabel autoclickOnMouseHoldLabel = gui.labelFactory("Autoclick on button hold:", false, false, new int[]{135, 255, 190, 20});
      autoclickOnMouseHoldCheckBox = gui.checkBoxFactory(this, new int[]{275, 255, 15, 20});

      JPanel autoclickOnMouseHoldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 9));
      autoclickOnMouseHoldPanel.setBounds(125, 250, 175, 30);
      autoclickOnMouseHoldPanel.add(autoclickOnMouseHoldLabel);
      autoclickOnMouseHoldPanel.add(autoclickOnMouseHoldCheckBox);

//      gui.addComponent(autoclickOnMouseHoldPanel , gui);
   }

   @Override
   public void nativeKeyPressed(NativeKeyEvent e) {
      int keyPressed = e.getKeyCode();

      if (newHotkey) {
         newHotkey(e);
      } else if (keyPressed == hotkey && !autoclickOnMouseHold) {
         toggleClicker();
      }
   }

   @Override
   public void nativeMousePressed(NativeMouseEvent e) {

      if (autoclickOnMouseHold) {
         int buttonPressed = e.getButton();


         if (buttonPressed == mouseButton) {
            if (registeredPressing == 0 && autoclickOnMouseHold) {
//               toggleClicker(true);
            }
            registeredPressing++;
         }
      }
   }

   @Override
   public void nativeMouseReleased(NativeMouseEvent e) {

      if (autoclickOnMouseHold){
         int buttonPressed = e.getButton();

         if (buttonPressed == mouseButton) {
            registeredPressing--;
            if (registeredPressing == 0) {
//               toggleClicker(false);
            }
         }
      }

   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == newHotkeyButton) {
         newHotkeyButton.setText("Press new hotkey");
         newHotkey = true;
      } else if (e.getSource() == autoclickOnMouseHoldCheckBox) {
         autoclickOnMouseHold = autoclickOnMouseHoldCheckBox.isSelected();

         if (!autoclickOnMouseHoldCheckBox.isSelected()) {
            toggleClicker(false);
         }
      }

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
    * Toggles the clicker to a specific state
    *
    * @param activate true to activate, false to deactivate clicker
    */
   private void toggleClicker(boolean activate) {
      if (activate && !clicker.isRunning()) {
         clicker = new Autoclicker(gui);
         clicker.start();
      } else if (!activate && clicker.isRunning()) {
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
