package autoclicker;

import java.awt.AWTException;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class Main implements NativeKeyListener {
	private static Autoclicker clicker;
	private static MyFrame frame;
	public static boolean newHotkey = false;
	
	// ############ SETTING #################
	public static String hotkey = "T";

	public static void main(String[] args){
		// Clicker
		clicker = new Autoclicker();
		// GUI
		frame = new MyFrame();
		// key listener
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException e) {
		}
		GlobalScreen.addNativeKeyListener(new Main());

		// hide JNativeKeyListener file
		new FileHider("JNativeHook.x86_64.dll");
	}

	/**
	 * activates when a key on keyboard is pressed<br>
	 * if i search hor hotkey just set new hotkey<br>
	 * else if the key pressed is equal to hotkey toggle clicker
	 */
	public void nativeKeyPressed(NativeKeyEvent nke) {
		if (newHotkey) {
			hotkey = NativeKeyEvent.getKeyText(nke.getKeyCode());
			MyFrame.hotkeyB.setText("Click to select hotkey(" + hotkey + ")");
			newHotkey = false;
		} else if (NativeKeyEvent.getKeyText(nke.getKeyCode()) == hotkey) {
			try {
				toggleClicker();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * togles autoclicker on and off<br>
	 * Makes new instance of Autoclicker so it can "restart"
	 * @throws AWTException 
	 */
	private void toggleClicker() { 
		if (!Autoclicker.running) {
			clicker = new Autoclicker();
		}
		
		if (Autoclicker.running) {
			Autoclicker.stop = true;
			frame.setTitle(frame.frameTitle + "  -  Stopped");
		} else if (!Autoclicker.running) {
			setSettings();
			clicker.start();
			frame.setTitle(frame.frameTitle + "  -  Clicking");
		}
	}

	/**
	 * takes vallues of GUI and translates to autoclicker settings
	 */
	private void setSettings() {
		// click delay
		int h = advancedParceInt(MyFrame.clickIntervalTF[3].getText()) * 3_600_000;
		int m = advancedParceInt(MyFrame.clickIntervalTF[2].getText()) * 3_600;
		int s = advancedParceInt(MyFrame.clickIntervalTF[1].getText()) * 1000;
		int ms = advancedParceInt(MyFrame.clickIntervalTF[0].getText());
		Autoclicker.clickDelay = ms + s + m + h;

		// hold delay
		h = advancedParceInt(MyFrame.holdTimeTF[3].getText()) * 3_600_000;
		m = advancedParceInt(MyFrame.holdTimeTF[2].getText()) * 3_600;
		s = advancedParceInt(MyFrame.holdTimeTF[1].getText()) * 1000;
		ms = advancedParceInt(MyFrame.holdTimeTF[0].getText());
		Autoclicker.holdDelay = ms + s + m + h;

		// ranomize range
		Autoclicker.clickRand = advancedParceInt(MyFrame.randomizeTF[0].getText());
		Autoclicker.holdRand = advancedParceInt(MyFrame.randomizeTF[1].getText());

		// amout of clicks
		Autoclicker.clicks = advancedParceInt(MyFrame.clicksTF.getText());

		// ranomize check box
		Autoclicker.clickRandB = MyFrame.randomizeCB[0].isSelected();
		Autoclicker.holdRandB = MyFrame.randomizeCB[1].isSelected();

		// button to press
		Autoclicker.buttonNum = MyFrame.buttonCB.getSelectedIndex();
	}

	/**
	 * parses argument if fails returns 0
	 * 
	 * @param string string to be parsed
	 * @return parced string or 0
	 */
	private int advancedParceInt(String string) {
		try {
			return Integer.parseInt(string);
		} catch (Exception e) {
			return 0;
		}
	}
}
