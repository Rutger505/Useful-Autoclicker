package autoclicker;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.PlainDocument;

/**
 * GUI where settings can be changed
 */
public class MyFrame extends JFrame implements ActionListener {
	// spacing border for textfields
	static Border whiteB = BorderFactory.createLineBorder(Color.white, 2);

	public static JTextField[] delayClickTF = textFieldFactory(4, Color.white, whiteB);
	public static JTextField[] delayHoldTF = textFieldFactory(4, Color.white, whiteB);
	public static JTextField[] randomizeTF = textFieldFactory(2, Color.white, whiteB);
	public static JTextField[] clicksTF = textFieldFactory(1, Color.white, whiteB);
	public static JCheckBox[] randomizeCB = checkBoxFactory(2);
	public static String[] comboBoxOptions = { "left", "middle", "right", "side front", "side back" };
	public static JComboBox<?> buttonCB = comboBoxFactory(comboBoxOptions);
	public static JButton hotkeyB;
	private JButton helpB;

	// fonts
	private static Font fontDefault = new Font("arial", Font.PLAIN, 12);

	// default background color
	private static Color defaultBackgroundColor = Color.lightGray;

	public MyFrame() {
		int screenWidth = 400;

		int labelHeight = 20;

		int timeLabelWidth = 20;

		int inputHeight = 20;
		int inputWidth = 35;

		int delayClickItemsY = 35;
		int delayHoldItemsY = 75;
		int randomizeItemsY = 145;
		int clicksY = 215;

		// inisialize components and place ########################################
		// label signs ###################################
		// text sign that says "Click delay/Hold delay"
		JLabel intervalL = LabelFactory("Click interval/Hold time", true, true, defaultBackgroundColor);
		intervalL.setBounds(0, 0, screenWidth, labelHeight+5);

		// text sign that says "Randomize click interval"
		JLabel randomizeL = LabelFactory("Randomize click interval", true, true, defaultBackgroundColor);
		randomizeL.setBounds(0, 110, screenWidth, labelHeight);

		// text sign that says "Clicks/Hotkey/Button"
		JLabel clicksHotkeyL = LabelFactory("Clicks/Button/Hotkey", true, true, defaultBackgroundColor);
		clicksHotkeyL.setBounds(0, 180, screenWidth, labelHeight);

		// variable texts labels ########################################
		// "Click delay:" label
		JLabel clickintervalL = LabelFactory("Click interval:", false, false, defaultBackgroundColor);
		clickintervalL.setBounds(30, delayClickItemsY, 80, labelHeight); // for click delay

		// "Hold delay:" label
		JLabel holdintervalL = LabelFactory("Hold time:", false, false, defaultBackgroundColor);
		holdintervalL.setBounds(30, delayHoldItemsY, 80, labelHeight); // for click delay

		// "Click:" label
		JLabel clickL = LabelFactory("Click interv:", false, false, defaultBackgroundColor);
		clickL.setBounds(30, randomizeItemsY, 80, labelHeight); // for random interval "click:"

		// "Hold:" label
		JLabel holdL = LabelFactory("Hold time:", false, false, defaultBackgroundColor);
		holdL.setBounds(200, randomizeItemsY, 80, labelHeight); // for random interval "hold:"

		// "Clicks:" label
		JLabel clicksL = LabelFactory("Clicks:", false, false, defaultBackgroundColor);
		clicksL.setBounds(30, clicksY, 80, labelHeight);

		// "Press:" label
		JLabel buttonL = LabelFactory("Button:", false, false, defaultBackgroundColor);
		buttonL.setBounds(135, clicksY, 80, labelHeight);

		// Time text labels ########################################
		// ms label
		int[][] msLCords = { { 328, delayClickItemsY, timeLabelWidth, labelHeight },
				{ 328, delayHoldItemsY, timeLabelWidth, labelHeight },
				{ 153, randomizeItemsY, timeLabelWidth, labelHeight },
				{ 328, randomizeItemsY, timeLabelWidth, labelHeight } };
		JLabel[] msL = timeLabelFactory(4, "ms", msLCords);
		msL[0].setBounds(328, delayClickItemsY, timeLabelWidth, labelHeight); // for click delay
		msL[1].setBounds(328, delayHoldItemsY, timeLabelWidth, labelHeight); // for hold delay
		msL[2].setBounds(153, randomizeItemsY, timeLabelWidth, labelHeight); // for randomize click
		msL[3].setBounds(328, randomizeItemsY, timeLabelWidth, labelHeight); // for randomize hold

		// s label
		int[][] sLCords = { { 268, delayClickItemsY, timeLabelWidth, labelHeight },
				{ 268, delayHoldItemsY, timeLabelWidth, labelHeight } };
		JLabel[] sL = timeLabelFactory(2, "s", sLCords);

		// min label
		int[][] mLCords = { { 208, delayClickItemsY, timeLabelWidth, labelHeight },
				{ 208, delayHoldItemsY, timeLabelWidth, labelHeight } };
		JLabel[] mL = timeLabelFactory(2, "m", mLCords);

		// h label
		int[][] hLCords = { { 148, delayClickItemsY, timeLabelWidth, labelHeight },
				{ 148, delayHoldItemsY, timeLabelWidth, labelHeight } };
		JLabel[] hL = timeLabelFactory(2, "h", hLCords);

		// textfields #########################
		// click delay TF
		delayClickTF[3].setBounds(110, delayClickItemsY, inputWidth, inputHeight);
		delayClickTF[2].setBounds(170, delayClickItemsY, inputWidth, inputHeight);
		delayClickTF[1].setBounds(230, delayClickItemsY, inputWidth, inputHeight);
		delayClickTF[0].setBounds(290, delayClickItemsY, inputWidth, inputHeight);
		delayClickTF[0].setText("100");

		// hold delay TF
		delayHoldTF[3].setBounds(110, delayHoldItemsY, inputWidth, inputHeight);
		delayHoldTF[2].setBounds(170, delayHoldItemsY, inputWidth, inputHeight);
		delayHoldTF[1].setBounds(230, delayHoldItemsY, inputWidth, inputHeight);
		delayHoldTF[0].setBounds(290, delayHoldItemsY, inputWidth, inputHeight);

		// random interval TF
		randomizeTF[0].setBounds(115, randomizeItemsY, inputWidth, inputHeight);
		randomizeTF[1].setBounds(290, randomizeItemsY, inputWidth, inputHeight);
		randomizeTF[0].setText("20");
		randomizeTF[1].setText("20");

		// amount clicks TF
		clicksTF[0].setBounds(70, clicksY, inputWidth, inputHeight);

		// button ###################################
		// help button
		helpB = buttonFactory("?", BorderFactory.createLineBorder(Color.black));
		helpB.setBounds(360, 4, 17, 17);
		// hotkey button
		hotkeyB = buttonFactory("Click to select hotkey(" + Main.hotkey + ")", null);
		hotkeyB.setBounds(20, 250, 165, 30);

		// check boxes ############################
		randomizeCB[0].setBounds(95, randomizeItemsY, 15, 20);
		randomizeCB[1].setBounds(270, randomizeItemsY, 15, 20);

		// dropdown #############################
		buttonCB.setBounds(180, clicksY, 90, 20);

		// backgrounds ########################
		JPanel backgroundP[] = backgroundPanelFactory(6, Color.lightGray);
		backgroundP[0].setBounds(20, 30, 340, 30);
		backgroundP[1].setBounds(20, 70, 340, 30);
		backgroundP[2].setBounds(20, 140, 165, 30);
		backgroundP[3].setBounds(195, 140, 165, 30);
		backgroundP[4].setBounds(20, 210, 95, 30);
		backgroundP[5].setBounds(125, 210, 155, 30);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 330); // sets size of COMPLETE window(i think)
		this.setResizable(false); // makes the windows resizable/ not resizable
		this.setVisible(true); // makes windows visible
		this.setLayout(null); // set positions manually
		this.setTitle("Usefull Autoclicker"); // application title
		this.getContentPane().setBackground(Color.white); // backgrround color of frame itself

		// adding components ####################
		// adding textfields
		addComp(delayClickTF);
		addComp(delayHoldTF);
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
		addComp(clicksHotkeyL);

		// adding text labels
		addComp(clickintervalL);
		addComp(holdintervalL);
		addComp(clickL);
		addComp(holdL);
		addComp(clicksL);
		addComp(buttonL);

		// adding ms, s, min, h label
		addComp(msL);
		addComp(sL);
		addComp(mL);
		addComp(hL);

		// adding backgrounds
		addComp(backgroundP);
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
	public JPanel[] backgroundPanelFactory(int amount, Color color) {
		JPanel[] panel = new JPanel[amount];
		for (int i = 0; i < panel.length; i++) {
			panel[i] = new JPanel();
			panel[i].setBackground(color);
			panel[i].setOpaque(true);
		}
		return panel;
	}

	/**
	 * declares textfields and give a color
	 * 
	 * @param amount how many textfields
	 * @param color  which background color
	 * @return the textfield(s) made
	 */
	public static JTextField[] textFieldFactory(int amount, Color color, Border border) {
		JTextField[] textField = new JTextField[amount];
		for (int i = 0; i < textField.length; i++) {
			textField[i] = new JTextField();
			textField[i].setBorder(border);
			textField[i].setBackground(color);
			textField[i].setText("0");
			PlainDocument document = (PlainDocument) (textField[i].getDocument());
			document.setDocumentFilter(new MyIntFilter(4));
		}
		return textField;
	}

	/**
	 * declares a Checkbox
	 * 
	 * @param amount how many checkboxes
	 * @return the checkbox
	 */
	public static JCheckBox[] checkBoxFactory(int amount) {
		JCheckBox[] checkBox = new JCheckBox[amount];
		for (int i = 0; i < checkBox.length; i++) {
			checkBox[i] = new JCheckBox();
			checkBox[i].setBorder(null);
			checkBox[i].setFocusable(false);
			checkBox[i].setOpaque(false);
		}
		return checkBox;
	}

	/**
	 * declares a Button
	 * 
	 * @param internalText what to put in button
	 * @return
	 */
	public JButton buttonFactory(String internalText, Border border) {
		JButton button = new JButton();
		button.setBackground(defaultBackgroundColor);
		button.setFont(fontDefault);
		button.setText(internalText);
		button.setBorder(border);
		button.setFocusable(false);
		button.addActionListener(this);
		button.setOpaque(true);
		return button;
	}

	/**
	 * declares a combobox(dropdown with options)
	 * 
	 * @return the combobox
	 */
	public static JComboBox<String> comboBoxFactory(String[] options) {
		JComboBox<String> comboBox = new JComboBox<String>(options);
		comboBox.setBackground(Color.white);
		comboBox.setFocusable(false);
		comboBox.setFont(fontDefault);
		return comboBox;
	}


	HelpFrame helpFrame = new HelpFrame();
	/**
	 * if button is pressed search for hew hotkey and set button to "Press new
	 * hotkey"
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == hotkeyB) {
			Main.newHotkey = true;
			hotkeyB.setText("Press new hotkey");
		} else if(e.getSource() == helpB) {
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
}