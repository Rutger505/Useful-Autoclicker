package errorHandeling;

import javax.swing.*;

public class Error {
    private Error() {
        throw new IllegalStateException("Utility class");
    }

    public static void showError(String title, String message, String printMessage) {
        System.out.println(printMessage);
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
    }
}
