package net.rizov.shufflepuzzle.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import net.rizov.gameutils.scene.RoomEntity;
import net.rizov.gameutils.scene.Room;

public abstract class Timer extends RoomEntity {

    private int time;

    private float space;

    private TextureRegion[] digits = new TextureRegion[10];

    private TextureRegion noneDigit;

    private TextureRegion separator;

    private Array<TextureRegion> entities = new Array<TextureRegion>();

    private float digitWidth;

    private float digitHeight;

    private float noneDigitWidth;

    private float noneDigitHeight;

    private float noneDigitShiftY;

    private float separatorWidth;

    private float separatorHeight;

    private float separatorShiftY;

    public Timer(Room room) {
        super(room);
    }

    public Timer(RoomEntity parent) {
        super(parent);
    }

    public void setRegions(TextureRegion[] digits, TextureRegion noneDigit, TextureRegion separator) {
        this.digits = digits;

        for (int i = 0; i < digits.length; i++) {
            if (digitWidth < digits[i].getRegionWidth()) {
                digitWidth = digits[i].getRegionWidth();
            }

            if (digitHeight < digits[i].getRegionHeight()) {
                digitHeight = digits[i].getRegionHeight();
            }
        }

        this.noneDigit = noneDigit;
        noneDigitWidth = noneDigit.getRegionWidth();
        noneDigitHeight = noneDigit.getRegionHeight();
        noneDigitShiftY = Math.abs(digitHeight - noneDigitHeight) / 2;

        this.separator = separator;
        separatorWidth = separator.getRegionWidth();
        separatorHeight = separator.getRegionHeight();
        separatorShiftY = Math.abs(digitHeight - separatorHeight) / 3;
        computeDimensions();
    }

    public void setSpace(float space) {
        this.space = space;
        computeDimensions();
    }

    public void setSeparator(TextureRegion separator) {
        this.separator = separator;
    }

    public void addTime(int time) {
        setTime(this.time + time);
    }

    public void setNoneTime() {
        time = 0;
        entities.add(noneDigit);
        entities.add(noneDigit);
        entities.add(separator);
        entities.add(noneDigit);
        entities.add(noneDigit);
        entities.add(separator);
        entities.add(noneDigit);
        entities.add(noneDigit);
    }

    public void setTime(int time) {
        this.time = time;
        int hundredths = (time / 10) % 100;
        int seconds = (time / 1000) % 60;
        int minutes = (time / (1000 * 60)) % 60;

        if (minutes > 99) {
            minutes = 99;
            seconds = 59;
            hundredths = 99;
        }

        entities.clear();

        entities.add(digits[minutes / 10]);
        entities.add(digits[minutes % 10]);
        entities.add(separator);

        entities.add(digits[seconds / 10]);
        entities.add(digits[seconds % 10]);
        entities.add(separator);

        entities.add(digits[hundredths / 10]);
        entities.add(digits[hundredths % 10]);
    }

    public float getTime() {
        return time / 1000f;
    }

    @Override
    protected void computeDimensions() {
        setWidth(6 * digitWidth + 2 * separatorWidth + 7 * space);
        setHeight(Math.max(Math.max(digitHeight, separatorHeight), noneDigitHeight));
    }

    @Override
    public void draw() {
        SpriteBatch sb = getSpriteBatch();
        float x = getPositionX();
        float y = getPositionY();

        for (TextureRegion region : entities) {
            if (region == separator) {
                sb.draw(region, x, y + separatorShiftY);
                x += separatorWidth + space;
            } else {
                sb.draw(region, x, y + (digitHeight - region.getRegionHeight()) / 2);
                x += digitWidth + space;
            }

        }
    }

}
