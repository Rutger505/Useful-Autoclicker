package autoclicker;

import java.net.ServerSocket;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import fileHider.FileHider;

public class Main implements NativeKeyListener {
	public static Double AutoclickerVers = 1.0;
	
	private static Autoclicker clicker;
	private static GUI frame;
	public static boolean newHotkey = false;
	public static int hotkey = 59; // F1
	public static String hotkeyT = NativeKeyEvent.getKeyText(hotkey); // F1
	
	@SuppressWarnings("resource")
	public static void main(String[] args){
		// only one instance of program
		try {
			// when the program is launched twice the second cant connect to port and terminates
			new ServerSocket(1324);
		} catch (Exception e) {
			System.exit(0);
		}
		
		// Clicker
		clicker = new Autoclicker();
		// GUI
		frame = new GUI();
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
	public void nativeKeyReleased(NativeKeyEvent nke) {
		if (newHotkey) {
			hotkey = nke.getKeyCode();
			hotkeyT = NativeKeyEvent.getKeyText(nke.getKeyCode());
			GUI.hotkeyB.setText("Select hotkey(" + hotkeyT + ")");
			newHotkey = false;
		} else if (nke.getKeyCode() == hotkey) {
			toggleClicker();
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
		int h = advancedParceInt(GUI.clickIntervalTF[3].getText()) * 3_600_000;
		int m = advancedParceInt(GUI.clickIntervalTF[2].getText()) * 3_600;
		int s = advancedParceInt(GUI.clickIntervalTF[1].getText()) * 1000;
		int ms = advancedParceInt(GUI.clickIntervalTF[0].getText());
		Autoclicker.clickDelay = ms + s + m + h;

		// hold delay
		h = advancedParceInt(GUI.holdTimeTF[3].getText()) * 3_600_000;
		m = advancedParceInt(GUI.holdTimeTF[2].getText()) * 3_600;
		s = advancedParceInt(GUI.holdTimeTF[1].getText()) * 1000;
		ms = advancedParceInt(GUI.holdTimeTF[0].getText());
		Autoclicker.holdDelay = ms + s + m + h;

		// ranomize range
		Autoclicker.clickRand = advancedParceInt(GUI.randomizeTF[0].getText());
		Autoclicker.holdRand = advancedParceInt(GUI.randomizeTF[1].getText());

		// amout of clicks
		Autoclicker.clicks = advancedParceInt(GUI.clicksTF.getText());

		// ranomize check box
		Autoclicker.clickRandB = GUI.randomizeCB[0].isSelected();
		Autoclicker.holdRandB = GUI.randomizeCB[1].isSelected();

		// button to press
		Autoclicker.buttonNum = GUI.buttonCB.getSelectedIndex() + 1;
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
