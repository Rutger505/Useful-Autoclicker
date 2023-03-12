package autoclicker;

import java.awt.AWTException;
import java.awt.Robot;
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

	// functional vars
	public static boolean running = false;
	private Robot robot;

	public Autoclicker() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
		}
	}

	private Random r = new Random();
	public static boolean stop = false;
	private int clickDelayO;
	private int holdDelayO;
	private int button;

	@Override
	public void run() {
		running = true;

		// setting wich button to press
		button = MouseEvent.getMaskForButton(buttonNum + 1);

		// if no click delay set to 1 to prevent crashing or lagging
		if (clickDelay < 1) {
			clickDelay = 1;
		}

		switch (clicks) {
		// 0 clicks = inf clicks
		case 0:
			while (true) {
				if (clickRandB || holdRandB) {
					randomizeDelay();
				}

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
				if (clickRandB || holdRandB) {
					randomizeDelay();
				}

				clickCycle();

				if (stop) {
					stop = false;
					break;
				}
			}
		}
		running = false;
	}

	/**
	 * 
	 * calculates random delay if turned on<br>
	 * clickRand and holdRand as the range<br>
	 * delay will range from -clickRand to +clickRand
	 * 
	 */
	public void randomizeDelay() {
		clickDelayO = clickDelay;
		if (clickRandB) {
			clickDelay = clickDelayO + r.nextInt(clickRand * 2 + 1) - clickRand;
		}

		holdDelayO = holdDelay;
		if (holdRandB) {
			holdDelay = holdDelayO + r.nextInt(holdRand * 2 + 1) - holdRand;
		}
	}

	/**
	 * does a full click cycle:
	 * 
	 * mouse press<br>
	 * hold delay <br>
	 * mouse release <br>
	 * click delay
	 * 
	 * @throws AWTException
	 */
	public void clickCycle() {
		mousePress();
		wait(holdDelay);
		mouseRelease();
		wait(clickDelay);
	}

	/**
	 * press mouse button
	 * 
	 * @throws AWTException
	 */
	public void mousePress() {
		try {
			robot.mousePress(button);
		} catch (Exception e) {
			try {
				robot = new Robot();
			} catch (AWTException e1) {
			}
			System.out.println("error in mouse press");
		}
	}

	/**
	 * release mouse button
	 * 
	 * @throws AWTException
	 */
	public void mouseRelease() {
		try {
			robot.mouseRelease(button);
		} catch (Exception e) {
			try {
				robot = new Robot();
			} catch (AWTException e1) {
			}
			System.out.println("error in mouse release");
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
		}
	}
}