package net.rizov.shufflepuzzle.utils.save;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class FileSaveConnector implements SaveConnector {

    private static final String FILE_PATH = ".shufflepuzzle.sav";

    @Override
    public void save(SaveData saveData) {
        try {
            FileOutputStream out = new FileOutputStream(FILE_PATH);
            out.write(saveData.getBytes());
            out.close();
        } catch (IOException e) {
        }
    }

    @Override
    public SaveData load() {
        SaveData saveData = new SaveData();
        Properties props = new Properties();

        try {
            props.load(new FileInputStream(FILE_PATH));
        } catch (IOException e) {
        }

        for (Object key : props.keySet()) {
            if (props.toString().startsWith("single.")) {

            }
        }

        return saveData;
    }
}
