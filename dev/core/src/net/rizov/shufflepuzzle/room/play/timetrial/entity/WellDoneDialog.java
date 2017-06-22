package net.rizov.shufflepuzzle.room.play.timetrial.entity;

import com.badlogic.gdx.math.Rectangle;
import net.rizov.gameutils.scene.Room;
import net.rizov.gameutils.scene.TextureEntity;
import net.rizov.shufflepuzzle.entity.Dialog;
import net.rizov.shufflepuzzle.entity.Timer;
import net.rizov.shufflepuzzle.room.play.entity.BigTimer;

public class WellDoneDialog extends Dialog<WellDoneDialog.Action> {

    public enum Action {next, restart, quit}

    private TextureEntity image;

    private Timer timer;

    private TextureEntity yourTime;

    private TextureEntity bestTime;

    private TextureEntity timeLabel;

    private boolean showTime;

    private Rectangle timeBounds = new Rectangle(31, 94, 336, 85);

    public WellDoneDialog(Room room) {
        super(room);
        image = new TextureEntity(this);

        timer = new BigTimer(this);
        timer.setSpace(3);

        yourTime = new TextureEntity(this);
        yourTime.setTextureRegion("your-time-label", "play.pack");

        bestTime = new TextureEntity(this);
        bestTime.setTextureRegion("new-best-time-label", "play.pack");

        setBackground("welldone-single-dialog", "play.pack");
        clearActionBounds();
        addActionBounds(WellDoneDialog.Action.next, new Rectangle(23, 30, 95, 67));
        addActionBounds(WellDoneDialog.Action.restart, new Rectangle(129, 30, 148, 67));
        addActionBounds(WellDoneDialog.Action.quit, new Rectangle(282, 30, 94, 67));
    }

    public void setImage(String textureName) {
        image.setTextureRegion(textureName);
        image.setScaleX((float) 154 / image.getTextureRegion()
                .getRegionWidth());
        image.setScaleY((float) 154 / image.getTextureRegion()
                .getRegionHeight());
        positionImage();
    }

    public void setTime(float bestTime, float yourTime) {
        timer.setTime((int) (yourTime * 1000));

        if (bestTime <= yourTime) {
            timeLabel = this.yourTime;
        } else {
            timeLabel = this.bestTime;
        }

        showTime = true;
        positionTimeEntities();
    }

    public void clearTime() {
        showTime = false;
        timeLabel = null;
        timer.setNoneTime();
    }

    @Override
    public void draw() {
        super.draw();

        if (!isDelayed()) {
            image.draw();

            if (showTime) {
                timeLabel.draw();
                timer.draw();
            }
        }
    }

    @Override
    protected void onMove() {
        positionImage();
    }

    private void positionImage() {
        if (image != null) {
            image.setPositionX(123);
            image.setPositionY(199);
        }
    }

    private void positionTimeEntities() {
        float space = 5;
        float y = timeBounds.y + (timeBounds.height - (timer.getHeight() + space + timeLabel.getHeight())) / 2;
        timer.setPositionX(timeBounds.x + (timeBounds.width - timer.getWidth()) / 2);
        timer.setPositionY(y);
        y += timer.getHeight() + space;
        timeLabel.setPositionX(timeBounds.x + (timeBounds.width - timeLabel.getWidth()) / 2);
        timeLabel.setPositionY(y);
    }

}
