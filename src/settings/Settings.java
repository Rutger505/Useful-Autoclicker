package settings;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

import java.awt.event.InputEvent;
import java.io.Serializable;

public class Settings implements Serializable {
    private static Settings instance;

    // Autoclicker
    private int clicks = 0;

    private long clickDelay = 100;
    private long clickDelayOriginal = 100;
    private int[] clickDelayArray = {100, 0, 0, 0};


    private long holdDelay = 10;
    private long holdDelayOriginal = 10;
    private int[] holdDelayArray = {10, 0, 0, 0};

    private boolean shouldRandomizeClick = false;
    private boolean shouldRandomizeHold = false;

    private int clickRandomizeRange = 20;

    private int holdRandomizeRange = 20;

    private int buttonNumber = 0;
    private int button = InputEvent.getMaskForButton(buttonNumber + 1);

    // inputListener
    private int hotkey = 59;
    private String hotkeyText = NativeKeyEvent.getKeyText(hotkey);
    private boolean autoclickOnMouseHold = false;

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    public static void setNewInstance(Settings newInstance) {
        instance = newInstance;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
        SaveSettings.saveSettings();
    }

    public long getClickDelay() {
        return clickDelay;
    }

    public void setClickDelay(long clickDelay) {
        this.clickDelay = clickDelay;
        SaveSettings.saveSettings();
    }

    public void setClickDelay(int[] clickDelayRaw) {
        clickDelay = 0;
        clickDelay += clickDelayRaw[3] * 3_600_000L;
        clickDelay += clickDelayRaw[2] * 60_000L;
        clickDelay += clickDelayRaw[1] * 1_000L;
        clickDelay += clickDelayRaw[0];
        // prevent lagging
        if (clickDelay < 1) {
            clickDelay = 1;
        }
        clickDelayOriginal = clickDelay;
        clickDelayArray = clickDelayRaw;
        SaveSettings.saveSettings();
    }

    public void setClickDelay(int element, int index) {
        clickDelayArray[index] = element;
        setClickDelay(clickDelayArray);
    }

    public long getClickDelayOriginal() {
        return clickDelayOriginal;
    }

    public int[] getClickDelayArray() {
        return clickDelayArray;
    }

    public long getHoldDelay() {
        return holdDelay;
    }

    public void setHoldDelay(int[] holdDelayRaw) {
        holdDelay = 0;
        holdDelay += holdDelayRaw[3] * 3_600_000L;
        holdDelay += holdDelayRaw[2] * 60_000L;
        holdDelay += holdDelayRaw[1] * 1_000L;
        holdDelay += holdDelayRaw[0];
        // prevent not registering clicks
        if (holdDelay < 1) {
            holdDelay = 1;
        }
        holdDelayOriginal = holdDelay;
        holdDelayArray = holdDelayRaw;
        SaveSettings.saveSettings();
    }

    public void setHoldDelay(long holdDelay) {
        this.holdDelay = holdDelay;
        SaveSettings.saveSettings();
    }

    public void setHoldDelay(int element, int index) {
        holdDelayArray[index] = element;
        setHoldDelay(holdDelayArray);
    }

    public long getHoldDelayOriginal() {
        return holdDelayOriginal;
    }

    public int[] getHoldDelayArray() {
        return holdDelayArray;
    }

    public boolean shouldRandomizeClick() {
        return shouldRandomizeClick;
    }

    public boolean shouldRandomizeHold() {
        return shouldRandomizeHold;
    }

    public int getHoldRandomizeRange() {
        return holdRandomizeRange;
    }

    public void setHoldRandomizeRange(int holdRandomizeRange) {
        this.holdRandomizeRange = holdRandomizeRange;
        SaveSettings.saveSettings();
    }

    public int getClickRandomizeRange() {
        return clickRandomizeRange;
    }

    public void setClickRandomizeRange(int clickRandomizeRange) {
        this.clickRandomizeRange = clickRandomizeRange;
        SaveSettings.saveSettings();
    }

    public void setRandomizeRange(int element, int index) {
        if (index == 0) {
            setClickRandomizeRange(element);
        } else if (index == 1) {
            setHoldRandomizeRange(element);
        }
    }

    public int getButtonNumber() {
        return buttonNumber;
    }

    public void setButtonNumber(int buttonNumber) {
        this.buttonNumber = buttonNumber;
        int number = buttonNumber + 1;
        if (number == 2) {
            number = 3;
        } else if (number == 3) {
            number = 2;
        }
        this.button = InputEvent.getMaskForButton(number);
        SaveSettings.saveSettings();
    }

    public int getButton() {
        return button;
    }

    public int getHotkey() {
        return hotkey;
    }

    public void setHotkey(int hotkey) {
        this.hotkey = hotkey;
        this.hotkeyText = NativeKeyEvent.getKeyText(hotkey);
        SaveSettings.saveSettings();
    }

    public String getHotkeyText() {
        return hotkeyText;
    }

    public boolean shouldAutoclickOnMouseHold() {
        return autoclickOnMouseHold;
    }

    public void setShouldRandomizeClick(boolean shouldRandomizeClick) {
        this.shouldRandomizeClick = shouldRandomizeClick;
        SaveSettings.saveSettings();
    }

    public void setShouldRandomizeHold(boolean shouldRandomizeHold) {
        this.shouldRandomizeHold = shouldRandomizeHold;
        SaveSettings.saveSettings();
    }

    public void setAutoclickOnMouseHold(boolean autoclickOnMouseHold) {
        this.autoclickOnMouseHold = autoclickOnMouseHold;
        SaveSettings.saveSettings();
    }
}
