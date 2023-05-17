package main;

import GUI.GUI;
import GUI.HelpGUI;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;
import fileUtilities.FileHider;
import settings.Settings;

import javax.swing.text.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

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
      this.clicker = new Autoclicker();

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
            System.out.println("(InputListener) Autoclick on mouse hold: " + Settings.shouldAutoclickOnMouseHold());

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
            System.out.println("(InputListener) Should randomize click: " + Settings.shouldRandomizeClick());
         }
      });

      // should randomize hold
      gui.getShouldRandomize()[1].addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            Settings.setShouldRandomizeHold(gui.getShouldRandomize()[1].isSelected());
            System.out.println("(InputListener) Should randomize hold: " + Settings.shouldRandomizeHold());
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
            Settings.setButton(InputEvent.getMaskForButton(buttonNumber));
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
               if (onTextAdd(fb, offset, length, text, attrs, limitDefault)) {
                  Settings.setClickDelay(Integer.parseInt(fb.getDocument().getText(0, fb.getDocument().getLength())), finalI);
               }
            }
            @Override
            public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
               if (onTextRemove(fb, offset, length)) {
                  Settings.setClickDelay(Integer.parseInt(fb.getDocument().getText(0, fb.getDocument().getLength())), finalI);
               }
            }
         });
         holdDelayDocument[i].setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
               if (onTextAdd(fb, offset, length, text, attrs, limitDefault)) {
                  Settings.setHoldDelay(Integer.parseInt(fb.getDocument().getText(0, fb.getDocument().getLength())), finalI);
               }
            }
            @Override
            public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
               if(onTextRemove(fb, offset, length)) {
                  Settings.setHoldDelay(Integer.parseInt(fb.getDocument().getText(0, fb.getDocument().getLength())), finalI);
               }
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
               if(onTextAdd(fb, offset, length, text, attrs, limitDefault)) {
                  Settings.setRandomizeRange(Integer.parseInt(fb.getDocument().getText(0, fb.getDocument().getLength())), finalI);
               }
            }
            @Override
            public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
               if(onTextRemove(fb, offset, length)) {
                  Settings.setRandomizeRange(Integer.parseInt(fb.getDocument().getText(0, fb.getDocument().getLength())), finalI);
               }
            }
         });
      }

      // Click Amount TextField Filters
      PlainDocument clicksDocument = (PlainDocument) gui.getClickAmount().getDocument();
      clicksDocument.setDocumentFilter(new DocumentFilter() {
         @Override
         public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (onTextAdd(fb, offset, length, text, attrs, limitClicks)) {
               Settings.setClicks(Integer.parseInt(fb.getDocument().getText(0, fb.getDocument().getLength())));
            }
         }
         @Override
         public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
            if (onTextRemove(fb, offset, length)) {
               Settings.setClicks(Integer.parseInt(fb.getDocument().getText(0, fb.getDocument().getLength())));
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

   private boolean onTextAdd(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs, int maxSize) throws BadLocationException {
      Document doc = fb.getDocument();
      StringBuilder sb = new StringBuilder();
      sb.append(doc.getText(0, doc.getLength()));
      sb.replace(offset, offset + length, text);

      if (isInt(sb.toString()) && sb.length() <= maxSize) {
         fb.replace(offset, length, text, attrs);
         return true;
      }
      return false;
   }

   private boolean onTextRemove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
      Document doc = fb.getDocument();
      StringBuilder sb = new StringBuilder();
      sb.append(doc.getText(0, doc.getLength()));
      sb.delete(offset, offset + length);

      if (sb.toString().isEmpty()) {
         fb.replace(offset, length, "", null);
      } else if (isInt(sb.toString())) {
         fb.remove(offset, length);
         return true;
      }
      return false;
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
      }
   }

   @Override
   public void nativeMouseReleased(NativeMouseEvent nativeEvent) {
      if (!Settings.shouldAutoclickOnMouseHold()) {
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
      clicker = new Autoclicker();
      clicker.start();
      gui.setTitle(GUI.MAIN_FRAME_TITLE + "  -  Clicking");
   }

   /**
    * Stops the clicker, sets registeredPressing to 0 and updates the gui title.
    */
   private void stopClicker() {
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
      System.out.println("(InputListener) New hotkey: " + Settings.getHotkeyText());
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
