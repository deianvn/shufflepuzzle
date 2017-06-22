package net.rizov.shufflepuzzle.input;

import net.rizov.gameutils.scene.Room;

public abstract class RoomInputProcessor<R extends Room> {

    private R room;

    public void setRoom(R room) {
        this.room = room;
    }

    public R getRoom() {
        return room;
    }

    public abstract void setAsGdxInputProcessor();

}
