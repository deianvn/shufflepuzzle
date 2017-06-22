package net.rizov.shufflepuzzle.utils;

public class ChallengeDescriptor {

    private int size;

    private int count;

    private int time;

    private boolean passed;

    private boolean locked;

    private int level;

    public ChallengeDescriptor() {
    }

    public ChallengeDescriptor(int size, int count, int time) {
        this(size, count, time, false, true);
    }

    public ChallengeDescriptor(int size, int count, int time, boolean passed, boolean locked) {
        this.size = size;
        this.count = count;
        this.time = time;
        this.passed = passed;
        this.locked = locked;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String createId() {
        return String.format("\\d:\\d:\\d", size, count, time);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
