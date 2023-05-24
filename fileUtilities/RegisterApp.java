package fileUtilities;

import mslinks.ShellLinkException;
import mslinks.ShellLinkHelper;

import java.io.IOException;
import java.nio.file.Paths;

public class RegisterApp {

   /**
    * Creates a shortcut in the start menu
    */
   public RegisterApp() {
      // get data paths
      String programFileName = "\\Useful-Autoclicker.jar";
      String linkFileName = "\\Useful-Autoclicker.lnk";
      String targetPath = Paths.get("").toAbsolutePath().normalize() + programFileName;
      String linkPath = Paths.get(ClickerData.WINDOWS_DRIVE + "Users\\" + ClickerData.USER + "\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs").toAbsolutePath().normalize() + linkFileName;

      try {
         ShellLinkHelper.createLink(targetPath, linkPath);
      } catch (IOException | ShellLinkException ignored) {
      }
   }
}
