import settings.Settings;
import utils.Logger;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Autoclicker {
    private Thread autoclickerThread;
    private final Settings settings;
    private final Random random = new Random();
    private final InputListener inputListener;
    private Robot robot;
    private boolean running;

    /**
     * Sets up robot
     */
    public Autoclicker(InputListener inputListener) {
        this.inputListener = inputListener;
        settings = Settings.getInstance();
        try {
            robot = new Robot();
        } catch (AWTException e) {
            Logger.showError("Error starting Autoclicker try restarting the Autoclicker");
            Logger.fatal("Error creating robot (Object that simulates clicks");
            System.exit(1);
        }
    }

    /**
     * @return if program is running
     */
    public boolean isRunning() {
        return running;
    }

    public void start() {
        Logger.info("Starting Autoclicker");
        autoclickerThread = new Thread(this::autoclickerMain);
        autoclickerThread.start();
    }

    public void stop() {
        Logger.info("Stopping Autoclicker");
        autoclickerThread.interrupt();
    }

    /**
     * Driver method
     */
    public void autoclickerMain() {
        running = true;

        if (settings.getClicks() == 0) {
            Logger.trace("Entering infinite clicker loop");
            while (!Thread.currentThread().isInterrupted()) {
                randomizeDelay();

                clickCycle();
            }
        } else {
            Logger.trace("Entering limited clicker loop");
            for (int i = 0; i < settings.getClicks() && !Thread.interrupted(); i++) {
                randomizeDelay();

                clickCycle();
            }
        }
        Logger.trace("End of clicker loop");
        running = false;
        inputListener.stopClicker();
    }

    /**
     * Randomizes delay of click and hold delay.
     */
    private void randomizeDelay() {
        if (settings.shouldRandomizeClick() && settings.getClickRandomizeRange() > 0) {
            settings.setClickDelay(Math.abs(settings.getClickDelayOriginal() + random.nextInt(settings.getClickRandomizeRange() * 2) - settings.getClickRandomizeRange()));
        }

        if (settings.shouldRandomizeHold() && settings.getHoldRandomizeRange() > 0) {
            settings.setHoldDelay(Math.abs(settings.getHoldDelayOriginal() + random.nextInt(settings.getHoldRandomizeRange() * 2) - settings.getHoldRandomizeRange()));
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
        waitMs(settings.getHoldDelay());
        mouseRelease();
        waitMs(settings.getClickDelay());
    }

    /**
     * Press mouse button
     */
    private void mousePress() {
        try {
            robot.mousePress(settings.getButton());
        } catch (RuntimeException e) {
            try {
                robot = new Robot();
                robot.mousePress(settings.getButton());
            } catch (AWTException ignored) {
            }
            Logger.error("error in mouse press");
        }
    }

    /**
     * Release mouse button
     */
    private void mouseRelease() {
        try {
            robot.mouseRelease(settings.getButton());
        } catch (RuntimeException e) {
            try {
                robot = new Robot();
                robot.mouseRelease(settings.getButton());
            } catch (AWTException ignored) {
            }
            Logger.error("error in mouse release");
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