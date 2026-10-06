package utils;

import java.io.File;
import java.io.IOException;

/**
 * Clicks through ydotool, which injects input via uinput so it reaches every app.
 * Needed on Wayland, where AWT's Robot only reaches XWayland windows.
 */
public class Ydotool {
    private static final String EXECUTABLE = "ydotool";
    // GUI order: left, right, middle, side front, side back
    private static final int[] BUTTONS = {0x00, 0x01, 0x02, 0x03, 0x04};
    private static final int DOWN = 0x40;
    private static final int UP = 0x80;
    private static final File DEV_NULL = new File("/dev/null");

    private boolean errorLogged;

    public static boolean isWaylandSession() {
        return System.getenv("WAYLAND_DISPLAY") != null;
    }

    public static boolean isInstalled() {
        String path = System.getenv("PATH");
        if (path == null) {
            return false;
        }
        for (String dir : path.split(File.pathSeparator)) {
            if (new File(dir, EXECUTABLE).canExecute()) {
                return true;
            }
        }
        return false;
    }

    public void press(int buttonNumber) {
        click(DOWN | BUTTONS[buttonNumber]);
    }

    public void release(int buttonNumber) {
        click(UP | BUTTONS[buttonNumber]);
    }

    private void click(int code) {
        ProcessBuilder builder = new ProcessBuilder(EXECUTABLE, "click", "--next-delay=0", String.format("0x%02x", code));
        builder.redirectErrorStream(true);
        builder.redirectOutput(DEV_NULL);
        try {
            Process process = builder.start();
            int exitCode = waitUninterruptibly(process);
            if (exitCode != 0) {
                logOnce("ydotool exited with " + exitCode + ", is ydotoold running? (systemctl --user enable --now ydotool)");
            }
        } catch (IOException e) {
            logOnce("Could not run ydotool " + e);
        }
    }

    /**
     * Waits even when the clicker is stopped mid-click, so a press is never left without its release.
     */
    private static int waitUninterruptibly(Process process) {
        boolean interrupted = false;
        while (true) {
            try {
                int exitCode = process.waitFor();
                if (interrupted) {
                    Thread.currentThread().interrupt();
                }
                return exitCode;
            } catch (InterruptedException e) {
                interrupted = true;
            }
        }
    }

    private void logOnce(String message) {
        if (!errorLogged) {
            errorLogged = true;
            Logger.error(message);
        }
    }
}
