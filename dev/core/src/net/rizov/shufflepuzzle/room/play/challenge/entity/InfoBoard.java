package net.rizov.shufflepuzzle.room.play.challenge.entity;

import com.badlogic.gdx.math.Rectangle;
import net.rizov.gameutils.scene.Room;
import net.rizov.gameutils.scene.RoomEntity;
import net.rizov.gameutils.scene.TextureEntity;
import net.rizov.shufflepuzzle.entity.Counter;
import net.rizov.shufflepuzzle.entity.Timer;
import net.rizov.shufflepuzzle.room.play.entity.BigCounter;
import net.rizov.shufflepuzzle.room.play.entity.BigTimer;

public class InfoBoard extends RoomEntity {

    private float space;

    private TextureEntity timeLeftLabel;

    private TextureEntity puzzlesLeftLabel;

    private TextureEntity movesLabel;

    private Timer timeLeft;

    private Counter puzzlesLeft;

    private Counter counter;

    private Rectangle area = new Rectangle(5, 480, 220, 245);

    public InfoBoard(Room room) {
        super(room);

        space = 10;

        timeLeftLabel = new TextureEntity(room);
        timeLeftLabel.setTextureRegion("time-left-label", "play.pack");

        puzzlesLeftLabel = new TextureEntity(room);
        puzzlesLeftLabel.setTextureRegion("puzzles-left-label", "play.pack");

        movesLabel = new TextureEntity(room);
        movesLabel.setTextureRegion("moves-label", "play.pack");

        timeLeft = new BigTimer(getRoom());
        timeLeft.setNoneTime();
        timeLeft.setSpace(3);

        puzzlesLeft = new BigCounter(getRoom());
        puzzlesLeft.setSpace(3);

        counter = new BigCounter(room);
        counter.setSpace(3);

        computeDimensions();
        positionEntities();
    }

    public void setSpace(float space) {
        this.space = space;
        computeDimensions();
        positionEntities();
    }

    @Override
    public void draw() {
        timeLeftLabel.draw();
        puzzlesLeftLabel.draw();
        movesLabel.draw();
        timeLeft.draw();
        puzzlesLeft.draw();
        counter.draw();
    }

    @Override
    protected void computeDimensions() {
        setWidth(Math.max(timeLeftLabel.getWidth(), Math.max(movesLabel.getWidth(),
                Math.max(timeLeft.getWidth(),
                        counter.getWidth()))));

        setHeight(timeLeftLabel.getHeight() + puzzlesLeftLabel.getHeight() + movesLabel.getHeight() + timeLeft.getHeight() + puzzlesLeft.getHeight() + counter
                .getHeight() + 6 * space);
    }

    @Override
    protected void onMove() {
        positionEntities();
    }

    public void clearTime() {
        setTimeLeft(0l);
    }

    public void setTimeLeft(float seconds) {
        if (seconds < 0) {
            seconds = 0;
        }

        timeLeft.setTime((int) (seconds * 1000));
    }

    public void setPuzzlesLeft(int left) {
        puzzlesLeft.setCount(left);
        centerPuzzlesLeft();
    }

    public void setCount(int count) {
        counter.setCount(count);
        centerCounter();
    }

    private void positionEntities() {
        float y = area.y + area.height - (area.height - getHeight()) / 2 - timeLeftLabel.getHeight();

        timeLeftLabel.setPositionX(area.x + (area.width - timeLeftLabel.getWidth()) / 2);
        timeLeftLabel.setPositionY(y);
        y -= (timeLeft.getHeight() + space);
        timeLeft.setPositionX(area.x + (area.width - timeLeft.getWidth()) / 2);
        timeLeft.setPositionY(y);

        y -= (puzzlesLeftLabel.getHeight() + 2 * space);
        puzzlesLeftLabel.setPositionX(area.x + (area.width - puzzlesLeftLabel.getWidth()) / 2);
        puzzlesLeftLabel.setPositionY(y);
        y -= (puzzlesLeft.getHeight() + space);
        puzzlesLeft.setPositionX(area.x + (area.width - puzzlesLeft.getWidth()) / 2);
        puzzlesLeft.setPositionY(y);

        y -= (movesLabel.getHeight() + 2 * space);
        movesLabel.setPositionX(area.x + (area.width - movesLabel.getWidth()) / 2);
        movesLabel.setPositionY(y);
        y -= (counter.getHeight() + space);
        counter.setPositionX(area.x + (area.width - counter.getWidth()) / 2);
        counter.setPositionY(y);
    }

    private void centerCounter() {
        counter.setPositionX(area.x + (area.width - counter.getWidth()) / 2);
    }

    private void centerPuzzlesLeft() {
        puzzlesLeft.setPositionX(area.x + (area.width - puzzlesLeft.getWidth()) / 2);
    }

}
