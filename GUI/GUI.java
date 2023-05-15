package GUI;

import fileUtilities.ClickerData;
import resources.Constants;

import javax.swing.*;
import javax.swing.border.Border;
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
   private final JButton defaultsButton;
   private final JCheckBox autoclickOnMouseHoldCheckBox;

   /**
    * Makes GUI
    */
   public GUI() {
      JComponentFactory components = new JComponentFactory();
      helpGUI = new HelpGUI();
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
      defaultsButton = components.buttonFactory("Defaults", topButtonBorder, this, new int[]{10, 4, 60, 17});
      add(defaultsButton);
      helpButton = components.buttonFactory("?", topButtonBorder, this, new int[]{360, 4, 17, 17});

      JLabel clickDelayL = components.labelFactory("Click interval:", false, false, new int[]{30, clickDelayItemsY, 80, labelHeight});
      clickDelayTF = components.textFieldFactory(4, clickerData.getClickDelay(), new int[][]{{290, clickDelayItemsY, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT}, {230, clickDelayItemsY, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT}, {170, clickDelayItemsY, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT}, {110, clickDelayItemsY, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT}});

      JLabel holdDelayL = components.labelFactory("Hold time:", false, false, new int[]{30, holdDelayItemsY, 80, labelHeight});
      holdDelayTF = components.textFieldFactory(4, clickerData.getHoldDelay(), new int[][]{{290, holdDelayItemsY, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT}, {230, holdDelayItemsY, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT}, {170, holdDelayItemsY, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT}, {110, holdDelayItemsY, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT}});

      // second section ########################################################
      JLabel randomizeLabel = components.labelFactory("Randomize click interval", true, true, new int[]{0, 110, MAIN_FRAME_ACTUAL_WIDTH, labelHeight});

      JLabel clickRandomizeL = components.labelFactory("Click inter:", false, false, new int[]{30, randomizeItemsY, 80, labelHeight});
      JLabel holdRandomizeL = components.labelFactory("Hold time:", false, false, new int[]{200, randomizeItemsY, 80, labelHeight});

      shouldRandomizeCB = components.checkBoxFactory(clickerData.getRandomizeDelay(), new int[][]{{95, randomizeItemsY, 15, 20}, {270, randomizeItemsY, 15, 20}});

      randomizeRangeTF = components.textFieldFactory(4, clickerData.getRandomizeRange(), new int[][]{{115, randomizeItemsY, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT}, {290, randomizeItemsY, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT}});

      // third section ########################################################
      JLabel miscellaneousLabel = components.labelFactory("Clicks/Button/Hotkey", true, true, new int[]{0, 180, MAIN_FRAME_ACTUAL_WIDTH, labelHeight});

      JLabel clickAmountL = components.labelFactory("Clicks:", false, false, new int[]{30, clicksItems2Y, 80, labelHeight});
      clickAmountTF = components.textFieldFactory(5, clickerData.getClicks(), new int[]{70, clicksItems2Y, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT});

      JLabel buttonSelectL = components.labelFactory("Button:", false, false, new int[]{205, clicksItemsY, 80, labelHeight});
      String[] buttonSelectOptions = {"left", "right", "middle", "side front", "side back"};
      buttonSelectCB = components.comboBoxFactory(buttonSelectOptions, clickerData.getButton(), new int[]{250, clicksItemsY, 90, 20});

      // autoclick on hold
      JLabel autoclickOnMouseHoldLabel = components.labelFactory("Autoclick on button hold:", false, false, new int[]{135, 255, 190, 20});
      autoclickOnMouseHoldCheckBox = components.checkBoxFactory(this, clickerData.shouldAutoclickOnMouseHold(), new int[]{275, 255, 15, 20});
      JPanel autoclickOnMouseHoldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 9));
      autoclickOnMouseHoldPanel.setBounds(125, 250, 175, 30);
      autoclickOnMouseHoldPanel.add(autoclickOnMouseHoldLabel);
      autoclickOnMouseHoldPanel.add(autoclickOnMouseHoldCheckBox);


      add(autoclickOnMouseHoldPanel);

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

   @Override
   public void actionPerformed(ActionEvent e) {
      System.out.println("(GUI) Action performed");
      if (e.getSource() == helpButton) {
         helpGUI.setLocationRelativeTo(this);
         helpGUI.setVisible(true);
      }
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


}