package autoclicker;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class Main implements NativeKeyListener {
	MyFrame frame = new MyFrame();

	public static void main(String[] args) {
		// ############## SETTINGS ##############
		Autoclicker.clickDelay = 1000;
		Autoclicker.clickDelay = 0;
		Autoclicker.clicks = 10;

		// key listener
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException e) {
		}
		GlobalScreen.addNativeKeyListener(new Main());
	}

	// ############ SETTING #################
	static String hotkey = "T";
	static boolean running = false;
	Autoclicker clicker = new Autoclicker();
	public void nativeKeyPressed(NativeKeyEvent nke) {
		// when hotkey is pressed toggle the autoclicker
		if (NativeKeyEvent.getKeyText(nke.getKeyCode()) == hotkey) {
			
			System.out.println(hotkey + " was pressed " + running);

			// if going to start/restart make new object
			if (!running) {
				clicker = new Autoclicker();
			}
			// if running stop if not running start
			if (running) {
				Autoclicker.stop = true;
				running = false;
			} else if (!running) {
				clicker.start();
				running = true;
			}
		}
	}
}
