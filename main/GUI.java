package main;

import fileUtilities.ClickerData;
import resources.Constants;
import textFieldFilter.TextFieldFilter;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI where settings can be changed
 */
public class GUI extends JFrame implements ActionListener {
   private final HelpGUI helpGUI;

   // title/version
   private static final double AUTOCLICKER_VERSION = 2.1;
   static final String MAIN_FRAME_TITLE = "Useful Autoclicker " + AUTOCLICKER_VERSION;

   // sizes
   private static final int MAIN_FRAME_WIDTH = 400;
   private static final int MAIN_FRAME_HEIGHT = 330;
   private static final int MAIN_FRAME_ACTUAL_WIDTH = 384;

   private static final int TEXT_FIELD_WIDTH = 35;
   private static final int TEXT_FIELD_HEIGHT = 20;

   // colors
   private static final Color TEXT_FIELD_COLOR = new Color(255, 255, 255);
   private static final Border TEXT_FIELD_BORDER = BorderFactory.createLineBorder(TEXT_FIELD_COLOR, 2);


   // components
   private final JTextField[] clickDelayTF;
   private final JTextField[] holdDelayTF;
   private final JCheckBox[] shouldRandomizeCB;
   private final JTextField[] randomizeRangeTF;
   private final JTextField clickAmountTF;
   private final JComboBox<String> buttonSelectCB;
   private final JButton helpButton;

   /**
    * Makes GUI
    */
   public GUI() {
      helpGUI = new HelpGUI(this);
      ClickerData clickerData = new ClickerData();

      // label sizes
      int labelHeight = 20;

      int timeIdentifierLabelWidth = 20;

      // y levels of components
      int clickDelayItemsY = 35;
      int holdDelayItemsY = 75;
      int randomizeItemsY = 145;
      int clicksItemsY = 215;
      int clicksItems2Y = 255;

      // used throughout the GUI ########################################################
      // backgrounds
      JPanel[] backgrounds = panelFactory(new int[][]{{20, 140, 165, 30}, {195, 140, 165, 30}, {20, 250, 95, 30}, {195, 210, 155, 30}, {20, 30, 340, 30}, {20, 70, 340, 30}});
      // time identifiers
      JLabel[] millisecondsL = labelFactory("ms", true, false, new int[][]{{328, clickDelayItemsY, timeIdentifierLabelWidth, labelHeight}, {328, holdDelayItemsY, timeIdentifierLabelWidth, labelHeight}, {153, randomizeItemsY, timeIdentifierLabelWidth, labelHeight}, {328, randomizeItemsY, timeIdentifierLabelWidth, labelHeight}});

      JLabel[] secondL = labelFactory("s", true, false, new int[][]{{268, clickDelayItemsY, timeIdentifierLabelWidth, labelHeight}, {268, holdDelayItemsY, timeIdentifierLabelWidth, labelHeight}});

      JLabel[] minutesL = labelFactory("m", true, false, new int[][]{{208, clickDelayItemsY, timeIdentifierLabelWidth, labelHeight}, {208, holdDelayItemsY, timeIdentifierLabelWidth, labelHeight}});

      JLabel[] hourL = labelFactory("h", true, false, new int[][]{{148, clickDelayItemsY, timeIdentifierLabelWidth, labelHeight}, {148, holdDelayItemsY, timeIdentifierLabelWidth, labelHeight}});


      // first section ########################################################
      JLabel delayLabel = labelFactory("Click interval/Hold time", true, true, new int[]{0, 0, MAIN_FRAME_ACTUAL_WIDTH, labelHeight + 5});

      Color helpButtonBorderColor = new Color(200, 200, 200);
      Border helpButtonBorder = BorderFactory.createLineBorder(helpButtonBorderColor, 1);
      helpButton = buttonFactory("?", helpButtonBorder, this, new int[]{360, 4, 17, 17});

      JLabel clickDelayL = labelFactory("Click interval:", false, false, new int[]{30, clickDelayItemsY, 80, labelHeight});
      clickDelayTF = textFieldFactory(4, clickerData.getClickDelay(), new int[][]{{290, clickDelayItemsY, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT}, {230, clickDelayItemsY, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT}, {170, clickDelayItemsY, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT}, {110, clickDelayItemsY, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT}});

      JLabel holdDelayL = labelFactory("Hold time:", false, false, new int[]{30, holdDelayItemsY, 80, labelHeight});
      holdDelayTF = textFieldFactory(4, clickerData.getHoldDelay(), new int[][]{{290, holdDelayItemsY, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT}, {230, holdDelayItemsY, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT}, {170, holdDelayItemsY, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT}, {110, holdDelayItemsY, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT}});

      // second section ########################################################
      JLabel randomizeLabel = labelFactory("Randomize click interval", true, true, new int[]{0, 110, MAIN_FRAME_ACTUAL_WIDTH, labelHeight});

      JLabel clickRandomizeL = labelFactory("Click inter:", false, false, new int[]{30, randomizeItemsY, 80, labelHeight});
      JLabel holdRandomizeL = labelFactory("Hold time:", false, false, new int[]{200, randomizeItemsY, 80, labelHeight});

      shouldRandomizeCB = checkBoxFactory(clickerData.getRandomizeDelay(), new int[][]{{95, randomizeItemsY, 15, 20}, {270, randomizeItemsY, 15, 20}});

      randomizeRangeTF = textFieldFactory(4, clickerData.getRandomizeRange(), new int[][]{{115, randomizeItemsY, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT}, {290, randomizeItemsY, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT}});

      // third section ########################################################
      JLabel miscellaneousLabel = labelFactory("Clicks/Button/Hotkey", true, true, new int[]{0, 180, MAIN_FRAME_ACTUAL_WIDTH, labelHeight});

      JLabel clickAmountL = labelFactory("Clicks:", false, false, new int[]{30, clicksItems2Y, 80, labelHeight});
      clickAmountTF = textFieldFactory(5, clickerData.getClicks(), new int[]{70, clicksItems2Y, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT});

      JLabel buttonSelectL = labelFactory("Button:", false, false, new int[]{205, clicksItemsY, 80, labelHeight});
      String[] buttonSelectOptions = {"left", "right", "middle", "side front", "side back"};
      buttonSelectCB = comboBoxFactory(buttonSelectOptions, clickerData.getButton(), new int[]{250, clicksItemsY, 90, 20});


      // adding components ########################################################
      // adding text fields
      addComponent(clickDelayTF, this);
      addComponent(holdDelayTF, this);
      addComponent(randomizeRangeTF, this);
      addComponent(clickAmountTF, this);

      // adding buttons
      addComponent(helpButton, this);

      // adding checkboxes
      addComponent(shouldRandomizeCB, this);

      // adding dropdown
      addComponent(buttonSelectCB, this);

      // adding section labels
      addComponent(delayLabel, this);
      addComponent(randomizeLabel, this);
      addComponent(miscellaneousLabel, this);

      // adding text labels
      addComponent(clickDelayL, this);
      addComponent(holdDelayL, this);
      addComponent(clickRandomizeL, this);
      addComponent(holdRandomizeL, this);
      addComponent(clickAmountL, this);
      addComponent(buttonSelectL, this);

      // adding time identifiers
      addComponent(millisecondsL, this);
      addComponent(secondL, this);
      addComponent(minutesL, this);
      addComponent(hourL, this);

      // adding backgrounds
      addComponent(backgrounds, this);

      // frame itself
      setIconImage(Constants.FRAME_ICON);
      setTitle(MAIN_FRAME_TITLE);
      setSize(MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT);
      setResizable(false);
      getContentPane().setBackground(Constants.FRAME_COLOR);
      setLayout(null);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      setVisible(true);
   }

   /**
    * @return click delay text fields
    */
   public JTextField[] getClickDelay() {
      return clickDelayTF;
   }

   /**
    * @return hold delay text fields
    */
   public JTextField[] getHoldDelay() {
      return holdDelayTF;
   }

   /**
    * @return should randomize checkboxes
    */
   public JCheckBox[] getShouldRandomize() {
      return shouldRandomizeCB;
   }

   /**
    * @return randomize range text fields
    */
   public JTextField[] getRandomizeRange() {
      return randomizeRangeTF;
   }

   /**
    * @return click amount text fields
    */
   public JTextField getClickAmount() {
      return clickAmountTF;
   }

   /**
    * @return button select combo box
    */
   public JComboBox<String> getButtonSelect() {
      return buttonSelectCB;
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == helpButton) {
         helpGUI.setLocationRelativeTo(this);
         helpGUI.setVisible(true);
      }
   }

   /**
    * adds component to frame
    *
    * @param component component to add
    * @param frame     frame to add to
    */
   public void addComponent(JComponent component, JFrame frame) {
      frame.add(component);
      if (frame.isVisible()) {
         frame.repaint();
         frame.setVisible(true);
      }
   }

   /**
    * adds component array to frame
    *
    * @param componentArray component array to add
    * @param frame          frame to add to
    */
   private void addComponent(JComponent[] componentArray, JFrame frame) {
      for (Component component : componentArray) {
         frame.add(component);
      }
      if (frame.isVisible()) {
         frame.repaint();
      }
   }

   /**
    * Makes panel array
    *
    * @param coordinates cords and size of the panels
    * @return the panels made
    */
   private JPanel[] panelFactory(int[][] coordinates) {
      JPanel[] panel = new JPanel[coordinates.length];
      for (int i = 0; i < panel.length; i++) {
         panel[i] = new JPanel();
         panel[i].setBackground(Constants.DEFAULT_BACKGROUND_COLOR);
         panel[i].setOpaque(true);
         panel[i].setBounds(coordinates[i][0], coordinates[i][1], coordinates[i][2], coordinates[i][3]);
      }
      return panel;
   }

   /**
    * Makes label array
    *
    * @param internalText what do the labels say?
    * @param center       do you want the text centered in labels
    * @param background   show default background?
    * @param coordinates  coordinates and size of the labels
    * @return the label
    */
   private JLabel[] labelFactory(String internalText, boolean center, boolean background, int[][] coordinates) {
      JLabel[] label = new JLabel[coordinates.length];
      for (int i = 0; i < label.length; i++) {
         label[i] = new JLabel();
         label[i].setText(internalText);
         label[i].setFont(Constants.DEFAULT_FONT);
         label[i].setBackground(Constants.DEFAULT_BACKGROUND_COLOR);
         if (center) {
            label[i].setHorizontalAlignment(SwingConstants.CENTER);
            label[i].setVerticalAlignment(SwingConstants.CENTER);
         }
         label[i].setOpaque(background);
         label[i].setBounds(coordinates[i][0], coordinates[i][1], coordinates[i][2], coordinates[i][3]);
      }
      return label;
   }

   /**
    * Makes label
    *
    * @param internalText what does the label say?
    * @param center       do you want the text centered in label
    * @param background   show default background?
    * @param coordinates  coordinates and size of the labels
    * @return the label
    */
   public JLabel labelFactory(String internalText, boolean center, boolean background, int[] coordinates) {
      JLabel label = new JLabel();
      label.setText(internalText);
      label.setFont(Constants.DEFAULT_FONT);
      label.setBackground(Constants.DEFAULT_BACKGROUND_COLOR);
      if (center) {
         label.setHorizontalAlignment(SwingConstants.CENTER);
         label.setVerticalAlignment(SwingConstants.CENTER);
      }
      label.setOpaque(background);
      label.setBounds(coordinates[0], coordinates[1], coordinates[2], coordinates[3]);
      return label;
   }

   /**
    * Makes text field array
    *
    * @param textFieldLimit how many characters can be in the textField
    * @param coordinates    coordinates and size of the text fields
    * @return the text fields made
    */
   private JTextField[] textFieldFactory(int textFieldLimit, int[] value, int[][] coordinates) {
      JTextField[] textField = new JTextField[coordinates.length];
      for (int i = 0; i < textField.length; i++) {
         textField[i] = new JTextField();
         textField[i].setBorder(TEXT_FIELD_BORDER);
         textField[i].setBackground(TEXT_FIELD_COLOR);
         textField[i].setText(String.valueOf(value[i]));
         textField[i].setBounds(coordinates[i][0], coordinates[i][1], coordinates[i][2], coordinates[i][3]);
         PlainDocument document = (PlainDocument) (textField[i].getDocument());
         document.setDocumentFilter(new TextFieldFilter(textFieldLimit));
      }
      return textField;
   }

   /**
    * Makes text field
    *
    * @param textFieldLimit how many characters can be in the textField
    * @param coordinates    coordinates and size of the text fields
    * @return the text fields made
    */
   private JTextField textFieldFactory(int textFieldLimit, int value, int[] coordinates) {
      JTextField textField = new JTextField();
      textField.setBorder(TEXT_FIELD_BORDER);
      textField.setBackground(TEXT_FIELD_COLOR);
      textField.setText(String.valueOf(value));
      textField.setBounds(coordinates[0], coordinates[1], coordinates[2], coordinates[3]);
      PlainDocument document = (PlainDocument) (textField.getDocument());
      document.setDocumentFilter(new TextFieldFilter(textFieldLimit));
      return textField;
   }

   /**
    * Makes checkbox array
    *
    * @param value      what is the value of the checkboxes
    * @param coordinates coordinates and size of the checkboxes
    * @return the checkbox array
    */
   private JCheckBox[] checkBoxFactory(boolean[] value, int[][] coordinates) {
      JCheckBox[] checkBox = new JCheckBox[coordinates.length];
      for (int i = 0; i < checkBox.length; i++) {
         checkBox[i] = new JCheckBox();
         checkBox[i].setSelected(value[i]);
         checkBox[i].setBorder(null);
         checkBox[i].setOpaque(false);
         checkBox[i].setBounds(coordinates[i][0], coordinates[i][1], coordinates[i][2], coordinates[i][3]);
      }
      return checkBox;
   }

   /**
    * Makes checkbox
    *
    * @param actionListener action listener for the checkbox
    * @param value         what is the value of the checkboxes
    * @param coordinates coordinates and size of the checkboxes
    * @return the checkbox
    */
   public JCheckBox checkBoxFactory(ActionListener actionListener, boolean value, int[] coordinates) {
      JCheckBox checkBox = new JCheckBox();
      checkBox.addActionListener(actionListener);
      checkBox.setSelected(value);
      checkBox.setBorder(null);
      checkBox.setOpaque(false);
      checkBox.setBounds(coordinates[0], coordinates[1], coordinates[2], coordinates[3]);
      return checkBox;
   }

   /**
    * Makes a Button
    *
    * @param internalText what to put in button
    * @param border       what border add
    * @param actionListener action listener for the button
    * @param coordinates  coordinates and size of the button
    * @return the button
    */
   public JButton buttonFactory(String internalText, Border border, ActionListener actionListener, int[] coordinates) {
      JButton button = new JButton();
      button.setBackground(Constants.DEFAULT_BACKGROUND_COLOR);
      button.setFont(Constants.DEFAULT_FONT);
      button.setText(internalText);
      button.setBorder(border);
      button.setFocusable(false);
      button.addActionListener(actionListener);
      button.setCursor(new Cursor(Cursor.HAND_CURSOR));
      button.setOpaque(true);
      button.setBounds(coordinates[0], coordinates[1], coordinates[2], coordinates[3]);
      return button;
   }

   /**
    * Makes a combobox (dropdown with options)
    *
    * @param options     what options to put in the dropdown
    * @param coordinates coordinates and size of the combobox
    * @return the combobox
    */
   private JComboBox<String> comboBoxFactory(String[] options, int value, int[] coordinates) {
      JComboBox<String> comboBox = new JComboBox<>(options);
      comboBox.setSelectedIndex(value);
      comboBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
      comboBox.setBackground(Constants.FRAME_COLOR);
      comboBox.setFocusable(false);
      comboBox.setFont(Constants.DEFAULT_FONT);
      comboBox.setBounds(coordinates[0], coordinates[1], coordinates[2], coordinates[3]);
      return comboBox;
   }
}