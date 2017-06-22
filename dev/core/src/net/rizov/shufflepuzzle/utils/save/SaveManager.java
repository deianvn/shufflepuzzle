package net.rizov.shufflepuzzle.utils.save;

import net.rizov.gameutils.scene.Game;

public class SaveManager {

    private static SaveConnector connector;

    private static SaveData saveData;

    public static void create(Game game) {
        connector = game.inject(SaveConnector.class);
    }

    public static void load() {
        saveData = connector.load();
    }

    public static void save() {
        if (saveData != null) {
            connector.save(saveData);
        }
    }

    public static SingleRecord getSingleRecord(String name, int hardness) {
        return saveData.getSingleRecord(name + "." + hardness);
    }

    public static void setSingleRecord(SingleRecord record) {
        saveData.updateSingleRecord(record);
    }

    public static int getArcadeLevel() {
        return saveData.getArcadeLevel();
    }

    public static void setArcadeLevel(int arcadeLevel) {
        saveData.updateArcadeLevel(arcadeLevel);
    }

}
