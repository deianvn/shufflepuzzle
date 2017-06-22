package net.rizov.shufflepuzzle.room.menu.entity;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import net.rizov.gameutils.scene.RoomEntity;

public class SmallTimer extends net.rizov.shufflepuzzle.entity.Timer {

    public SmallTimer(RoomEntity parent) {
        super(parent);
        TextureAtlas atlas = getRoom().getAsset("menu.pack", TextureAtlas.class);
        TextureRegion[] digits = new TextureRegion[10];

        for (int i = 0; i < digits.length; i++) {
            digits[i] = atlas.findRegion("sdigit" + i);
        }

        setRegions(digits, atlas.findRegion("sdigitnone"), atlas.findRegion("sseparator"));
    }

}
