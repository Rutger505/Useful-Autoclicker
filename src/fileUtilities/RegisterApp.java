package fileUtilities;

import mslinks.ShellLinkHelper;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.nio.file.Paths;

public class RegisterApp {


    /**
     * Creates a shortcut in the start menu
     */
    public static void RegisterApp() {
        try {
            File windowsDrive = getWindowsDrive();
            String user = System.getProperty("user.name");
            // get data paths
            String programFileName = "\\Useful-Autoclicker.jar";
            String linkFileName = "\\Useful-Autoclicker.lnk";
            String targetPath = Paths.get("").toAbsolutePath().normalize() + programFileName;
            String linkPath = Paths.get(windowsDrive + "Users\\" + user + "\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs").toAbsolutePath().normalize() + linkFileName;


            ShellLinkHelper.createLink(targetPath, linkPath);
        } catch (Exception ignored) {
        }
    }

    /**
     * Gets windows drive
     *
     * @return windows drive
     */
    private static File getWindowsDrive() {
        FileSystemView fileSystemView = FileSystemView.getFileSystemView();
        File[] drives = File.listRoots();
        File windowsDrive = null;
        for (File drive : drives) {
            // if drive is local disk (windows disk)
            String systemDriveDescription = fileSystemView.getSystemTypeDescription(drive);
            if (systemDriveDescription.equals("Local Disk")) {
                // set this drive to Windows Drive
                windowsDrive = drive;
                break;
            }
        }
        return windowsDrive;
    }
}
