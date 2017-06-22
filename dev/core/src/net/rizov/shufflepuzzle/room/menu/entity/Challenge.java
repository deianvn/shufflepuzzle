package net.rizov.shufflepuzzle.room.menu.entity;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import net.rizov.gameutils.scene.Room;
import net.rizov.gameutils.scene.RoomEntity;
import net.rizov.gameutils.scene.TextureEntity;
import net.rizov.shufflepuzzle.utils.ChallengeDescriptor;
import net.rizov.shufflepuzzle.utils.helper.UIHelper;

public class Challenge extends RoomEntity {

    private enum TimeType {seconds, minutes}

    private static final Rectangle countBounds = new Rectangle(140, 65, 255, 38);

    private static final Rectangle timeBounds = new Rectangle(90, 12, 235, 38);

    private static final float space = 5;

    private static final float sizePadding = 10;

    private static final float iconPadding = 10;

    private ChallengeDescriptor descriptor;

    private TextureEntity button;

    private TextureEntity size;

    private BigCounter count;

    private BigCounter time;

    private TextureEntity puzzleLabel;

    private TextureEntity puzzlesLabel;

    private TextureEntity secondsLabel;

    private TextureEntity minutesLabel;

    private TextureEntity lockedIcon;

    private TextureEntity passedIcon;

    private TimeType timeType;

    private Array<RoomEntity> entities = new Array<RoomEntity>();

    private Runnable callback;

    public Challenge(Room<?, ?> room) {
        super(room);
        createEntities();
    }

    public Challenge(RoomEntity parent) {
        super(parent);
        createEntities();
    }

    public void setDescriptor(ChallengeDescriptor descriptor) {

        entities.clear();

        this.descriptor = descriptor;
        entities.add(button);

        if (descriptor.isLocked()) {
            entities.add(lockedIcon);
            positionIcon(lockedIcon, button.getBounds());
            return;
        } else if (descriptor.isPassed()) {
            entities.add(passedIcon);
            positionIcon(passedIcon);
        }

        int sizeValue = descriptor.getSize();
        size.setTextureRegion("size-" + sizeValue + "x" + sizeValue, "menu.pack");
        entities.add(size);
        positionSize();

        int countValue = descriptor.getCount();
        count.setCount(descriptor.getCount());
        entities.add(count);

        if (countValue > 1) {
            entities.add(puzzlesLabel);
            positionCount(puzzlesLabel);
        } else {
            entities.add(puzzleLabel);
            positionCount(puzzleLabel);
        }

        int timeValue = descriptor.getTime();
        timeType = getTimeType(timeValue);
        time.setCount(getTimeValue(timeValue, timeType));
        entities.add(time);

        if (timeType == TimeType.minutes) {
            entities.add(minutesLabel);
            positionTime(minutesLabel);
        } else {
            entities.add(secondsLabel);
            positionTime(secondsLabel);
        }

    }

    public boolean contains(float x, float y) {
        return getBounds().contains(x, y);
    }

    public ChallengeDescriptor getDescriptor() {
        return descriptor;
    }

    @Override
    protected void computeDimensions() {
        setWidth(button.getWidth());
        setHeight(button.getHeight());
    }

    @Override
    public void draw() {
        for (RoomEntity entity : entities) {
            entity.draw();
        }
    }

    public void setCallback(Runnable callback) {
        this.callback = callback;
    }

    public Runnable getCallback() {
        return callback;
    }

    public void click(float x, float y) {

        if (getPositionX() <= x && getPositionX() + getWidth() >= x && getPositionY() <= y && getPositionY() + getHeight() >= y) {
            UIHelper.click();

            if (callback != null) {
                callback.run();
            }
        }

    }

    private void createEntities() {
        Room<?, ?> room = getRoom();
        TextureAtlas atlas = room.getAsset("menu.pack", TextureAtlas.class);

        button = new TextureEntity(this);
        size = new TextureEntity(this);
        count = new BigCounter(this);
        time = new BigCounter(this);
        puzzleLabel = new TextureEntity(this);
        puzzlesLabel = new TextureEntity(this);
        secondsLabel = new TextureEntity(this);
        minutesLabel = new TextureEntity(this);
        lockedIcon = new TextureEntity(this);
        passedIcon = new TextureEntity(this);

        button.setTextureRegion(atlas.findRegion("challenge-button"));
        puzzleLabel.setTextureRegion(atlas.findRegion("puzzle-label"));
        puzzlesLabel.setTextureRegion(atlas.findRegion("puzzles-label"));
        secondsLabel.setTextureRegion(atlas.findRegion("seconds-label"));
        minutesLabel.setTextureRegion(atlas.findRegion("minutes-label"));
        lockedIcon.setTextureRegion(atlas.findRegion("locked-icon"));
        passedIcon.setTextureRegion(atlas.findRegion("passed-icon"));

        computeDimensions();
    }

    private TimeType getTimeType(int time) {
        if (time > 60 && time % 60 == 0) {
            return TimeType.minutes;
        }

        return TimeType.seconds;
    }

    private int getTimeValue(int time, TimeType type) {
        if (type == TimeType.minutes) {
            return time / 60;
        } else {
            return time;
        }
    }

    private void positionSize() {
        size.setPositionX(sizePadding);
        size.setPositionY(button.getHeight() - sizePadding - size.getHeight());
    }

    private void positionIcon(TextureEntity icon) {
        icon.setPositionX(button.getWidth() - iconPadding - icon.getWidth());
        icon.setPositionY(iconPadding);
    }

    private void positionIcon(TextureEntity icon, Rectangle bounds) {
        icon.setPositionX((bounds.getWidth() - icon.getWidth()) / 2);
        icon.setPositionY((bounds.getHeight() - icon.getHeight()) / 2);
    }

    private void positionTime(TextureEntity label) {
        positionToBounds(time, label, timeBounds);
    }

    private void positionCount(TextureEntity label) {
        positionToBounds(count, label, countBounds);
    }

    private void positionToBounds(RoomEntity a, RoomEntity b, Rectangle bounds) {
        float width = a.getWidth() + space + b.getWidth();
        float height = Math.max(a.getHeight(), b.getHeight());
        float x = bounds.x + (bounds.width - width) / 2;
        float y = bounds.y + (bounds.height - height) / 2;

        a.setPositionX(x);
        a.setPositionY(y);
        b.setPositionX(x + space + a.getWidth());
        b.setPositionY(y);
    }

}
