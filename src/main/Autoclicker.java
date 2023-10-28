package main;

import errorHandeling.Error;
import settings.Settings;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Autoclicker {
    private Thread autoclickerThread;
    private final Random random = new Random();
    private final InputListener inputListener;
    private Robot robot;
    private boolean running;

    /**
     * Sets up robot
     */
    public Autoclicker(InputListener inputListener) {
        this.inputListener = inputListener;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            Error.showError("Error starting Autoclicker", "Error starting Autoclicker try restarting the autoclicker", "(Autoclicker) Error creating robot");
        }
    }

    /**
     * @return if program is running
     */
    public boolean isRunning() {
        return running;
    }

    public void start() {
        autoclickerThread = new Thread(this::autoclickerMain);
        autoclickerThread.start();
    }

    public void stop() {
        autoclickerThread.interrupt();
    }

    /**
     * Driver method
     */
    public void autoclickerMain() {
        running = true;

        if (Settings.getClicks() == 0) {
            while (!Thread.currentThread().isInterrupted()) {
                randomizeDelay();

                clickCycle();
            }
        } else {
            for (int i = 0; i < Settings.getClicks() && !Thread.interrupted(); i++) {
                randomizeDelay();

                clickCycle();
            }
        }
        running = false;
        inputListener.stopClicker();
    }

    /**
     * Randomizes delay of click and hold delay.
     */
    private void randomizeDelay() {
        if (Settings.shouldRandomizeClick() && Settings.getClickRandomizeRange() > 0) {
            Settings.setClickDelay(Math.abs(Settings.getClickDelayOriginal() + random.nextInt(Settings.getClickRandomizeRange() * 2) - Settings.getClickRandomizeRange()));
        }

        if (Settings.shouldRandomizeHold() && Settings.getHoldRandomizeRange() > 0) {
            Settings.setHoldDelay(Math.abs(Settings.getHoldDelayOriginal() + random.nextInt(Settings.getHoldRandomizeRange() * 2) - Settings.getHoldRandomizeRange()));
        }
    }

    /**
     * Does a full click cycle:
     * <p>
     * mouse press<br>
     * hold delay <br>
     * mouse release <br>
     * click delay
     */
    private void clickCycle() {
        mousePress();
        waitMs(Settings.getHoldDelay());
        mouseRelease();
        waitMs(Settings.getClickDelay());
    }

    /**
     * Press mouse button
     */
    private void mousePress() {
        try {
            robot.mousePress(Settings.getButton());
        } catch (RuntimeException e) {
            try {
                robot = new Robot();
                robot.mousePress(Settings.getButton());
            } catch (AWTException ignored) {
            }
            System.out.println("error in mouse press");
        }
    }

    /**
     * Release mouse button
     */
    private void mouseRelease() {
        try {
            robot.mouseRelease(Settings.getButton());
        } catch (RuntimeException e) {
            try {
                robot = new Robot();
                robot.mouseRelease(Settings.getButton());
            } catch (AWTException ignored) {
            }
            System.out.println("error in mouse release");
        }
    }

    /**
     * Sleep
     *
     * @param ms how many ms to sleep if less than 0 set to 0.
     */
    private void waitMs(long ms) {
        try {
            long startTime = System.nanoTime();
            long elapsedTime;
            while (true) {
                elapsedTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
                if (elapsedTime >= ms) {
                    break;
                }
                Thread.sleep(1);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}