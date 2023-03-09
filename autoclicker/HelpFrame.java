package autoclicker;

import java.awt.Font;

import javax.swing.*;

public class HelpFrame extends JFrame {
	private int frameWith = 350;
	private int frameHeight = 300;
 	private Font fontDefault = new Font("arial", Font.PLAIN, 12);

	public HelpFrame() {
		JLabel label = new JLabel("HELP!");
		label.setBounds(0, 0, 100, 50);
		label.setFont(fontDefault);

		this.add(label);

		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setSize(frameWith, frameHeight);
		this.setLayout(null);
	}

}
