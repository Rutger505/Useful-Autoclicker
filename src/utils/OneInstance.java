package utils;

import java.io.IOException;
import java.net.ServerSocket;

public class OneInstance {
    private static final int PORT = 1324;
    private static ServerSocket prevention;


    private OneInstance() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Checks if the program is already running. Exits if it is.
     */
    public static void Activate() {

        if (isRunning()) {
            Logger.showError("Program is already running. Exiting...");
            Logger.fatal("Program is already running. Exiting...");
            System.exit(1);
        }
        Logger.info("[INFO] Socket opened");
    }

    /**
     * Checks if the program is already running by using an internet port.
     */
    private static boolean isRunning() {
        try {
            prevention = new ServerSocket(PORT);
            return false;
        } catch (IOException e) {
            return true;
        }
    }
}
