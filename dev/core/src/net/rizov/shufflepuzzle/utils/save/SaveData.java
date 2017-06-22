package net.rizov.shufflepuzzle.utils.save;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class SaveData {

    private static final String ARCADE_LEVEL_KEY = "arcade.level";

    private Map<String, SingleRecord> singleSave = new HashMap<String, SingleRecord>();

    private int arcadeLevel;

    public byte[] getBytes() {
        StringBuilder sb = new StringBuilder();

        for (String key : singleSave.keySet()) {
            sb.append(singleSave.get(key) + "\n");
        }

        if (arcadeLevel > 0) {
            sb.append(ARCADE_LEVEL_KEY + "=" + arcadeLevel + "\n");
        }

        return sb.toString().getBytes();
    }

    public void update(byte[] bytes) {
        String data = new String(bytes);
        BufferedReader in = new BufferedReader(new StringReader(data));
        String line;

        try {
            while ((line = in.readLine()) != null) {
                if (line.startsWith("single.")) {
                    parseSingle(line);
                } else if (line.startsWith(ARCADE_LEVEL_KEY)) {
                    parseArcadeLevel(line);
                }
            }
        } catch (IOException e) {

        } finally {
            try {
                in.close();
            } catch (IOException e) {
            }
        }
    }

    public void updateSingleRecord(SingleRecord record) {
        singleSave.put(record.getName() + "." + record.getHardness(), record);
    }

    public SingleRecord getSingleRecord(String key) {
        return singleSave.get(key);
    }

    public void updateArcadeLevel(int arcadeLevel) {
        this.arcadeLevel = arcadeLevel;
    }

    public int getArcadeLevel() {
        return arcadeLevel;
    }

    private void parseSingle(String line) {
        try {
            String key = line.substring(line.indexOf('.') + 1, line.indexOf('='));
            String[] splitKey = key.split("\\.");
            float time = Float.parseFloat(line.substring(line.indexOf('=') + 1, line.indexOf(':')));
            int moves = Integer.parseInt(line.substring(line.indexOf(':') + 1));
            updateSingleRecord(new SingleRecord(splitKey[0], Integer.parseInt(splitKey[1]), time, moves));
        } catch (NullPointerException e) {
        }
    }

    private void parseArcadeLevel(String line) {
        try {
            arcadeLevel = Integer.parseInt(line.substring(line.indexOf('=') + 1));
        } catch (IndexOutOfBoundsException e) {
        } catch (NullPointerException e) {
        }
    }

}
