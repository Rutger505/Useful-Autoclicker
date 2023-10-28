package main;

import errorHandeling.Error;

import java.io.IOException;
import java.net.ServerSocket;

public class OneInstance {
    private static final int port = 1324;
    private static ServerSocket prevention;


    private OneInstance() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Checks if the program is already running. Exits if it is.
     */
    public static void Activate() {
        if (isRunning()) {
            Error.showError("Already running", "Program is already running. Exiting...", "Program is already running. Exiting...");
            System.exit(1);
        }
    }

    /**
     * Checks if the program is already running by using an internet port.
     */
    private static boolean isRunning() {
        try {
            prevention = new ServerSocket(port);
            return false;
        } catch (IOException e) {
            return true;
        }
    }
}