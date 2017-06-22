package net.rizov.shufflepuzzle.room.menu.entity;

import com.badlogic.gdx.utils.Array;
import net.rizov.gameutils.scene.Room;
import net.rizov.gameutils.scene.RoomEntity;
import net.rizov.gameutils.scene.TextureEntity;
import net.rizov.shufflepuzzle.entity.Background;
import net.rizov.shufflepuzzle.entity.ButtonBounds;

public class TimeSelector extends RoomEntity {

    public enum Area {
        seconds10,
        seconds15,
        seconds30,
        minutes1,
        minutes2,
        minutes5,
        minutes10,
        minutes20,
        minutes30,
        back
    }

    private Background background;

    private TextureEntity title;

    private TextureEntity backButton;

    private TextureEntity time;

    private Array<ButtonBounds<Area>> bounds = new Array<ButtonBounds<Area>>();

    public TimeSelector(Room room) {
        super(room);

        background = new Background(room);
        background.setTextureRegion("background", "menu.pack");

        title = new TextureEntity(room);
        title.setTextureRegion("select-time-title", "menu.pack");
        backButton = new TextureEntity(room);
        backButton.setTextureRegion("back-button", "menu.pack");
        time = new TextureEntity(room);
        time.setTextureRegion("time-selector", "menu.pack");

        title.setPositionX(0);
        title.setPositionY(getRoom().getHeight() - title.getHeight());

        backButton.setPositionX(0);
        backButton.setPositionY(0);

        time.setPositionX((getRoom().getWidth() - time.getWidth()) / 2);
        time.setPositionY((getRoom().getHeight() - time.getHeight()) / 2);

        bounds.add(new ButtonBounds<Area>(Area.seconds10, 10, 10, 120, 120));
        bounds.add(new ButtonBounds<Area>(Area.seconds15, 140, 10, 120, 120));
        bounds.add(new ButtonBounds<Area>(Area.seconds30, 270, 10, 120, 120));
        bounds.add(new ButtonBounds<Area>(Area.minutes1, 10, 140, 120, 120));
        bounds.add(new ButtonBounds<Area>(Area.minutes2, 140, 140, 120, 120));
        bounds.add(new ButtonBounds<Area>(Area.minutes5, 270, 140, 120, 120));
        bounds.add(new ButtonBounds<Area>(Area.minutes10, 10, 270, 120, 120));
        bounds.add(new ButtonBounds<Area>(Area.minutes20, 140, 270, 120, 120));
        bounds.add(new ButtonBounds<Area>(Area.minutes30, 270, 270, 120, 120));

        for (ButtonBounds<Area> buttonBounds : bounds) {
            buttonBounds.shift(time.getPositionX(), time.getPositionY());
        }

        bounds.add(new ButtonBounds<Area>(Area.back, 0, 0, 480, 70));
    }

    @Override
    protected void computeDimensions() {

    }

    @Override
    public void draw() {
        background.draw();
        title.draw();
        backButton.draw();
        time.draw();
    }

    public Area click(float x, float y) {

        Area area;

        for (ButtonBounds<Area> buttonBounds : bounds) {
            area = buttonBounds.click(x, y);

            if (area != null) {
                return area;
            }
        }

        return null;
    }

}
