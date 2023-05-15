package GUI;

import resources.Constants;
import textFieldFilter.TextFieldFilter;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Class for making all JComponents in this project
 */
public class JComponentFactory {
   // colors
   private static final Color TEXT_FIELD_COLOR = new Color(255, 255, 255);
   private static final Border TEXT_FIELD_BORDER = BorderFactory.createLineBorder(TEXT_FIELD_COLOR, 2);

   /**
    * Makes panel array
    *
    * @param coordinates cords and size of the panels
    * @return the panels made
    */
   public JPanel[] panelFactory(int[][] coordinates) {
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
   public JLabel[] labelFactory(String internalText, boolean center, boolean background, int[][] coordinates) {
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
    * @param value         what is the value of the text field
    * @param coordinates    coordinates and size of the text fields
    * @return the text fields made
    */
   public JTextField[] textFieldFactory(int textFieldLimit, int[] value, int[][] coordinates) {
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
    * @param value        what is the value of the text field
    * @param coordinates    coordinates and size of the text fields
    * @return the text fields made
    */
   public JTextField textFieldFactory(int textFieldLimit, int value, int[] coordinates) {
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
   public JCheckBox[] checkBoxFactory(boolean[] value, int[][] coordinates) {
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
    * @param value      what is the value of the combobox
    * @param coordinates coordinates and size of the combobox
    * @return the combobox
    */
   public JComboBox<String> comboBoxFactory(String[] options, int value, int[] coordinates) {
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
