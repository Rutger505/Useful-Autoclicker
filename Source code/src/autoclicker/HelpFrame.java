package autoclicker;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.*;

/**
 * frame when Help button is pressed with all information needed
 */
public class HelpFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private int frameWidth = 350;
	// actual frame with
	private int aFrameWidth = frameWidth - 16;
	private int frameHeight = 290;

	private int titleHeight = 20;

	private int fontTitleS = 16;
	private Font fontTitle = new Font("arial", Font.PLAIN, fontTitleS);
	private int fontTextS = 12;
	private Font fontText = new Font("arial", Font.PLAIN, fontTextS);

	public HelpFrame() {
		// part 1
		int[] Ti1C = { 0, 0, aFrameWidth, titleHeight };
		JLabel Ti1 = labelFactory("Click interval/Hold time", fontTitle, Ti1C, true);
		int[] Te1C = { 0, 24, aFrameWidth, 40 };
		JLabel Te1 = labelFactory(
				"<html><b>Click interval</b> means the time between clicks. <br> "
						+ " <b>Hold time</b> means the time between a mouse press and release.</html>",
				fontText, Te1C, false);

		// part 2
		int[] Ti2C = { 0, 72, aFrameWidth, titleHeight };
		JLabel Ti2 = labelFactory("Randomize click interval", fontTitle, Ti2C, true);
		int[] Te2C = { 0, 96, aFrameWidth, 70 };
		JLabel Te2 = labelFactory(
				"<html><b>Randomize click interval</b> means there is a random number around the interval depending of range.<br>"
						+ " <b>Example:</b> If interval is 100ms with a random interval range of 20ms the click interval will be a random number between 80 and 140.</html>",
				fontText, Te2C, false);

		// part 3
		int[] Ti3C = { 0, 174, aFrameWidth, titleHeight };
		JLabel Ti3 = labelFactory("Clicks/Button/Hotkey", fontTitle, Ti3C, true);
		int[] Te3C = { 0, 198, aFrameWidth, 50 };
		JLabel Te3 = labelFactory("<html><b>Clicks</b> means how many times the Autoclicker will click.<br>"
				+ " <b>Button</b> means the button that the Autoclicker will press.<br>"
				+ " <b>Hotkey</b> means the button to activate the Autoclicker</html>", fontText, Te3C, false);

		this.setIconImage(GUI.icon.getImage());
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setSize(frameWidth, frameHeight);
		this.setTitle("Help");
		this.setLayout(null);
		this.setResizable(false);
		this.setVisible(false);
		this.setAlwaysOnTop(true);

		// titles
		addComp(Ti1);
		addComp(Ti2);
		addComp(Ti3);

		// texts
		addComp(Te1);
		addComp(Te2);
		addComp(Te3);

	}
/**
 * Makes label
 * 
 * @param internalText what to put inside label?
 * @param font what font to use?
 * @param bounds where do i need to place label?
 * @param background want background?
 * @return the label
 */
 	private JLabel labelFactory(String internalText, Font font, int[] bounds, boolean background) {
		JLabel label = new JLabel();
		label.setFont(font);
		label.setText(internalText);
		label.setBackground(Color.LIGHT_GRAY);
		label.setOpaque(background);
		label.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
		return label;
	}

 	/**
 	 * adds component to frame
 	 * @param comp component to add
 	 */
	public void addComp(Component comp) {
		this.add(comp);
	}

	/**
	 * adds comnent to frame
	 * @param compA comonent to add
	 */
	public void addComp(Component[] compA) {
		for (int i = 0; i < compA.length; i++) {
			this.add(compA[i]);
		}
	}
}
