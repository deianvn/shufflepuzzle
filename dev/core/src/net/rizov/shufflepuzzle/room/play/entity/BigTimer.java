package net.rizov.shufflepuzzle.room.play.entity;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import net.rizov.gameutils.scene.Room;
import net.rizov.gameutils.scene.RoomEntity;
import net.rizov.shufflepuzzle.entity.Timer;

public class BigTimer extends Timer {

    public BigTimer(Room room) {
        super(room);
        setupTimer();
    }

    public BigTimer(RoomEntity parent) {
        super(parent);
        setupTimer();
    }

    private void setupTimer() {
        TextureAtlas atlas = getRoom().getAsset("play.pack", TextureAtlas.class);
        TextureRegion[] digits = new TextureRegion[10];

        for (int i = 0; i < digits.length; i++) {
            digits[i] = atlas.findRegion("digit" + i);
        }

        setRegions(digits, atlas.findRegion("digitnone"), atlas.findRegion("separator"));
    }

}
