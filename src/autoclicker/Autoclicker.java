package autoclicker;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Autoclicker extends Thread {
	// ## settings from main ##
	public static int clicks = 0;
	public static int clickDelay = 0;
	public static int holdDelay = 0;

	public static boolean clickRandB = false;
	public static boolean holdRandB = false;

	public static int clickRand = 0;
	public static int holdRand = 0;

	public static int buttonNum = 0;

	// idk
	public static Autoclicker singleton;
	// functional vars
	public static boolean stop = false;
	private static int clickDelayR = 0;
	private static int holdDelayR = 0;
	private Random r = new Random();

	private static int button;

	@Override
	public void run() {
		System.out.println("i start");
		// setting wich button to press
		button = MouseEvent.getMaskForButton(buttonNum + 1);

		// if no click delay set to 1 to pre
		if (clickDelay < 1) {
			clickDelay = 1;
		}

		// checking to click for a infinite or finite number
		switch (clicks) {
		// 0 clicks = inf clicks
		case 0:
			while (true) {
				randomizeDelay();
				clickCycle();
				if (stop) {
					stop = false;
					break;
				}
			}
			break;

		// if not 0 clicks click for how many "clicks" is
		default:
			for (int i = 0; i < clicks; i++) {
				randomizeDelay();
				clickCycle();
				if (stop) {
					stop = false;
					break;
				}
			}
			Main.running = false;
		}
		System.out.println("i stop");
	}

	/**
	 * 
	 * calculates random delay if turned on<br>
	 * clickRand and holdRand as the range<br>
	 * delay will range from -clickRand to +clickRand
	 * 
	 */
	public void randomizeDelay() {
		clickDelayR = clickDelay;
		// need to randomize?
		if (clickRandB) {
			clickDelayR = clickDelay + r.nextInt(clickRand * 2 + 1) - clickRand;
		}
		holdDelayR = holdDelay;
		// need to randomize?
		if (holdRandB) {
			holdDelayR = holdDelay + r.nextInt(holdRand * 2 + 1) - holdRand;
		}
	}

	/**
	 * does a full click cycle:
	 *  
	 * mouse press<br>
	 * hold delay <br>
	 * mouse release <br>
	 * click delay
	 */
	public void clickCycle() {
		mousePress();
		wait(clickDelayR);
		mouseRelease();
		wait(holdDelayR);
	}

	/**
	 * press mouse button
	 */
	public void mousePress() {
		try {
			Robot robot = new Robot();
			robot.mousePress(button);
		} catch (AWTException e) {
			System.out.println("error in mouse press");
			e.printStackTrace();
		}
	}

	/**
	 * release mouse button
	 */
	public void mouseRelease() {
		try {
			Robot robot = new Robot();
			robot.mouseRelease(button);
		} catch (AWTException e) {
			System.out.println("error in mouse release");
			e.printStackTrace();
		}
	}

	/**
	 * makes program sleep so many milliseconds<br>
	 * if
	 * 
	 * @param ms how many ms to sleep if less than 0 set to 0
	 */
	public void wait(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}