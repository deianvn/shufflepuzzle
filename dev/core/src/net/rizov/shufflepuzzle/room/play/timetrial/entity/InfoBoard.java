package net.rizov.shufflepuzzle.room.play.timetrial.entity;

import com.badlogic.gdx.math.Rectangle;
import net.rizov.gameutils.scene.RoomEntity;
import net.rizov.gameutils.scene.Room;
import net.rizov.gameutils.scene.TextureEntity;
import net.rizov.shufflepuzzle.entity.Counter;
import net.rizov.shufflepuzzle.entity.Timer;
import net.rizov.shufflepuzzle.room.play.entity.BigCounter;
import net.rizov.shufflepuzzle.room.play.entity.BigTimer;

public class InfoBoard extends RoomEntity {

    private float space;

    private TextureEntity bestTimeLabel;

    private TextureEntity timeLabel;

    private TextureEntity movesLabel;

    private Timer bestTime;

    private Timer timer;

    private Counter counter;

    private Rectangle area = new Rectangle(5, 480, 220, 245);

    public InfoBoard(Room room) {
        super(room);

        space = 10;

        bestTimeLabel = new TextureEntity(room);
        bestTimeLabel.setTextureRegion("besttime-label", "play.pack");

        timeLabel = new TextureEntity(room);
        timeLabel.setTextureRegion("time-label", "play.pack");

        movesLabel = new TextureEntity(room);
        movesLabel.setTextureRegion("moves-label", "play.pack");

        bestTime = new BigTimer(getRoom());
        bestTime.setNoneTime();
        bestTime.setSpace(3);

        timer = new BigTimer(getRoom());
        timer.setSpace(3);

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
        bestTimeLabel.draw();
        timeLabel.draw();
        movesLabel.draw();
        bestTime.draw();
        timer.draw();
        counter.draw();
    }

    @Override
    protected void computeDimensions() {
        setWidth(Math.max(timeLabel.getWidth(), Math.max(movesLabel.getWidth(),
                Math.max(timer.getWidth(),
                        counter.getWidth()))));

        setHeight(bestTimeLabel.getHeight() + timeLabel.getHeight() + movesLabel.getHeight() + bestTime.getHeight() + timer.getHeight() + counter
                .getHeight() + 6 * space);
    }

    @Override
    protected void onMove() {
        positionEntities();
    }

    public void clearTime() {
        setTime(0l);
    }

    public void setBestTime(float time) {
        bestTime.setTime((int) (time * 1000));
    }

    public void setTime(float seconds) {
        timer.setTime((int) (seconds * 1000));
    }

    public void setCount(int count) {
        counter.setCount(count);
        centerCounter();
    }

    private void positionEntities() {
        float y = area.y + area.height - (area.height - getHeight()) / 2 - timeLabel.getHeight();

        bestTimeLabel.setPositionX(area.x + (area.width - bestTimeLabel.getWidth()) / 2);
        bestTimeLabel.setPositionY(y);
        y -= (bestTime.getHeight() + space);
        bestTime.setPositionX(area.x + (area.width - bestTime.getWidth()) / 2);
        bestTime.setPositionY(y);

        y -= (timeLabel.getHeight() + 2 * space);
        timeLabel.setPositionX(area.x + (area.width - timeLabel.getWidth()) / 2);
        timeLabel.setPositionY(y);
        y -= (timer.getHeight() + space);
        timer.setPositionX(area.x + (area.width - timer.getWidth()) / 2);
        timer.setPositionY(y);

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

}
