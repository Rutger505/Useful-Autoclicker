package fileUtilities;

import main.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Hides file (Works with jar file. NOT with Eclipse project)
 */
public class FileVisibility {

    private FileVisibility() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Hides file (Works with jar file. NOT with Eclipse project)
     *
     * @param filename file to be hidden
     * @param hidden   true if file should be hidden, false if file should be visible.
     */
    public static void changeVisibility(String filename, boolean hidden) {
        try {
            File file = new File(filename);
            Path path = Paths.get(file.getAbsolutePath());

            Files.setAttribute(path, "dos:hidden", hidden, LinkOption.NOFOLLOW_LINKS);

            Logger.info(filename + " is now " + (hidden ? "hidden" : "visible"));
        } catch (IOException e) {
            Logger.warn("(FileHider) " + filename + " could not be hidden");
        }
    }
}
