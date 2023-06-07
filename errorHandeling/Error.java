package errorHandeling;

import javax.swing.*;

public class Error {
   private static boolean unidentifiedChangeShown = false;

   private Error() {
      throw new IllegalStateException("Utility class");
   }

   public static void showError(String title, String message, String printMessage) {
      System.out.println(printMessage);
      JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
   }

   public static void unidentifiedChangeError(){
      if(unidentifiedChangeShown){
         return;
      }
      Error.showError("(Reading data) Error loading settings", "<html>There was an unauthorized change in the setting file. Try avoiding changing the contents of this file</html>", "(File read) Error reading file");
      unidentifiedChangeShown = true;
   }
}
