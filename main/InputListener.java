package main;

import GUI.GUI;
import GUI.HelpGUI;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;
import fileUtilities.ClickerData;
import fileUtilities.FileHider;
import settings.Settings;

import javax.swing.text.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

public class InputListener implements NativeKeyListener, NativeMouseListener {
   private final ClickerData clickerData = new ClickerData();
   private final HelpGUI helpGUI = new HelpGUI();
   private final GUI gui;
   private Autoclicker clicker;

   private boolean newHotkey;
   private int mouseButtonSelected;
   private int registeredPressing;

   /**
    * Setup input listener(JNativeHook) and makes components for GUI.
    *
    * @param gui GUI for making components
    */
   public InputListener(GUI gui) {
      this.gui = gui;
      clicker = new Autoclicker(gui);


      // help gui button
      gui.getHelpButton().addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            helpGUI.setLocationRelativeTo(gui);
            helpGUI.setVisible(true);
         }
      });

      // autoclick on mouse hold
      gui.getAutoclickOnMouseHold().addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            Settings.autoclickOnMouseHold = gui.getAutoclickOnMouseHold().isSelected();
            System.out.println("(InputListener) Autoclick on mouse hold: " + Settings.autoclickOnMouseHold);

            if (!Settings.autoclickOnMouseHold) {
               toggleClicker(false);
            }
         }
      });

      // should randomize click
      gui.getShouldRandomize()[0].addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            Settings.shouldRandomizeClick = gui.getShouldRandomize()[0].isSelected();
            System.out.println("(InputListener) Should randomize click: " + Settings.shouldRandomizeClick);
         }
      });

      // should randomize hold
      gui.getShouldRandomize()[1].addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            Settings.shouldRandomizeHold = gui.getShouldRandomize()[1].isSelected();
            System.out.println("(InputListener) Should randomize hold: " + Settings.shouldRandomizeHold);
         }
      });


      // new hotkey button
      gui.getNewHotkeyButton().addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            toggleClicker(false);
            newHotkey = true;
            gui.getNewHotkeyButton().setText("Press new hotkey");
         }
      });

      gui.getClickDelay()[0].addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            System.out.println("(InputListener) Pressed enter on textField");
         }
      });

      // combo box (button select)
      gui.getButtonSelect().addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            int buttonNumber = gui.getButtonSelect().getSelectedIndex() + 1;
            if (buttonNumber == 2) {
               buttonNumber = 3;
            } else if (buttonNumber == 3) {
               buttonNumber = 2;
            }
            Settings.button = InputEvent.getMaskForButton(buttonNumber);
            Settings.buttonNumber = gui.getButtonSelect().getSelectedIndex();
         }
      });

      // default button
      gui.getDefaultsButton().addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            gui.getButtonSelect().setSelectedIndex(0);
            gui.getShouldRandomize()[0].setSelected(false);
            gui.getShouldRandomize()[1].setSelected(false);
            gui.getAutoclickOnMouseHold().setSelected(false);
         }
      });


      // document Filters        ##################################################################################################
      int limitDefault = 4;
      int limitClicks = 5;

      PlainDocument[] clickDelayDocument = new PlainDocument[4];
      for (int i = 0; i < clickDelayDocument.length; i++) {
         clickDelayDocument[i] = (PlainDocument) gui.getClickDelay()[i].getDocument();
      }
      clickDelayDocument[0].setDocumentFilter(new DocumentFilter() {
         @Override
         public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.replace(offset, offset + length, text);

            if (isInt(sb.toString()) && sb.length() <= limitDefault) {
               System.out.println("(InputListener)(ClickDelay) string replaced");
               System.out.println(sb);
               super.replace(fb, offset, length, text, attrs);
            }
         }

         @Override
         public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.delete(offset, offset + length);

            if (sb.toString().isEmpty()) {
               super.replace(fb, offset, length, "", null);
            } else {
               if (isInt(sb.toString())) {
                  System.out.println("(InputListener)(ClickDelay) string Removed");
                  super.remove(fb, offset, length);
               }
            }
         }
      });
      clickDelayDocument[1].setDocumentFilter(new DocumentFilter() {
         @Override
         public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.replace(offset, offset + length, text);

            if (isInt(sb.toString()) && sb.length() <= limitDefault) {
               System.out.println("(InputListener)(ClickDelay) string replaced");
               System.out.println(sb);
               super.replace(fb, offset, length, text, attrs);
            }
         }

         @Override
         public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.delete(offset, offset + length);

            if (sb.toString().isEmpty()) {
               super.replace(fb, offset, length, "", null);
            } else {
               if (isInt(sb.toString())) {
                  System.out.println("(InputListener)(ClickDelay) string Removed");
                  super.remove(fb, offset, length);
               }
            }
         }
      });
      clickDelayDocument[2].setDocumentFilter(new DocumentFilter() {
         @Override
         public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.replace(offset, offset + length, text);

            if (isInt(sb.toString()) && sb.length() <= limitDefault) {
               System.out.println("(InputListener)(ClickDelay) string replaced");
               System.out.println(sb);
               super.replace(fb, offset, length, text, attrs);
            }
         }

         @Override
         public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.delete(offset, offset + length);

            if (sb.toString().isEmpty()) {
               super.replace(fb, offset, length, "", null);
            } else {
               if (isInt(sb.toString())) {
                  System.out.println("(InputListener)(ClickDelay) string Removed");
                  super.remove(fb, offset, length);
               }
            }
         }
      });
      clickDelayDocument[3].setDocumentFilter(new DocumentFilter() {
         @Override
         public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.replace(offset, offset + length, text);

            if (isInt(sb.toString()) && sb.length() <= limitDefault) {
               System.out.println("(InputListener)(ClickDelay) string replaced");
               System.out.println(sb);
               super.replace(fb, offset, length, text, attrs);
            }
         }

         @Override
         public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.delete(offset, offset + length);

            if (sb.toString().isEmpty()) {
               super.replace(fb, offset, length, "", null);
            } else {
               if (isInt(sb.toString())) {
                  System.out.println("(InputListener)(ClickDelay) string Removed");
                  super.remove(fb, offset, length);
               }
            }
         }
      });

      PlainDocument[] holdDelayDocument = new PlainDocument[4];
      for (int i = 0; i < holdDelayDocument.length; i++) {
         holdDelayDocument[i] = (PlainDocument) gui.getHoldDelay()[i].getDocument();
      }
      holdDelayDocument[0].setDocumentFilter(new DocumentFilter() {
         @Override
         public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.replace(offset, offset + length, text);

            if (isInt(sb.toString()) && sb.length() <= limitDefault) {
               System.out.println("(InputListener)(HoldDelay) string replaced");
               System.out.println(sb);
               super.replace(fb, offset, length, text, attrs);
            }
         }

         @Override
         public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.delete(offset, offset + length);

            if (sb.toString().isEmpty()) {
               super.replace(fb, offset, length, "", null);
            } else {
               if (isInt(sb.toString())) {
                  System.out.println("(InputListener)(HoldDelay) string Removed");
                  super.remove(fb, offset, length);
               }
            }
         }
      });
      holdDelayDocument[1].setDocumentFilter(new DocumentFilter() {
         @Override
         public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.replace(offset, offset + length, text);

            if (isInt(sb.toString()) && sb.length() <= limitDefault) {
               System.out.println("(InputListener)(HoldDelay) string replaced");
               System.out.println(sb);
               super.replace(fb, offset, length, text, attrs);
            }
         }

         @Override
         public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.delete(offset, offset + length);

            if (sb.toString().isEmpty()) {
               super.replace(fb, offset, length, "", null);
            } else {
               if (isInt(sb.toString())) {
                  System.out.println("(InputListener)(HoldDelay) string Removed");
                  super.remove(fb, offset, length);
               }
            }
         }
      });
      holdDelayDocument[2].setDocumentFilter(new DocumentFilter() {
         @Override
         public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.replace(offset, offset + length, text);

            if (isInt(sb.toString()) && sb.length() <= limitDefault) {
               System.out.println("(InputListener)(HoldDelay) string replaced");
               System.out.println(sb);
               super.replace(fb, offset, length, text, attrs);
            }
         }

         @Override
         public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.delete(offset, offset + length);

            if (sb.toString().isEmpty()) {
               super.replace(fb, offset, length, "", null);
            } else {
               if (isInt(sb.toString())) {
                  System.out.println("(InputListener)(HoldDelay) string Removed");
                  super.remove(fb, offset, length);
               }
            }
         }
      });
      holdDelayDocument[3].setDocumentFilter(new DocumentFilter() {
         @Override
         public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.replace(offset, offset + length, text);

            if (isInt(sb.toString()) && sb.length() <= limitDefault) {
               System.out.println("(InputListener)(HoldDelay) string replaced");
               System.out.println(sb);
               super.replace(fb, offset, length, text, attrs);
            }
         }

         @Override
         public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.delete(offset, offset + length);

            if (sb.toString().isEmpty()) {
               super.replace(fb, offset, length, "", null);
            } else {
               if (isInt(sb.toString())) {
                  System.out.println("(InputListener)(HoldDelay) string Removed");
                  super.remove(fb, offset, length);
               }
            }
         }
      });

      PlainDocument[] clickRandomizeRangeDocument = new PlainDocument[2];
      for (int i = 0; i < clickRandomizeRangeDocument.length; i++) {
         clickRandomizeRangeDocument[i] = (PlainDocument) gui.getRandomizeRange()[i].getDocument();
      }
      clickRandomizeRangeDocument[0].setDocumentFilter(new DocumentFilter() {
         @Override
         public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.replace(offset, offset + length, text);

            if (isInt(sb.toString()) && sb.length() <= limitDefault) {
               System.out.println("(InputListener)(RandomizeClick) string replaced");
               System.out.println(sb);
               super.replace(fb, offset, length, text, attrs);
            }
         }

         @Override
         public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.delete(offset, offset + length);

            if (sb.toString().isEmpty()) {
               super.replace(fb, offset, length, "", null);
            } else {
               if (isInt(sb.toString())) {
                  System.out.println("(InputListener)(RandomizeClick) string Removed");
                  super.remove(fb, offset, length);
               }
            }
         }
      });
      clickRandomizeRangeDocument[1].setDocumentFilter(new DocumentFilter() {
         @Override
         public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.replace(offset, offset + length, text);

            if (isInt(sb.toString()) && sb.length() <= limitDefault) {
               System.out.println("(InputListener)(RandomizeHold) string replaced");
               System.out.println(sb);
               super.replace(fb, offset, length, text, attrs);
            }
         }

         @Override
         public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.delete(offset, offset + length);

            if (sb.toString().isEmpty()) {
               super.replace(fb, offset, length, "", null);
            } else {
               if (isInt(sb.toString())) {
                  System.out.println("(InputListener)(RandomizeHold) string Removed");
                  super.remove(fb, offset, length);
               }
            }
         }
      });
      PlainDocument clicksDocument = (PlainDocument) gui.getClickAmount().getDocument();
      clicksDocument.setDocumentFilter(new DocumentFilter() {
         @Override
         public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.replace(offset, offset + length, text);

            if (isInt(sb.toString()) && sb.length() <= limitClicks) {
               System.out.println("(InputListener)(ClickAmount) string replaced");
               System.out.println(sb);
               super.replace(fb, offset, length, text, attrs);
            }
         }

         @Override
         public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.delete(offset, offset + length);

            if (sb.toString().isEmpty()) {
               super.replace(fb, offset, length, "", null);
            } else {
               if (isInt(sb.toString())) {
                  System.out.println("(InputListener)(ClickAmount) string Removed");
                  super.remove(fb, offset, length);
               }
            }
         }
      });



      // add key and mouse listener #############################################################################
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
   }

   @Override
   public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
      int keyPressed = nativeEvent.getKeyCode();

      if (newHotkey) {
         newHotkey(nativeEvent);
      } else if (keyPressed == Settings.hotkey && !Settings.autoclickOnMouseHold) {
         toggleClicker();
      }
   }

   @Override
   public void nativeMousePressed(NativeMouseEvent nativeEvent) {
      if (!Settings.autoclickOnMouseHold) {
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
      if (!Settings.autoclickOnMouseHold) {
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
         registeredPressing = 0;
      }
   }

   /**
    * Saves the new hotkey
    *
    * @param e the key event
    */
   private void newHotkey(NativeKeyEvent e) {
      Settings.hotkey = e.getKeyCode();
      Settings.hotkeyText = NativeKeyEvent.getKeyText(Settings.hotkey);
      gui.getNewHotkeyButton().setText("Select Hotkey(" + Settings.hotkeyText + ")");
      newHotkey = false;
      System.out.println("(InputListener) New hotkey: " + Settings.hotkeyText);
   }

   /**
    * Tries to parse to int
    *
    * @param text to try to parse
    * @return if text is integer
    */
   private boolean isInt(String text) {
      try {
         Integer.parseInt(text);
         return true;
      } catch (NumberFormatException e) {
         return false;
      }
   }
}
