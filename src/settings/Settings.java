package settings;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import utils.Platform;

import java.awt.event.InputEvent;
import java.util.Properties;

public class Settings {
    // index order of the delay arrays, matching the text fields in the GUI
    private static final String[] TIME_UNITS = {"milliseconds", "seconds", "minutes", "hours"};
    // UI order is left, right, middle, side front, side back
    private static final int[] AWT_BUTTONS = {1, 3, 2, 4, 5};

    private static final Settings INSTANCE = new Settings();

    // Autoclicker
    private int clicks = 0;

    private long clickDelay = 100;
    private final int[] clickDelayArray = {100, 0, 0, 0};

    private long holdDelay = 10;
    private final int[] holdDelayArray = {10, 0, 0, 0};

    private boolean shouldRandomizeClick = false;
    private boolean shouldRandomizeHold = false;

    private int clickRandomizeRange = 20;

    private int holdRandomizeRange = 20;

    private int buttonNumber = 0;
    private int button = toButtonMask(buttonNumber);

    // inputListener
    private int hotkey = NativeKeyEvent.VC_F1;
    private String hotkeyText = NativeKeyEvent.getKeyText(hotkey);
    private boolean autoclickOnMouseHold = false;

    public static Settings getInstance() {
        return INSTANCE;
    }

    void load(Properties properties) {
        clicks = readInt(properties, "clicks", clicks);
        setClickDelayFields(readDelay(properties, "clickInterval", clickDelayArray));
        setHoldDelayFields(readDelay(properties, "holdTime", holdDelayArray));
        shouldRandomizeClick = readBoolean(properties, "clickInterval.randomize", shouldRandomizeClick);
        clickRandomizeRange = readInt(properties, "clickInterval.randomizeRange", clickRandomizeRange);
        shouldRandomizeHold = readBoolean(properties, "holdTime.randomize", shouldRandomizeHold);
        holdRandomizeRange = readInt(properties, "holdTime.randomizeRange", holdRandomizeRange);
        setButtonNumberFields(Math.min(readInt(properties, "mouseButton", buttonNumber), AWT_BUTTONS.length - 1));
        setHotkeyFields(readInt(properties, "hotkey", hotkey));
        autoclickOnMouseHold = readBoolean(properties, "autoclickOnMouseHold", autoclickOnMouseHold);
    }

    Properties toProperties() {
        Properties properties = new Properties();
        properties.setProperty("clicks", String.valueOf(clicks));
        writeDelay(properties, "clickInterval", clickDelayArray);
        writeDelay(properties, "holdTime", holdDelayArray);
        properties.setProperty("clickInterval.randomize", String.valueOf(shouldRandomizeClick));
        properties.setProperty("clickInterval.randomizeRange", String.valueOf(clickRandomizeRange));
        properties.setProperty("holdTime.randomize", String.valueOf(shouldRandomizeHold));
        properties.setProperty("holdTime.randomizeRange", String.valueOf(holdRandomizeRange));
        properties.setProperty("mouseButton", String.valueOf(buttonNumber));
        properties.setProperty("hotkey", String.valueOf(hotkey));
        properties.setProperty("autoclickOnMouseHold", String.valueOf(autoclickOnMouseHold));
        return properties;
    }

    private static int readInt(Properties properties, String key, int fallback) {
        try {
            return Math.max(0, Integer.parseInt(properties.getProperty(key, String.valueOf(fallback)).trim()));
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    private static boolean readBoolean(Properties properties, String key, boolean fallback) {
        return Boolean.parseBoolean(properties.getProperty(key, String.valueOf(fallback)).trim());
    }

    private static int[] readDelay(Properties properties, String prefix, int[] fallback) {
        int[] delay = new int[TIME_UNITS.length];
        for (int i = 0; i < TIME_UNITS.length; i++) {
            delay[i] = readInt(properties, prefix + "." + TIME_UNITS[i], fallback[i]);
        }
        return delay;
    }

    private static void writeDelay(Properties properties, String prefix, int[] delay) {
        for (int i = 0; i < TIME_UNITS.length; i++) {
            properties.setProperty(prefix + "." + TIME_UNITS[i], String.valueOf(delay[i]));
        }
    }

    private static long toMilliseconds(int[] delay) {
        return delay[3] * 3_600_000L + delay[2] * 60_000L + delay[1] * 1_000L + delay[0];
    }

    private static int toButtonMask(int buttonNumber) {
        int button = AWT_BUTTONS[buttonNumber];
        // X11 reserves buttons 4-7 for scrolling and AWT's Robot shifts extra buttons past them,
        // so the side buttons (X11 buttons 8 and 9) are AWT buttons 6 and 7
        if (Platform.isUnix() && button > 3) {
            button += 2;
        }
        return InputEvent.getMaskForButton(button);
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

    public void setClickDelay(int[] clickDelayRaw) {
        setClickDelayFields(clickDelayRaw);
        SaveSettings.saveSettings();
    }

    public void setClickDelay(int element, int index) {
        clickDelayArray[index] = element;
        setClickDelay(clickDelayArray);
    }

    private void setClickDelayFields(int[] clickDelayRaw) {
        System.arraycopy(clickDelayRaw, 0, clickDelayArray, 0, clickDelayArray.length);
        // prevent lagging
        clickDelay = Math.max(1, toMilliseconds(clickDelayArray));
    }

    public int[] getClickDelayArray() {
        return clickDelayArray;
    }

    public long getHoldDelay() {
        return holdDelay;
    }

    public void setHoldDelay(int[] holdDelayRaw) {
        setHoldDelayFields(holdDelayRaw);
        SaveSettings.saveSettings();
    }

    public void setHoldDelay(int element, int index) {
        holdDelayArray[index] = element;
        setHoldDelay(holdDelayArray);
    }

    private void setHoldDelayFields(int[] holdDelayRaw) {
        System.arraycopy(holdDelayRaw, 0, holdDelayArray, 0, holdDelayArray.length);
        // prevent not registering clicks
        holdDelay = Math.max(1, toMilliseconds(holdDelayArray));
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
        setButtonNumberFields(buttonNumber);
        SaveSettings.saveSettings();
    }

    private void setButtonNumberFields(int buttonNumber) {
        this.buttonNumber = buttonNumber;
        this.button = toButtonMask(buttonNumber);
    }

    public int getButton() {
        return button;
    }

    public int getHotkey() {
        return hotkey;
    }

    public void setHotkey(int hotkey) {
        setHotkeyFields(hotkey);
        SaveSettings.saveSettings();
    }

    private void setHotkeyFields(int hotkey) {
        this.hotkey = hotkey;
        this.hotkeyText = NativeKeyEvent.getKeyText(hotkey);
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
