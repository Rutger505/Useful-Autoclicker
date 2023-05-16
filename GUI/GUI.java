package GUI;

import resources.Constants;
import settings.Settings;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * GUI where settings can be changed
 */
public class GUI extends JFrame {
   // title/version
   private static final double AUTOCLICKER_VERSION = 2.1;
   public static final String MAIN_FRAME_TITLE = "Useful Autoclicker " + AUTOCLICKER_VERSION;

   // sizes
   private static final int MAIN_FRAME_WIDTH = 400;
   private static final int MAIN_FRAME_HEIGHT = 330;
   private static final int MAIN_FRAME_ACTUAL_WIDTH = 384;

   private static final int TEXT_FIELD_WIDTH = 35;
   private static final int TEXT_FIELD_HEIGHT = 20;

   // components
   private final JTextField[] clickDelayTF;
   private final JTextField[] holdDelayTF;
   private final JCheckBox[] shouldRandomizeCB;
   private final JTextField[] randomizeRangeTF;
   private final JTextField clickAmountTF;
   private final JComboBox<String> buttonSelectCB;

   private final JButton helpButton;
   private final JButton newHotkeyButton;
   private final JButton defaultsButton;
   private final JCheckBox autoclickOnMouseHoldCheckBox;

   /**
    * Makes GUI
    */
   public GUI() {
      JComponentFactory components = new JComponentFactory();

      // label sizes
      int labelHeight = 20;

      int timeIdentifierLabelWidth = 20;

      // y levels of components
      int clickDelayItemsY = 35;
      int holdDelayItemsY = 75;
      int randomizeItemsY = 145;
      int clicksItemsY = 215;
      int clicksItems2Y = 255;


      newHotkeyButton = components.buttonFactory("Select Hotkey(" + Settings.getHotkeyText() + ")", null, new int[]{20, 210, 165, 30});
      add(newHotkeyButton);


      // used throughout the GUI ########################################################
      // backgrounds
      JPanel[] backgrounds = components.panelFactory(new int[][]{{20, 140, 165, 30}, {195, 140, 165, 30}, {20, 250, 95, 30}, {195, 210, 155, 30}, {20, 30, 340, 30}, {20, 70, 340, 30}});
      // time identifiers
      JLabel[] millisecondsL = components.labelFactory("ms", true, false, new int[][]{{328, clickDelayItemsY, timeIdentifierLabelWidth, labelHeight}, {328, holdDelayItemsY, timeIdentifierLabelWidth, labelHeight}, {153, randomizeItemsY, timeIdentifierLabelWidth, labelHeight}, {328, randomizeItemsY, timeIdentifierLabelWidth, labelHeight}});

      JLabel[] secondL = components.labelFactory("s", true, false, new int[][]{{268, clickDelayItemsY, timeIdentifierLabelWidth, labelHeight}, {268, holdDelayItemsY, timeIdentifierLabelWidth, labelHeight}});

      JLabel[] minutesL = components.labelFactory("m", true, false, new int[][]{{208, clickDelayItemsY, timeIdentifierLabelWidth, labelHeight}, {208, holdDelayItemsY, timeIdentifierLabelWidth, labelHeight}});

      JLabel[] hourL = components.labelFactory("h", true, false, new int[][]{{148, clickDelayItemsY, timeIdentifierLabelWidth, labelHeight}, {148, holdDelayItemsY, timeIdentifierLabelWidth, labelHeight}});


      // first section ########################################################
      JLabel delayLabel = components.labelFactory("Click interval/Hold time", true, true, new int[]{0, 0, MAIN_FRAME_ACTUAL_WIDTH, labelHeight + 5});

      Color topButtonBorderColor = new Color(200, 200, 200);
      Border topButtonBorder = BorderFactory.createLineBorder(topButtonBorderColor, 1);
      defaultsButton = components.buttonFactory("Defaults", topButtonBorder, new int[]{10, 4, 60, 17});
      helpButton = components.buttonFactory("?", topButtonBorder, new int[]{360, 4, 17, 17});

      JLabel clickDelayL = components.labelFactory("Click interval:", false, false, new int[]{30, clickDelayItemsY, 80, labelHeight});
      clickDelayTF = components.textFieldFactory(Settings.getClickDelayArray(), new int[][]{{290, clickDelayItemsY, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT}, {230, clickDelayItemsY, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT}, {170, clickDelayItemsY, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT}, {110, clickDelayItemsY, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT}});

      JLabel holdDelayL = components.labelFactory("Hold time:", false, false, new int[]{30, holdDelayItemsY, 80, labelHeight});
      holdDelayTF = components.textFieldFactory(Settings.getHoldDelayArray(), new int[][]{{290, holdDelayItemsY, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT}, {230, holdDelayItemsY, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT}, {170, holdDelayItemsY, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT}, {110, holdDelayItemsY, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT}});

      // second section ########################################################
      JLabel randomizeLabel = components.labelFactory("Randomize click interval", true, true, new int[]{0, 110, MAIN_FRAME_ACTUAL_WIDTH, labelHeight});

      JLabel clickRandomizeL = components.labelFactory("Click inter:", false, false, new int[]{30, randomizeItemsY, 80, labelHeight});
      JLabel holdRandomizeL = components.labelFactory("Hold time:", false, false, new int[]{200, randomizeItemsY, 80, labelHeight});

      shouldRandomizeCB = components.checkBoxFactory(new boolean[]{Settings.shouldRandomizeClick(), Settings.shouldRandomizeHold()}, new int[][]{{95, randomizeItemsY, 15, 20}, {270, randomizeItemsY, 15, 20}});

      randomizeRangeTF = components.textFieldFactory(new int[]{Settings.getClickRandomizeRange(), Settings.getHoldRandomizeRange()}, new int[][]{{115, randomizeItemsY, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT}, {290, randomizeItemsY, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT}});

      // third section ########################################################
      JLabel miscellaneousLabel = components.labelFactory("Clicks/Button/Hotkey", true, true, new int[]{0, 180, MAIN_FRAME_ACTUAL_WIDTH, labelHeight});

      JLabel clickAmountL = components.labelFactory("Clicks:", false, false, new int[]{30, clicksItems2Y, 80, labelHeight});
      clickAmountTF = components.textFieldFactory(Settings.getClicks(), new int[]{70, clicksItems2Y, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT});

      JLabel buttonSelectL = components.labelFactory("Button:", false, false, new int[]{205, clicksItemsY, 80, labelHeight});
      String[] buttonSelectOptions = {"left", "right", "middle", "side front", "side back"};
      buttonSelectCB = components.comboBoxFactory(buttonSelectOptions, Settings.getButtonNumber(), new int[]{250, clicksItemsY, 90, 20});

      // autoclick on hold
      JLabel autoclickOnMouseHoldLabel = components.labelFactory("Autoclick on button hold:", false, false, new int[]{135, 255, 190, 20});
      autoclickOnMouseHoldCheckBox = components.checkBoxFactory(Settings.shouldAutoclickOnMouseHold(), new int[]{275, 255, 15, 20});
      JPanel autoclickOnMouseHoldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 9));
      autoclickOnMouseHoldPanel.setBounds(125, 250, 175, 30);
      autoclickOnMouseHoldPanel.add(autoclickOnMouseHoldLabel);
      autoclickOnMouseHoldPanel.add(autoclickOnMouseHoldCheckBox);


      add(autoclickOnMouseHoldPanel);
      add(defaultsButton);

      // adding components ########################################################
      // adding text fields
      add(clickDelayTF);
      add(holdDelayTF);
      add(randomizeRangeTF);
      add(clickAmountTF);

      // adding buttons
      add(helpButton);

      // adding checkboxes
      add(shouldRandomizeCB);

      // adding dropdown
      add(buttonSelectCB);

      // adding section labels
      add(delayLabel);
      add(randomizeLabel);
      add(miscellaneousLabel);

      // adding text labels
      add(clickDelayL);
      add(holdDelayL);
      add(clickRandomizeL);
      add(holdRandomizeL);
      add(clickAmountL);
      add(buttonSelectL);

      // adding time identifiers
      add(millisecondsL);
      add(secondL);
      add(minutesL);
      add(hourL);

      // adding backgrounds
      add(backgrounds);

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
    * adds component to frame
    *
    * @param component component to add
    */
   public void addComp(JComponent component) {
      this.add(component);
      if (this.isVisible()) {
         this.repaint();
         this.setVisible(true);
      }
   }

   /**
    * adds component array to frame
    *
    * @param componentArray component array to add
    */
   private void add(JComponent[] componentArray) {
      for (Component component : componentArray) {
         this.add(component);
      }
      if (this.isVisible()) {
         this.repaint();
      }
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

   /**
    * @return autoclick on mouse hold checkbox
    */
   public JCheckBox getAutoclickOnMouseHold() {
      return autoclickOnMouseHoldCheckBox;
   }

   /**
    * @return new hotkey button
    */
   public JButton getNewHotkeyButton() {
      return newHotkeyButton;
   }

   /**
    * @return help button
    */
   public JButton getHelpButton() {
      return helpButton;
   }

   /**
    * @return defaults button
    */
   public JButton getDefaultsButton() {
      return defaultsButton;
   }
}