package net.rizov.shufflepuzzle.utils.save;

public class SingleRecord {

    private String name;

    private int hardness;

    private float time;

    private int moves;

    public SingleRecord(String name, int hardness, float time, int moves) {
        this.name = name;
        this.hardness = hardness;
        this.time = time;
        this.moves = moves;
    }

    public String getName() {
        return name;
    }

    public int getHardness() {
        return hardness;
    }

    public float getTime() {
        return time;
    }

    public int getMoves() {
        return moves;
    }

    @Override
    public String toString() {
        return "single." + name + "." + hardness + "=" + time + ":" + moves;
    }

}
