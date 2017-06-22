package net.rizov.shufflepuzzle.room.play.entity;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import net.rizov.gameutils.scene.Room;
import net.rizov.shufflepuzzle.entity.Counter;

public class BigCounter extends Counter {

    public BigCounter(Room room) {
        super(room);
        TextureAtlas atlas = getRoom().getAsset("play.pack", TextureAtlas.class);
        TextureRegion[] digits = new TextureRegion[10];

        for (int i = 0; i < digits.length; i++) {
            digits[i] = atlas.findRegion("digit" + i);
        }

        setDigits(digits);
    }

}
