package settings;

import java.io.Serializable;

public class SettingsObject implements Serializable {
    // Autoclicker
    private final int clicks;

    private final int[] clickDelayArray;


    private final int[] holdDelayArray;

    private final boolean shouldRandomizeClick;
    private final boolean shouldRandomizeHold;

    private final int clickRandomizeRange;

    private final int holdRandomizeRange;

    private final int buttonNumber;

    // inputListener
    private final int hotkey;
    private final boolean autoclickOnMouseHold;

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
