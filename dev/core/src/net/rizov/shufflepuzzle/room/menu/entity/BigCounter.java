package net.rizov.shufflepuzzle.room.menu.entity;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import net.rizov.gameutils.scene.Room;
import net.rizov.gameutils.scene.RoomEntity;
import net.rizov.shufflepuzzle.entity.Counter;

public class BigCounter extends Counter {

    public BigCounter(Room room) {
        super(room);
        createEntities();
    }

    public BigCounter(RoomEntity parent) {
        super(parent);
        createEntities();
    }

    private void createEntities() {
        TextureAtlas atlas = getRoom().getAsset("menu.pack", TextureAtlas.class);
        TextureRegion[] digits = new TextureRegion[10];

        for (int i = 0; i < digits.length; i++) {
            digits[i] = atlas.findRegion("digit" + i);
        }

        setDigits(digits);
    }

}
