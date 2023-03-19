package autoclicker;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.PlainDocument;

import textFieldFilter.TextFieldFilter;

/**
 * GUI where settings can be changed
 */
public class GUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	// components
	public static JTextField[] clickIntervalTF;
	public static JTextField[] holdTimeTF;
	public static JTextField[] randomizeTF;
	public static JTextField clicksTF;
	public static JCheckBox[] randomizeCB;
	public static JComboBox<?> buttonCB;
	public static JButton hotkeyB;
	private JButton helpB;
	private HelpGUI helpFrame = new HelpGUI();

	// frame title
	public String frameTitle = "Usefull Autoclicker " + Main.AutoclickerVers;

	// spacing border for textfields
	private static Color textFieldColor = Color.white;
	private static Border textFieldB = BorderFactory.createLineBorder(textFieldColor, 2);

	// fonts
	private static Font fontDefault = new Font("arial", Font.PLAIN, 12);

	// background color
	public static Color defaultBackgroundColor = new Color(230, 230, 230);

	// icon
	public static final ImageIcon icon = new ImageIcon("icon.png");

	public GUI() {
		int frameWidth = 400;
		int frameHeight = 330;
		// actual frame width
		int AframeWidth = 384;

		// label sizes
		int labelHeight = 20;

		int timeLabelWidth = 20;

		// textFields sizes
		int inputHeight = 20;
		int inputWidth = 35;

		// y levels of components
		int clickIntervalItemsY = 35;
		int holdTimeItemsY = 75;
		int randomizeItemsY = 145;
		int clicksY = 215;
		int clicks2Y = 250;

		// labels ###################################
		// titles
		// text sign that says "Click delay/Hold delay"
		JLabel intervalL = LabelFactory("Click interval/Hold time", true, true, defaultBackgroundColor);
		intervalL.setBounds(0, 0, AframeWidth, labelHeight + 5);

		// text sign that says "Randomize click interval"
		JLabel randomizeL = LabelFactory("Randomize click interval", true, true, defaultBackgroundColor);
		randomizeL.setBounds(0, 110, AframeWidth, labelHeight);

		// text sign that says "Clicks/Hotkey/Button"
		JLabel clickspressL = LabelFactory("Clicks/Button/Hotkey", true, true, defaultBackgroundColor);
		clickspressL.setBounds(0, 180, AframeWidth, labelHeight);

		// "help" labels
		// "Click delay:" label
		JLabel clickIntervalL = LabelFactory("Click interval:", false, false, defaultBackgroundColor);
		clickIntervalL.setBounds(30, clickIntervalItemsY, 80, labelHeight);

		// "Hold delay:" label
		JLabel holdTimeL = LabelFactory("Hold time:", false, false, defaultBackgroundColor);
		holdTimeL.setBounds(30, holdTimeItemsY, 80, labelHeight);

		// "Click:" label
		JLabel clickL = LabelFactory("Click interv:", false, false, defaultBackgroundColor);
		clickL.setBounds(30, randomizeItemsY, 80, labelHeight);

		// "Hold:" label
		JLabel holdL = LabelFactory("Hold time:", false, false, defaultBackgroundColor);
		holdL.setBounds(200, randomizeItemsY, 80, labelHeight);

		// "Clicks:" label
		JLabel clicksL = LabelFactory("Clicks:", false, false, defaultBackgroundColor);
		clicksL.setBounds(30, clicksY, 80, labelHeight);

		// "Press:" label
		JLabel pressL = LabelFactory("Button:", false, false, defaultBackgroundColor);
		pressL.setBounds(135, clicksY, 80, labelHeight);

		// time labels
		// ms label
		int[][] msLCords = { { 328, clickIntervalItemsY, timeLabelWidth, labelHeight },
				{ 328, holdTimeItemsY, timeLabelWidth, labelHeight },
				{ 153, randomizeItemsY, timeLabelWidth, labelHeight },
				{ 328, randomizeItemsY, timeLabelWidth, labelHeight } };
		JLabel[] msL = timeLabelFactory(4, "ms", msLCords);

		// s label
		int[][] sLCords = { { 268, clickIntervalItemsY, timeLabelWidth, labelHeight },
				{ 268, holdTimeItemsY, timeLabelWidth, labelHeight } };
		JLabel[] sL = timeLabelFactory(2, "s", sLCords);

		// min label
		int[][] mLCords = { { 208, clickIntervalItemsY, timeLabelWidth, labelHeight },
				{ 208, holdTimeItemsY, timeLabelWidth, labelHeight } };
		JLabel[] mL = timeLabelFactory(2, "m", mLCords);

		// h label
		int[][] hLCords = { { 148, clickIntervalItemsY, timeLabelWidth, labelHeight },
				{ 148, holdTimeItemsY, timeLabelWidth, labelHeight } };
		JLabel[] hL = timeLabelFactory(2, "h", hLCords);

		// textfields #########################
		// click delay TF
		int[][] clickIntervalTFCords = { { 290, clickIntervalItemsY, inputWidth, inputHeight },
				{ 230, clickIntervalItemsY, inputWidth, inputHeight },
				{ 170, clickIntervalItemsY, inputWidth, inputHeight },
				{ 110, clickIntervalItemsY, inputWidth, inputHeight } };
		clickIntervalTF = textFieldFactory(textFieldColor, textFieldB, clickIntervalTFCords, 4);
		clickIntervalTF[0].setText("100");

		// hold delay TF
		int[][] holdTimeTFCords = { { 290, holdTimeItemsY, inputWidth, inputHeight },
				{ 230, holdTimeItemsY, inputWidth, inputHeight }, { 170, holdTimeItemsY, inputWidth, inputHeight },
				{ 110, holdTimeItemsY, inputWidth, inputHeight } };
		holdTimeTF = textFieldFactory(textFieldColor, textFieldB, holdTimeTFCords, 4);

		// random interval TF
		int[][] randomizeTFCords = { { 115, randomizeItemsY, inputWidth, inputHeight },
				{ 290, randomizeItemsY, inputWidth, inputHeight } };
		randomizeTF = textFieldFactory(textFieldColor, textFieldB, randomizeTFCords, 2);
		randomizeTF[0].setText("20");
		randomizeTF[1].setText("20");

		// amount clicks TF
		int[] clicksTFCords = { 70, clicksY, inputWidth, inputHeight };
		clicksTF = textFieldFactory(textFieldColor, textFieldB, clicksTFCords);

		// check boxes ############################
		int[][] randomizeCBCords = { { 95, randomizeItemsY, 15, 20 }, { 270, randomizeItemsY, 15, 20 } };
		randomizeCB = checkBoxFactory(2, randomizeCBCords);

		// dropdown #############################
		String[] comboBoxOptions = { "left", "middle", "right", "side front", "side back" };
		int[] buttonCBCords = { 180, clicksY, 90, 20 };
		buttonCB = comboBoxFactory(comboBoxOptions, buttonCBCords);

		// button ###################################
		// help button
		int[] helpBCords = { 360, 4, 17, 17 };
		helpB = buttonFactory("?", BorderFactory.createLineBorder(Color.gray), helpBCords);
		// hotkey button
		int[] hotkeyBCords = { 20, clicks2Y, 165, 30 };
		hotkeyB = buttonFactory("Select hotkey(" + Main.hotkeyT + ")", null, hotkeyBCords);

		// backgrounds ########################
		int[][] backgroundC = { { 20, 30, 340, 30 }, { 20, 70, 340, 30 }, { 20, 140, 165, 30 }, { 195, 140, 165, 30 },
				{ 20, 210, 95, 30 }, { 125, 210, 155, 30 } };
		JPanel backgrounds[] = backgroundPanelFactory(6, defaultBackgroundColor, backgroundC);

		// frame itself
		this.setIconImage(icon.getImage());
		this.setSize(frameWidth, frameHeight);
		this.setLayout(null);
		this.setTitle(frameTitle);
		this.getContentPane().setBackground(Color.white);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		// adding components ####################
		// adding textfields
		addComp(clickIntervalTF);
		addComp(holdTimeTF);
		addComp(randomizeTF);
		addComp(clicksTF);

		// adding hotkey button
		addComp(hotkeyB);
		// adding help button
		addComp(helpB);

		// addig checkboxes
		addComp(randomizeCB);

		// adding dropdown
		addComp(buttonCB);

		// adding sign labels
		addComp(intervalL);
		addComp(randomizeL);
		addComp(clickspressL);

		// adding text labels
		addComp(clickIntervalL);
		addComp(holdTimeL);
		addComp(clickL);
		addComp(holdL);
		addComp(clicksL);
		addComp(pressL);

		// adding ms, s, min, h label
		addComp(msL);
		addComp(sL);
		addComp(mL);
		addComp(hL);

		// adding backgrounds
		addComp(backgrounds);

	}

	/**
	 * declares labels width text background color and central alignment
	 * 
	 * @param internalText what to put in label
	 * @param center       do you want the text centerd in label
	 * @param background   do you want the background to show
	 * @param color        color of background
	 * @return the label
	 */
	public JLabel LabelFactory(String internalText, boolean center, boolean background, Color color) {
		JLabel label = new JLabel();
		label.setText(internalText);
		label.setFont(fontDefault);
		label.setBackground(color);
		if (center) {
			label.setHorizontalAlignment(JLabel.CENTER);
		}
		label.setOpaque(background);
		return label;
	}

	/**
	 * declares labels and put text in them
	 * 
	 * @param amount       how many labels
	 * @param internalText what to set inside label
	 * @return the label(s) made
	 */
	public JLabel[] timeLabelFactory(int amount, String internalText, int[][] cords) {
		JLabel[] label = new JLabel[amount];
		for (int i = 0; i < label.length; i++) {
			label[i] = new JLabel();
			label[i].setText(internalText);
			label[i].setFont(fontDefault);
			label[i].setBounds(cords[i][0], cords[i][1], cords[i][2], cords[i][3]);
		}
		return label;
	}

	/**
	 * declares panels and give a color
	 * 
	 * @param amount how many panels
	 * @param color  which background color
	 * @return the panel(s) made
	 */
	public JPanel[] backgroundPanelFactory(int amount, Color color, int[][] cords) {
		JPanel[] panel = new JPanel[amount];
		for (int i = 0; i < panel.length; i++) {
			panel[i] = new JPanel();
			panel[i].setBackground(color);
			panel[i].setOpaque(true);
			panel[i].setBounds(cords[i][0], cords[i][1], cords[i][2], cords[i][3]);
		}
		return panel;
	}

	/**
	 * declares textfields and give a color
	 * 
	 * @param amount how many textfields
	 * @param color  which background color
	 * @return the textfields made
	 */
	public static JTextField[] textFieldFactory(Color color, Border border, int[][] cords, int amount) {
		JTextField[] textField = new JTextField[amount];

		for (int i = 0; i < textField.length; i++) {
			textField[i] = new JTextField();
			textField[i].setBorder(border);
			textField[i].setBackground(color);
			textField[i].setText("0");
			textField[i].setBounds(cords[i][0], cords[i][1], cords[i][2], cords[i][3]);
			PlainDocument document = (PlainDocument) (textField[i].getDocument());
			document.setDocumentFilter(new TextFieldFilter(4));
		}
		return textField;
	}

	/**
	 * declares textfields and give a color
	 * 
	 * @param amount how many textfields
	 * @param color  which background color
	 * @return the textfield(s) made
	 */
	public static JTextField textFieldFactory(Color color, Border border, int[] cords) {
		JTextField textField = new JTextField();

		textField = new JTextField();
		textField.setBorder(border);
		textField.setBackground(color);
		textField.setText("0");
		textField.setBounds(cords[0], cords[1], cords[2], cords[3]);
		PlainDocument document = (PlainDocument) (textField.getDocument());
		document.setDocumentFilter(new TextFieldFilter(4));
		return textField;
	}

	/**
	 * declares a Checkbox
	 * 
	 * @param amount how many checkboxes
	 * @return the checkbox
	 */
	public static JCheckBox[] checkBoxFactory(int amount, int[][] cords) {
		JCheckBox[] checkBox = new JCheckBox[amount];
		for (int i = 0; i < checkBox.length; i++) {
			checkBox[i] = new JCheckBox();
			checkBox[i].setBorder(null);
			checkBox[i].setFocusable(false);
			checkBox[i].setOpaque(false);
			checkBox[i].setBounds(cords[i][0], cords[i][1], cords[i][2], cords[i][3]);
		}
		return checkBox;
	}

	/**
	 * declares a Button
	 * 
	 * @param internalText what to put in button
	 * @return
	 */
	public JButton buttonFactory(String internalText, Border border, int[] cords) {
		JButton button = new JButton();
		button.setBackground(defaultBackgroundColor);
		button.setFont(fontDefault);
		button.setText(internalText);
		button.setBorder(border);
		button.setFocusable(false);
		button.addActionListener(this);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setOpaque(true);
		button.setBounds(cords[0], cords[1], cords[2], cords[3]);
		return button;
	}

	/**
	 * declares a combobox(dropdown with options)
	 * 
	 * @return the combobox
	 */
	public static JComboBox<String> comboBoxFactory(String[] options, int[] cords) {
		JComboBox<String> comboBox = new JComboBox<String>(options);
		comboBox.setBackground(Color.white);
		comboBox.setFocusable(false);
		comboBox.setFont(fontDefault);
		comboBox.setBounds(cords[0], cords[1], cords[2], cords[3]);
		return comboBox;
	}

	/**
	 * if new hotkey button is pressed search for hew hotkey and set button to
	 * "Press new hotkey" if help button was pressed center popup and vieuw popup
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == hotkeyB) {
			Main.newHotkey = true;
			hotkeyB.setText("Press new hotkey");
		} else if (e.getSource() == helpB) {
			centerPopup();
			helpFrame.setVisible(true);
		}
	}

	public void centerPopup() {
		Point position = this.getLocationOnScreen();
		int x = position.x;
		int y = position.y;

		int tw = this.getWidth();
		int th = this.getHeight();

		int pw = helpFrame.getWidth();
		int ph = helpFrame.getHeight();

		double formX = (1 - (double) pw / tw) / 2;
		double formY = (1 - (double) ph / th) / 2;

		int calcX = (int) Math.round(x + (tw * formX));
		int calcY = (int) Math.round(y + (th * formY));

		helpFrame.setLocation(calcX, calcY);
	}

	/**
	 * adds component to frame
	 * 
	 * @param comp component to add
	 */
	public void addComp(Component comp) {
		if (comp != null) {
			this.add(comp);
		}
	}

	/**
	 * adds component array to frame
	 * 
	 * @param compA component array to add
	 */
	public void addComp(Component[] compA) {
		if (compA != null) {
			for (int i = 0; i < compA.length; i++) {
				this.add(compA[i]);
			}
		}
	}
}