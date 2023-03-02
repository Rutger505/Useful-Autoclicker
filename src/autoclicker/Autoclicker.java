package autoclicker;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class Autoclicker extends Thread {

	static Autoclicker singleton;
	static boolean stop = false;
	static int clicks = 0;
	static int holdDelay = 0;
	static int clickDelay = 100;

	@Override
	public void run() {
		// checking to click for a infinite or finite number
		switch (clicks) {
		// 0 clicks = inf clicks
		case 0:
			while (true) {
				clickCycle(-0);
				if (stop) {
					stop = false;
					break;
				}
			}
			break;

		// if not 0 clicks click for how many "clicks" is
		default:
			for (int i = 0; i < clicks; i++) {
				clickCycle(i);
				if (stop) {
					stop = false;
					break;
				}
			}
			Main.running = false;
		}
	}

	/**
	 * sets mouse delay to 5 if lower.<br>
	 * 
	 * mouse press hold delay. <br>
	 * 
	 * mouse release click delay.
	 */
	public void clickCycle(int i) {
		if (clickDelay < 5) {
			clickDelay = 5;
		}
		try {
			mousePress();
			wait(holdDelay);
			mouseRelease();
			wait(clickDelay);
		} catch (AWTException e) {
			System.out.println("error in cickCycle");
			System.out.println(e);
		}
	}

	/**
	 * Press mouse button
	 * 
	 * @throws AWTException
	 */
	public void mousePress() throws AWTException {		
		try {
			Robot robot = new Robot();
			robot.mousePress(InputEvent.BUTTON1_MASK);
		} catch (AWTException e) {
			System.out.println("error in mousePress");
			System.out.println(e);
		}
	}

	/**
	 * release mouse button
	 * 
	 * @throws AWTException
	 */
	public void mouseRelease() {
		try {
			Robot robot = new Robot();
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
		} catch (AWTException e) {
			System.out.println("error in mouseRelease");
			System.out.println(e);
		}
	}

	/**
	 * makes program sleep so many milliseconds
	 * 
	 * @param ms how many ms to sleep
	 */
	public void wait(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
		}
	}
}