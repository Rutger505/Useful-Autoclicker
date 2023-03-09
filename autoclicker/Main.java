package autoclicker;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class Main implements NativeKeyListener {
	private Autoclicker clicker = new Autoclicker();
	public static boolean running = false;
	public static boolean newHotkey = false;

	// ############ SETTING #################
	public static String hotkey = "T";

	public static void main(String[] args) {
		// GUI
		new MyFrame();
		// key listener
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException e) {
		}
		GlobalScreen.addNativeKeyListener(new Main());
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
			toggleClicker();
		}

	}

	/**
	 * togles autoclicker on and off<br>
	 * Makes new instance of Autoclicker so it can "restart"
	 */
	private void toggleClicker() {
		// if going to start/restart make new object
		if (!running) {
			clicker = new Autoclicker();
		}
		// if running stop if not running start
		if (running) {
			Autoclicker.stop = true;
			running = false;
		} else if (!running) {
			setSettings();
			clicker.start();
			running = true;
		}
	}

	/**
	 * takes vallues of GUI and translates to autoclicker settings
	 */
	private void setSettings() {
		// click delay
		int h = advancedParceInt(MyFrame.delayClickTF[3].getText()) * 3_600_000;
		int m = advancedParceInt(MyFrame.delayClickTF[2].getText()) * 3_600;
		int s = advancedParceInt(MyFrame.delayClickTF[1].getText()) * 1000;
		int ms = advancedParceInt(MyFrame.delayClickTF[0].getText());
		Autoclicker.clickDelay = ms + s + m + h;

		// hold delay
		h = advancedParceInt(MyFrame.delayHoldTF[3].getText()) * 3_600_000;
		m = advancedParceInt(MyFrame.delayHoldTF[2].getText()) * 3_600;
		s = advancedParceInt(MyFrame.delayHoldTF[1].getText()) * 1000;
		ms = advancedParceInt(MyFrame.delayHoldTF[0].getText());
		Autoclicker.holdDelay = ms + s + m + h;

		// ranomize range
		Autoclicker.clickRand = advancedParceInt(MyFrame.randomizeTF[0].getText());
		Autoclicker.holdRand = advancedParceInt(MyFrame.randomizeTF[1].getText());

		// amout of clicks
		Autoclicker.clicks = advancedParceInt(MyFrame.clicksTF[0].getText());

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
