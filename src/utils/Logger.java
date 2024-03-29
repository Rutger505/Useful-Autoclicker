package utils;

import javax.swing.*;

public class Logger {
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";

    private Logger() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Logs a message to the console
     *
     * @param message message to be logged
     */
    public static void trace(String message) {
        System.out.println("[TRACE] " + message);
    }

    /**
     * Logs a message to the console
     *
     * @param message message to be logged
     */
    public static void info(String message) {
        System.out.println("[INFO] " + message);
    }


    /**
     * Logs a warning to the console
     *
     * @param message message to be logged
     */
    public static void warn(String message) {
        System.out.println(YELLOW + "[WARN] " + message + RESET);
    }

    /**
     * Logs an error to the console
     *
     * @param message message to be logged
     */
    public static void error(String message) {
        System.out.println(RED + "[ERROR] " + message + RESET);
    }

    /**
     * Logs an fatal error to the console
     *
     * @param message message to be logged
     */
    public static void fatal(String message) {
        System.out.println(RED + "[FATAL] " + message + RESET);
    }

    /**
     * Shows an error message popup
     *
     * @param message message of the error popup
     */
    public static void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Useful-Autoclicker", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Shows an info message popup
     *
     * @param message message of the info popup
     */
    public static void showInfo(String message) {
        JOptionPane.showMessageDialog(null, message, "Useful-Autoclicker", JOptionPane.INFORMATION_MESSAGE);
    }
}
