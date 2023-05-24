package fileUtilities;

import errorHandeling.Error;
import mslinks.ShellLinkHelper;

import java.nio.file.Paths;

public class RegisterApp {

   /**
    * Creates a shortcut in the start menu
    */
   public RegisterApp() {
      // get data path
      String absolutePath = Paths.get("").toAbsolutePath().normalize().toString();
      String dataPath = Paths.get(ClickerData.WINDOWS_DRIVE + "Users/" + ClickerData.USER + "/AppData/Roaming/Microsoft/Windows/Start Menu/Programs").toAbsolutePath().normalize().toString();

      String autoclicker = "/Useful-Autoclicker.jar";
      String autoclickerLink = "/Useful-Autoclicker.lnk";

      String targetPath = absolutePath + autoclicker;
      String linkPath = dataPath + autoclickerLink;

      try {
         ShellLinkHelper.createLink(targetPath, linkPath);
         Error.showError("Created shortcut here", "Created shortcut here: " + linkPath + "\n Shortcut from here: " + targetPath, "Created shortcut here: " + linkPath + "\n Shortcut from here: " + targetPath);
      } catch (Exception e) {
         Error.showError("Error creating Link", e.toString(), e.toString());
         e.printStackTrace();
      }
   }
}
