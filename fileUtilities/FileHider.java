package fileUtilities;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Hides file (Works with jar file. NOT with Eclipse project)
 */
public class FileHider {
   /**
    * Hides file (Works with jar file. NOT with Eclipse project)
    *
    * @param filename file to be hidden
    */
   public FileHider(String filename) {
      try {
         File file = new File(filename);
         Path path = Paths.get(file.getAbsolutePath());

         Files.setAttribute(path, "dos:hidden", Boolean.TRUE, LinkOption.NOFOLLOW_LINKS);
      } catch (Exception ignored) {
      }
   }
}
