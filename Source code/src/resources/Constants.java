package resources;

import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Image;
import java.awt.Font;

public class Constants {
   // icon
   private static final String ICON_PATH = "/resources/icon.png";
   public static final Image FRAME_ICON = new ImageIcon(Constants.class.getResource(ICON_PATH)).getImage();

   // colors
   public static final Color FRAME_COLOR = new Color(255, 255, 255);
   public static final Color DEFAULT_BACKGROUND_COLOR = new Color(235, 235, 235);

   // font
   public static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 12);

   private Constants() {
      throw new IllegalStateException("Constants class");
   }
}
