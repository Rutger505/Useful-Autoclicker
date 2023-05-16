package GUI;

import resources.Constants;

import javax.swing.*;
import java.awt.*;

/**
 * Frame when Help button is pressed with all information needed
 */
public class HelpGUI extends JFrame {

   /**
    * Make help frame
    */
   public HelpGUI() {
      JComponentFactory components = new JComponentFactory();
      int titleHeight = 20;
      int frameWidth = 360;
      int frameHeight = 325;
      int actualFrameWidth = 334;

      // part 1
      JLabel delayLabel = components.labelFactory("Click interval/Hold time", true, true, new int[]{0, 0, actualFrameWidth, titleHeight});
      JLabel delayTextLabel = components.labelFactory("<html><b>Click interval</b> means the time between clicks. <br>  <b>Hold time</b> means the time between a mouse press and release.</html>", false, false, new int[]{0, 24, actualFrameWidth, 40});

      // part 2
      JLabel randomizeLabel = components.labelFactory("Randomize click interval", true, true, new int[]{0, 72, actualFrameWidth, titleHeight});

      JLabel randomizeTextLabel = components.labelFactory("<html><b>Randomize click interval</b> means there is a random number around the interval depending on range.<br> <b>Example:</b> If interval is 100ms with a random interval range of 20ms the click interval will be a random number between 80 and 120.</html>", false, false, new int[]{0, 96, actualFrameWidth, 70});

      // part 3
      JLabel miscellaneousLabel = components.labelFactory("Clicks/Button/Hotkey", true, true, new int[]{0, 174, actualFrameWidth, titleHeight});
      JLabel miscellaneousTextLabel = components.labelFactory("<html><b>Clicks</b> means how many times the Autoclicker will click.<br> <b>Button</b> means the button that the Autoclicker will press.<br> <b>Hotkey</b> means the button to activate the Autoclicker.<br><b>Autoclick on hold</b> means that if you hold down the button that is selected the autoclicker will start, when you release the mouse button the autoclicker will stop.<br></html>", false, false, new int[]{0, 198, actualFrameWidth, 84});


      // adding components
      add(delayLabel);
      add(delayTextLabel);

      add(randomizeLabel);
      add(randomizeTextLabel);

      add(miscellaneousLabel);
      add(miscellaneousTextLabel);

      setIconImage(Constants.FRAME_ICON);
      setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
      setSize(frameWidth, frameHeight);
      getContentPane().setBackground(Color.white);
      setTitle("Help");
      setLayout(null);
      setResizable(false);
      setAlwaysOnTop(true);
      setVisible(false);
   }
}