package net.rizov.shufflepuzzle.utils.save;

public interface SaveConnector {

    void save(SaveData saveData);

    SaveData load();

}
