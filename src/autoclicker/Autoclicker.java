package autoclicker;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class Autoclicker extends Thread {

	static Autoclicker singleton;
	boolean stop = false;
	static int clicks = 0;
	static int holdDelay = 0;
	static int clickDelay = 100;

	
	@Override
	public void run() {
		singleton = this;
		// checking to click for a infinite or finite number
		switch (clicks) {
		// 0 clicks = inf clicks
		case 0:
			while (true) {
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
				clickCycle();
				if (stop) {
					System.out.println("i am trying to stop");
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
	public void clickCycle() {
		if (clickDelay < 5) {
			clickDelay = 5;
		}

		try {
			mousePress();
			wait(holdDelay);
			mouseRelease();
			wait(clickDelay);
		} catch (AWTException e) {
		}
	}

	/**
	 * Press mouse button
	 * 
	 * @throws AWTException
	 */
	public void mousePress() throws AWTException {
		Robot robot = new Robot();
		robot.mousePress(InputEvent.BUTTON1_MASK);
	}

	/**
	 * release mouse button
	 * 
	 * @throws AWTException
	 */
	public void mouseRelease() throws AWTException {
		Robot robot = new Robot();
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
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