package main;

import GUI.GUI;
import GUI.HelpGUI;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;
import errorHandeling.Error;
import fileUtilities.FileHider;
import settings.Settings;

import javax.swing.text.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputListener implements NativeKeyListener, NativeMouseListener {
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
      this.clicker = new Autoclicker(this);

      // default button

      gui.getDefaultsButton().addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            // click and hold delay
            for (int i = 0; i < gui.getClickDelay().length; i++) {
               gui.getClickDelay()[i].setText("0");
               gui.getHoldDelay()[i].setText("0");
            }
            gui.getClickDelay()[0].setText("100");
            gui.getHoldDelay()[0].setText("10");

            Settings.setClickDelay(new int[]{100, 0, 0, 0});
            Settings.setHoldDelay(new int[]{10, 0, 0, 0});

            // randomize the click and hold delay.
            for (int i = 0; i < gui.getRandomizeRange().length; i++) {
               gui.getRandomizeRange()[i].setText("20");
               gui.getShouldRandomize()[i].setSelected(false);
            }
            Settings.setClickRandomizeRange(20);
            Settings.setHoldRandomizeRange(20);
            Settings.setShouldRandomizeClick(false);
            Settings.setShouldRandomizeHold(false);

            // misc
            gui.getClickAmount().setText("0");
            Settings.setClicks(0);

            gui.getButtonSelect().setSelectedIndex(0);
            Settings.setButtonNumber(0);

            gui.getAutoclickOnMouseHold().setSelected(false);
            Settings.setAutoclickOnMouseHold(false);
         }
      });

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
            Settings.setAutoclickOnMouseHold(gui.getAutoclickOnMouseHold().isSelected());

            if (!Settings.shouldAutoclickOnMouseHold()) {
               toggleClicker(false);
            }
         }
      });

      // should randomize click
      gui.getShouldRandomize()[0].addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            Settings.setShouldRandomizeClick(gui.getShouldRandomize()[0].isSelected());
         }
      });

      // should randomize hold
      gui.getShouldRandomize()[1].addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            Settings.setShouldRandomizeHold(gui.getShouldRandomize()[1].isSelected());
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

      // combo box (button select)
      gui.getButtonSelect().addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            Settings.setButtonNumber(gui.getButtonSelect().getSelectedIndex());
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


      // document Filters ##################################################################################################
      int limitDefault = 4;
      int limitClicks = 5;

      // Hold and Click Delay TextField Filters
      PlainDocument[] clickDelayDocument = new PlainDocument[4];
      PlainDocument[] holdDelayDocument = new PlainDocument[4];
      for (int i = 0; i < clickDelayDocument.length; i++) {
         clickDelayDocument[i] = (PlainDocument) gui.getClickDelay()[i].getDocument();
         holdDelayDocument[i] = (PlainDocument) gui.getHoldDelay()[i].getDocument();
         int finalI = i;

         clickDelayDocument[i].setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
               onTextAdd(fb, offset, length, text, attrs, limitDefault);
               Settings.setClickDelay(advancedParseInt(fb.getDocument().getText(0, fb.getDocument().getLength())), finalI);

            }

            @Override
            public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
               onTextRemove(fb, offset, length);
               Settings.setClickDelay(advancedParseInt(fb.getDocument().getText(0, fb.getDocument().getLength())), finalI);

            }
         });
         holdDelayDocument[i].setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
               onTextAdd(fb, offset, length, text, attrs, limitDefault);
               Settings.setHoldDelay(advancedParseInt(fb.getDocument().getText(0, fb.getDocument().getLength())), finalI);

            }

            @Override
            public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
               onTextRemove(fb, offset, length);
               Settings.setHoldDelay(advancedParseInt(fb.getDocument().getText(0, fb.getDocument().getLength())), finalI);

            }
         });
      }

      // Randomize Range TextField Filters
      PlainDocument[] clickRandomizeRangeDocument = new PlainDocument[2];
      for (int i = 0; i < clickRandomizeRangeDocument.length; i++) {
         clickRandomizeRangeDocument[i] = (PlainDocument) gui.getRandomizeRange()[i].getDocument();
         int finalI = i;
         clickRandomizeRangeDocument[i].setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
               onTextAdd(fb, offset, length, text, attrs, limitDefault);
               Settings.setRandomizeRange(advancedParseInt(fb.getDocument().getText(0, fb.getDocument().getLength())), finalI);

            }

            @Override
            public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
               onTextRemove(fb, offset, length);
               Settings.setRandomizeRange(advancedParseInt(fb.getDocument().getText(0, fb.getDocument().getLength())), finalI);

            }
         });
      }

      // Click Amount TextField Filters
      PlainDocument clicksDocument = (PlainDocument) gui.getClickAmount().getDocument();
      clicksDocument.setDocumentFilter(new DocumentFilter() {
         @Override
         public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            onTextAdd(fb, offset, length, text, attrs, limitClicks);
            Settings.setClicks(advancedParseInt(fb.getDocument().getText(0, fb.getDocument().getLength())));

         }

         @Override
         public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
            onTextRemove(fb, offset, length);
            Settings.setClicks(advancedParseInt(fb.getDocument().getText(0, fb.getDocument().getLength())));

         }
      });

      // add key and mouse listener #############################################################################
      try {
         GlobalScreen.registerNativeHook();
      } catch (NativeHookException e) {
         Error.showError("HotkeyListener", "The hotkeyListener was unable to start. Try restarting the program.", "(InputListener) JNativeHook could not be started");
         e.printStackTrace();
      }
      GlobalScreen.addNativeKeyListener(this);
      GlobalScreen.addNativeMouseListener(this);

      // hide JNativeHook file
      new FileHider("JNativeHook.x86_64.dll");
   }

   /**
    * Tries to parse it to an int, if it fails it returns 0/
    *
    * @param text String to parse
    * @return String parsed or 0
    */
   private int advancedParseInt(String text) {
      try {
         return Integer.parseInt(text);
      } catch (NumberFormatException e) {
         return 0;
      }
   }

   @Override
   public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
      int keyPressed = nativeEvent.getKeyCode();

      if (newHotkey) {
         newHotkey(nativeEvent);
      } else if (keyPressed == Settings.getHotkey() && !Settings.shouldAutoclickOnMouseHold()) {
         toggleClicker();
      }
   }

   @Override
   public void nativeMousePressed(NativeMouseEvent nativeEvent) {
      if (!Settings.shouldAutoclickOnMouseHold()) {
         return;
      }
      mouseButtonSelected = gui.getButtonSelect().getSelectedIndex() + 1;
      int buttonPressed = nativeEvent.getButton();

      if (buttonPressed == mouseButtonSelected) {
         if (registeredPressing == 0) {
            toggleClicker(true);
         }
         registeredPressing++;

         if (registeredPressing > 2) {
            registeredPressing = 2;
         }
      }
   }

   @Override
   public void nativeMouseReleased(NativeMouseEvent nativeEvent) {
      if (!Settings.shouldAutoclickOnMouseHold()) {
         return;
      }
      int buttonPressed = nativeEvent.getButton();

      if (buttonPressed == mouseButtonSelected) {
         if(registeredPressing < 1){
            registeredPressing = 1;
         }

         registeredPressing--;

         if (registeredPressing == 0) {
            toggleClicker(false);
         }
      }
   }

   /**
    * Adds new string to the textField if it is an integer and total string size is less than maxSize.
    *
    * @param fb      FilterBypass
    * @param offset  idk
    * @param length  idk
    * @param text    string to add
    * @param attrs   idk
    * @param maxSize max size of the string
    * @throws BadLocationException idk
    */
   private void onTextAdd(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs, int maxSize) throws BadLocationException {
      Document doc = fb.getDocument();
      StringBuilder sb = new StringBuilder();
      sb.append(doc.getText(0, doc.getLength()));
      sb.replace(offset, offset + length, text);

      if (isInt(sb.toString()) && sb.length() <= maxSize) {
         fb.replace(offset, length, text, attrs);
      }
   }

   /**
    * Removes text from the textField.
    *
    * @param fb     FilterBypass
    * @param offset idk
    * @param length idk
    * @throws BadLocationException idk
    */
   private void onTextRemove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
      Document doc = fb.getDocument();
      StringBuilder sb = new StringBuilder();
      sb.append(doc.getText(0, doc.getLength()));
      sb.delete(offset, offset + length);

      if (sb.toString().isEmpty()) {
         fb.replace(offset, length, "", null);
      } else if (isInt(sb.toString())) {
         fb.remove(offset, length);
      }
   }

   /**
    * Toggles the clicker
    */
   private void toggleClicker() {
      if (!clicker.isRunning()) {
         startClicker();
      } else {
         stopClicker();
      }
   }

   /**
    * Toggles the clicker to a specific state
    *
    * @param activate true to activate, false to deactivate clicker
    */
   private void toggleClicker(boolean activate) {
      if (activate && !clicker.isRunning()) {
         startClicker();
      } else if (!activate && clicker.isRunning()) {
         stopClicker();
      }
   }

   /**
    * Starts the clicker and updates the gui title.
    */
   private void startClicker() {
      clicker = new Autoclicker(this);
      clicker.start();
      gui.setTitle(GUI.MAIN_FRAME_TITLE + "  -  Clicking");
   }

   /**
    * Stops the clicker, sets registeredPressing to 0 and updates the gui title.
    */
   public void stopClicker() {
      clicker.stopClicker();
      registeredPressing = 0;
      gui.setTitle(GUI.MAIN_FRAME_TITLE + "  -  Stopped");
   }

   /**
    * Saves the new hotkey
    *
    * @param e the key event
    */
   private void newHotkey(NativeKeyEvent e) {
      Settings.setHotkey(e.getKeyCode());
      gui.getNewHotkeyButton().setText("Select Hotkey(" + Settings.getHotkeyText() + ")");
      newHotkey = false;
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
