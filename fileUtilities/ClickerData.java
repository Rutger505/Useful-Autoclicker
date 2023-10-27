package fileUtilities;

import settings.Settings;
import java.io.*;

public class ClickerData {

    private static final String FILE_NAME = "settings";
    private static final int TEST_BOOLEAN = 0;
    private static final int TEST_INT = 1;
    public static boolean shouldUseDefaults = false;
    private FileReader reader;

    /**
     * Makes data file if it doesn't exist
     * and gets settings from a file.
     */
    public ClickerData() {
        makeFile();

        getSettings();
    }

    /**
     * Writes the file in the data folder with the settings from settings class.
     */
    public static void writeFile() {
        if (shouldUseDefaults) {
            return;
        }

        try {
            FileWriter writer = new FileWriter(FILE_NAME);
            String[] timeNames = {"ms", "s", "m", "h"};

            for (int i = 0; i < Settings.getClickDelayArray().length; i++) {
                writer.write("clickDelay_" + timeNames[i] + " " + Settings.getClickDelayArray()[i] + "\n");
            }
            for (int i = 0; i < Settings.getHoldDelayArray().length; i++) {
                writer.write("holdTime_" + timeNames[i] + " " + Settings.getHoldDelayArray()[i] + "\n");
            }

            writer.write("shouldRandomize_clickDelay " + Settings.shouldRandomizeClick() + "\n");
            writer.write("shouldRandomize_holdTime " + Settings.shouldRandomizeHold() + "\n");

            writer.write("randomizeRange_clickDelay " + Settings.getClickRandomizeRange() + "\n");
            writer.write("randomizeRange_holdTime " + Settings.getHoldRandomizeRange() + "\n");

            writer.write("hotkeyCode " + Settings.getHotkey() + "\n");
            writer.write("buttonNumber " + Settings.getButtonNumber() + "\n");
            writer.write("clicks " + Settings.getClicks() + "\n");
            writer.write("autoclickOnHold " + Settings.shouldAutoclickOnMouseHold() + "\n");

            writer.close();
        } catch (IOException e) {
            shouldUseDefaults = true;
        }
    }

    /**
     * Gets data from file
     */
    private void getSettings() {
        if (shouldUseDefaults) {
            return;
        }

        try {
            reader = new FileReader(FILE_NAME);
        } catch (FileNotFoundException e) {
            shouldUseDefaults = true;
        }
        int[] temp = new int[Settings.getClickDelayArray().length];
        for (int i = 0; i < Settings.getClickDelayArray().length; i++) {
            temp[i] = Integer.parseInt(processValue(TEST_INT));
        }
        Settings.setClickDelay(temp);

        int[] temp2 = new int[Settings.getHoldDelayArray().length];
        for (int i = 0; i < Settings.getHoldDelayArray().length; i++) {
            temp2[i] = Integer.parseInt(processValue(TEST_INT));
        }
        Settings.setHoldDelay(temp2);

        Settings.setShouldRandomizeClick(Boolean.parseBoolean(processValue(TEST_BOOLEAN)));
        Settings.setShouldRandomizeHold(Boolean.parseBoolean(processValue(TEST_BOOLEAN)));

        Settings.setClickRandomizeRange(Integer.parseInt(processValue(TEST_INT)));
        Settings.setHoldRandomizeRange(Integer.parseInt(processValue(TEST_INT)));

        Settings.setHotkey(Integer.parseInt(processValue(TEST_INT)));

        Settings.setButtonNumber(Integer.parseInt(processValue(TEST_INT)));

        Settings.setClicks(Integer.parseInt(processValue(TEST_INT)));

        Settings.setAutoclickOnMouseHold(Boolean.parseBoolean(processValue(TEST_BOOLEAN)));
    }

    /**
     * Gets value from the config file and checks if it is parsable to desired type.
     *
     * @return String value of line.
     */
    private String processValue(int type) {
        if (shouldUseDefaults) {
            return "0";
        }

        String value = readValue();
        if (type == TEST_BOOLEAN) {
            if (value == null) {
                return "false";
            }
            if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
                return value;
            } else {
                return "false";
            }
        } else if (type == TEST_INT) {
            try {
                Integer.parseInt(value);
                return value;
            } catch (NumberFormatException e) {
                return "0";
            }
        }
        throw new IllegalArgumentException("Type must be either TEST_BOOLEAN or TEST_INT.");
    }

    /**
     * Reads one line from file
     *
     * @return String value of line.
     */
    private String readValue() {
        if (shouldUseDefaults) {
            return null;
        }

        try {
            StringBuilder value = new StringBuilder();
            int charNum = 0;
            boolean shouldSave = false;
            while ((char) charNum != '\n' && charNum != -1) {
                if (shouldSave) {
                    value.append((char) charNum);
                }
                if ((char) charNum == ' ') {
                    shouldSave = true;
                }
                charNum = reader.read();
            }
            return value.toString();

        } catch (IOException e) {
            writeFile();
            shouldUseDefaults = true;
            return null;
        }
    }

    /*
     * Makes file
     * @param filePath path of file to be made
     */
    private void makeFile() {
        if (shouldUseDefaults) {
            return;
        }

        File file = new File(FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
                writeFile();
            } catch (IOException e) {
                shouldUseDefaults = true;
            }
        }
    }
}