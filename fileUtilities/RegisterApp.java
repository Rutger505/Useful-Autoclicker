package fileUtilities;

import errorHandeling.Error;
import mslinks.ShellLink;

import java.nio.file.Paths;

public class RegisterApp {


   public RegisterApp() {
      // get data path
      String absolutePath = Paths.get("").toAbsolutePath().normalize().toString();
      String dataPath = Paths.get(ClickerData.WINDOWS_DRIVE + "Users/" + ClickerData.USER + "/AppData/Roaming/Microsoft/Windows/Start Menu/Programs/").toAbsolutePath().normalize().toString();

      try {
         Error.showError("Created shortcut here", "Created shortcut here: " + dataPath + "/Useful-Autoclicker.lnk\n Shortcut from here: " + absolutePath + "/Useful-Autoclicker.jar", "Created shortcut here: " + dataPath + "/Test.lnk\n Shortcut from here: " + absolutePath);

         ShellLink.createLink(absolutePath + "/Useful-Autoclicker.jar", dataPath + "/Useful-Autoclicker.lnk");

      } catch (Exception e) {
         Error.showError("Error creating Link", e.toString(), e.toString());
         e.printStackTrace();
      }
   }
}
