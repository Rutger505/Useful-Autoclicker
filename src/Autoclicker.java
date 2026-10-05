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
    private volatile boolean running;

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
        running = true;
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
        if (settings.getClicks() == 0) {
            Logger.trace("Entering infinite clicker loop");
            while (!Thread.currentThread().isInterrupted()) {
                clickCycle();
            }
        } else {
            Logger.trace("Entering limited clicker loop");
            for (int i = 0; i < settings.getClicks() && !Thread.interrupted(); i++) {
                clickCycle();
            }
        }
        Logger.trace("End of clicker loop");
        running = false;
        inputListener.stopClicker();
    }

    private long randomize(long delay, boolean shouldRandomize, int range) {
        if (!shouldRandomize || range <= 0) {
            return delay;
        }
        return Math.abs(delay + random.nextInt(range * 2) - range);
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
        waitMs(randomize(settings.getHoldDelay(), settings.shouldRandomizeHold(), settings.getHoldRandomizeRange()));
        mouseRelease();
        waitMs(randomize(settings.getClickDelay(), settings.shouldRandomizeClick(), settings.getClickRandomizeRange()));
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