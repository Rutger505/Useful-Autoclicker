package main;

import resources.Constants;

import javax.swing.*;
import java.awt.*;

/**
 * Frame when Help button is pressed with all information needed
 */
public class HelpGUI extends JFrame {
   public static final int HELP_FRAME_WIDTH = 350;
   public static final int HELP_FRAME_HEIGHT = 290;
   public static final int HELP_FRAME_ACTUAL_WIDTH = 334;

   public HelpGUI(GUI gui) {
      int titleHeight = 20;

      // part 1
      JLabel delayLabel = gui.labelFactory("Click interval/Hold time", true, true, new int[]{0, 0, HELP_FRAME_ACTUAL_WIDTH, titleHeight});
      JLabel delayTextLabel = gui.labelFactory("<html><b>Click interval</b> means the time between clicks. <br> " + " <b>Hold time</b> means the time between a mouse press and release.</html>", false, false, new int[]{0, 24, HELP_FRAME_ACTUAL_WIDTH, 40});

      // part 2
      JLabel randomizeLabel = gui.labelFactory("Randomize click interval", true, true, new int[]{0, 72, HELP_FRAME_ACTUAL_WIDTH, titleHeight});

      JLabel randomizeTextLabel = gui.labelFactory("<html><b>Randomize click interval</b> means there is a random number around the interval depending on range.<br>" + " <b>Example:</b> If interval is 100ms with a random interval range of 20ms the click interval will be a random number between 80 and 120.</html>", false, false, new int[]{0, 96, HELP_FRAME_ACTUAL_WIDTH, 70});

      // part 3
      JLabel miscellaneousLabel = gui.labelFactory("Clicks/Button/Hotkey", true, true, new int[]{0, 174, HELP_FRAME_ACTUAL_WIDTH, titleHeight});

      JLabel miscellaneousTextLabel = gui.labelFactory("<html><b>Clicks</b> means how many times the Autoclicker will click.<br>" + " <b>Button</b> means the button that the Autoclicker will press.<br>" + " <b>Hotkey</b> means the button to activate the Autoclicker</html>", false, false, new int[]{0, 198, HELP_FRAME_ACTUAL_WIDTH, 50});


      // adding components
      gui.addComponent(delayLabel, this);
      gui.addComponent(delayTextLabel, this);

      gui.addComponent(randomizeLabel, this);
      gui.addComponent(randomizeTextLabel, this);

      gui.addComponent(miscellaneousLabel, this);
      gui.addComponent(miscellaneousTextLabel, this);


      this.setIconImage(Constants.FRAME_ICON);
      this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
      this.setSize(HELP_FRAME_WIDTH, HELP_FRAME_HEIGHT);
      this.getContentPane().setBackground(Color.white);
      this.setTitle("Help");
      this.setLayout(null);
      this.setResizable(false);
      this.setAlwaysOnTop(true);
      this.setVisible(false);
   }
}
