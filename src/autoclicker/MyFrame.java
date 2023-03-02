package autoclicker;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * GUI where settings can be changed(soon)
 */
public class MyFrame extends JFrame {

	// fonts
	Font fontDefault = new Font("arial", Font.PLAIN, 12);

	// border
	Border black2pxB = BorderFactory.createLineBorder(Color.gray);
	// default background color
	Color defaultBackgroundColor = Color.lightGray;

	MyFrame() {
		// text sign that says "Click delay/Hold delay"
		JLabel delayL = signLabelFactory("Click delay/Hold delay", defaultBackgroundColor);
		delayL.setBounds(0, 0, 400, 20);

		// text sign that says "Randomize click interval"
		JLabel randomizeL = signLabelFactory("Randomize click interval", defaultBackgroundColor);
		randomizeL.setBounds(0, 110, 400, 20);

		// text sign that says "Clicks/Hotkey/Button"
		JLabel clicksHotkeyL = signLabelFactory("Clicks/Hotkey/Button", defaultBackgroundColor);
		clicksHotkeyL.setBounds(0, 180, 400, 20);

		// background ########################
		JPanel backgroundP[] = backgroundPanelFactory(7, Color.lightGray);
		backgroundP[0].setBounds(20, 30, 340, 30);
		backgroundP[1].setBounds(20, 70, 340, 30);
		backgroundP[2].setBounds(20, 140, 165, 30);
		backgroundP[3].setBounds(195, 140, 165, 30);
		backgroundP[4].setBounds(20, 210, 95, 30);
		backgroundP[5].setBounds(125, 210, 155, 30);
		backgroundP[6].setBounds(20, 250, 125, 30);

		int inputHeight = 20;
		int inputWidth = 35;

		int delayClickItemsY = 35;
		int delayHoldItemsY = 75;
		int randomizeItemsY = 145;
		int clicksY = 215;

		// textfields #########################
		// click delay TF
		JTextField[] delayClickTF = textFieldFactory(4, Color.white);
		delayClickTF[0].setBounds(110, delayClickItemsY, inputWidth, inputHeight);
		delayClickTF[1].setBounds(170, delayClickItemsY, inputWidth, inputHeight);
		delayClickTF[2].setBounds(230, delayClickItemsY, inputWidth, inputHeight);
		delayClickTF[3].setBounds(290, delayClickItemsY, inputWidth, inputHeight);

		// hold delay TF
		JTextField[] delayHoldTF = textFieldFactory(4, Color.white);
		delayHoldTF[0].setBounds(110, delayHoldItemsY, inputWidth, inputHeight);
		delayHoldTF[1].setBounds(170, delayHoldItemsY, inputWidth, inputHeight);
		delayHoldTF[2].setBounds(230, delayHoldItemsY, inputWidth, inputHeight);
		delayHoldTF[3].setBounds(290, delayHoldItemsY, inputWidth, inputHeight);

		// random interval TF
		JTextField[] randomizeTF = textFieldFactory(2, Color.white);
		randomizeTF[0].setBounds(115, randomizeItemsY, inputWidth, inputHeight);
		randomizeTF[1].setBounds(290, randomizeItemsY, inputWidth, inputHeight);
		
		// amount clicks TF
		JTextField[] clicksTF = textFieldFactory(1, Color.white);
		clicksTF[0].setBounds(70, clicksY, inputWidth, inputHeight);
		
		// ms, s, min, h labels ########################################
		// ms label
		JLabel[] msL = timeLabelFactory(4, "ms");
		msL[0].setBounds(330, delayClickItemsY, 20, 20); // for click delay
		msL[1].setBounds(330, delayHoldItemsY, 20, 20); // for hold delay
		msL[2].setBounds(155, randomizeItemsY, 20, 20); // for click delay
		msL[3].setBounds(330, randomizeItemsY, 20, 20); // for hold delay

		// s label
		JLabel[] sL = timeLabelFactory(2, "s");
		sL[0].setBounds(270, delayClickItemsY, 20, 20); // for click delay
		sL[1].setBounds(270, delayHoldItemsY, 20, 20); // for hold delay

		// min label
		JLabel[] minL = timeLabelFactory(2, "min");
		minL[0].setBounds(206, delayClickItemsY, 30, 20); // for click delay
		minL[1].setBounds(206, delayHoldItemsY, 30, 20); // for hold delay

		// h label
		JLabel[] hL = timeLabelFactory(2, "h");
		hL[0].setBounds(150, delayClickItemsY, 20, 20); // for click delay
		hL[1].setBounds(150, delayHoldItemsY, 20, 20); // for hold delay

		// variable texts labels ########################################
		// "Click delay:" label
		JLabel clickDelayL = textLabelFactory("Click delay:");
		clickDelayL.setBounds(30, delayClickItemsY, 80, 20); // for click delay

		// "Hold delay:" label
		JLabel holdDelayL = textLabelFactory("Hold delay:");
		holdDelayL.setBounds(30, delayHoldItemsY, 80, 20); // for click delay

		// "Click:" label
		JLabel clickL = textLabelFactory("Click:");
		clickL.setBounds(30, randomizeItemsY, 80, 20); // for random interval "click:"

		// "Hold:" label
		JLabel holdL = textLabelFactory("Hold:");
		holdL.setBounds(200, randomizeItemsY, 80, 20); // for random interval "hold:"

		// "Clicks:" label
		JLabel clicksL = textLabelFactory("Clicks:");
		clicksL.setBounds(30, clicksY, 80, 20);

		// "Press:" label
		JLabel pressL = textLabelFactory("Press:");
		pressL.setBounds(135, clicksY, 80, 20);
		
		// check boxes ############################
		JCheckBox[] randomizeCB = checkBoxFactory(2);
		randomizeCB[0].setBounds(70, randomizeItemsY, 15, 20);
		randomizeCB[1].setBounds(240, randomizeItemsY, 15, 20);
		
		// dropdown #############################
		JComboBox<?> pressCB = comboBoxFactory();
		pressCB.setBounds(180, clicksY, 90, 20);
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 330); // sets size of COMPLETE window(i think)
		this.setResizable(false); // makes the windows resizable/ not resizable
		this.setVisible(true); // makes windows visible
		this.setLayout(null); // set positions manually
		this.setTitle("Autoclicker"); // application title
		this.getContentPane().setBackground(Color.white); // backgrround color of frame itself

		// sign labels
		this.add(delayL);
		this.add(randomizeL);
		this.add(clicksHotkeyL);

		// text labels
		this.add(clickDelayL);
		this.add(holdDelayL);
		this.add(clickL);
		this.add(holdL);
		this.add(clicksL);
		this.add(pressL);

		// ms, s, min, h texts
		for (int i = 0; i < msL.length; i++) {
			this.add(msL[i]);
			try {
				this.add(sL[i]);
				this.add(minL[i]);
				this.add(hL[i]);
			} catch (Exception e) {
			}
		}

		// adding textfields
		for (int i = 0; i < delayClickTF.length; i++) {
			this.add(delayClickTF[i]);
			this.add(delayHoldTF[i]);
			try {
				this.add(randomizeTF[i]);
			} catch (Exception e) {
			}
			try {
				this.add(clicksTF[i]);
			} catch (Exception e) {
			}
		}
		// addig checkboxes
		this.add(randomizeCB[0]);
		this.add(randomizeCB[1]);
				
		// adding dropdown
		this.add(pressCB);
		
		// adding backgrounds
		for (int i = 0; i < backgroundP.length; i++) {
			this.add(backgroundP[i]);
		}
	}

	/**
	 * styles Label with text background color font central textalignment
	 * 
	 * @param internalText what to set inside label
	 * @param color        which background color
	 */
	public JLabel signLabelFactory(String internalText, Color color) {
		JLabel label = new JLabel();
		label.setText(internalText);
		label.setBackground(color);
		label.setFont(fontDefault);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setOpaque(true);
		return label;
	}

	/**
	 * inisialize panels and give a color
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
	 * inisialize textfields and give a color
	 * 
	 * @param amount how many textfields
	 * @param color  which background color
	 * @return the textfield(s) made
	 */
	public JTextField[] textFieldFactory(int amount, Color color) {
		JTextField[] textField = new JTextField[amount];
		for (int i = 0; i < textField.length; i++) {
			textField[i] = new JTextField();
			textField[i].setBackground(color);
		}
		return textField;
	}

	/**
	 * inisialize labels and put text in them
	 * 
	 * @param amount       how many labels
	 * @param internalText what to set inside label
	 * @return the label(s) made
	 */
	public JLabel[] timeLabelFactory(int amount, String internalText) {
		JLabel[] label = new JLabel[amount];
		for (int i = 0; i < label.length; i++) {
			label[i] = new JLabel();
			label[i].setText(internalText);
			label[i].setFont(fontDefault);
		}
		return label;
	}

	/**
	 * Makes a text label
	 * 
	 * @param internalText what is labe text?
	 * @return the label
	 */
	public JLabel textLabelFactory(String internalText) {
		JLabel label = new JLabel();
		label.setText(internalText);
		label.setFont(fontDefault);
		return label;
	}
	public JCheckBox[] checkBoxFactory(int amount) {
		JCheckBox[] checkBox = new JCheckBox[amount];
		for(int i=0; i < checkBox.length; i++){
			checkBox[i] = new JCheckBox();
			checkBox[i].setBorder(null);
			checkBox[i].setOpaque(false);
		}
		return checkBox;
	}
	public JComboBox<?> comboBoxFactory(){
		String[] comboBoxOptions = {"MBUTTON1" ,"MBUTTON2","MBUTTON3","MBUTTON4","MBUTTON5"};
		JComboBox<?> comboBox = new JComboBox<Object>(comboBoxOptions);
		return comboBox;
	}
}
