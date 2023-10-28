package settings;

import java.io.Serializable;

public class SettingsObject implements Serializable {
    // Autoclicker
    private int clicks;

    private int[] clickDelayArray;


    private int[] holdDelayArray;

    private boolean shouldRandomizeClick;
    private boolean shouldRandomizeHold;

    private int clickRandomizeRange;

    private int holdRandomizeRange;

    private int buttonNumber;

    // inputListener
    private int hotkey;
    private boolean autoclickOnMouseHold;

    public SettingsObject() {
        clicks = Settings.getClicks();
        clickDelayArray = Settings.getClickDelayArray();

        holdDelayArray = Settings.getHoldDelayArray();

        shouldRandomizeClick = Settings.shouldRandomizeClick();
        shouldRandomizeHold = Settings.shouldRandomizeHold();

        clickRandomizeRange = Settings.getClickRandomizeRange();
        holdRandomizeRange = Settings.getHoldRandomizeRange();

        buttonNumber = Settings.getButtonNumber();

        hotkey = Settings.getHotkey();
        autoclickOnMouseHold = Settings.shouldAutoclickOnMouseHold();
    }

    public void transferSettings() {
        Settings.setClicks(clicks);
        Settings.setClickDelay(clickDelayArray);

        Settings.setHoldDelay(holdDelayArray);

        Settings.setShouldRandomizeClick(shouldRandomizeClick);
        Settings.setShouldRandomizeHold(shouldRandomizeHold);

        Settings.setClickRandomizeRange(clickRandomizeRange);
        Settings.setHoldRandomizeRange(holdRandomizeRange);

        Settings.setButtonNumber(buttonNumber);

        Settings.setHotkey(hotkey);
        Settings.setAutoclickOnMouseHold(autoclickOnMouseHold);
    }
}
