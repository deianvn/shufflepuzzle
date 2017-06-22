package net.rizov.shufflepuzzle.room.menu.entity;

import com.badlogic.gdx.utils.Array;
import net.rizov.gameutils.scene.Room;
import net.rizov.gameutils.scene.RoomEntity;
import net.rizov.gameutils.scene.TextureEntity;
import net.rizov.shufflepuzzle.entity.Background;
import net.rizov.shufflepuzzle.entity.ButtonBounds;

public class PuzzlesCountSelector extends RoomEntity {

    public enum Area {
        count1,
        count2,
        count3,
        count5,
        count10,
        count15,
        count20,
        count25,
        count30,
        back
    }

    private Background background;

    private TextureEntity title;

    private TextureEntity backButton;

    private TextureEntity count;

    private Array<ButtonBounds<Area>> bounds = new Array<ButtonBounds<Area>>();

    public PuzzlesCountSelector(Room room) {
        super(room);

        background = new Background(room);
        background.setTextureRegion("background", "menu.pack");

        title = new TextureEntity(room);
        title.setTextureRegion("select-puzzles-count-title", "menu.pack");
        backButton = new TextureEntity(room);
        backButton.setTextureRegion("back-button", "menu.pack");
        count = new TextureEntity(room);
        count.setTextureRegion("puzzles-count-selector", "menu.pack");

        title.setPositionX(0);
        title.setPositionY(getRoom().getHeight() - title.getHeight());

        backButton.setPositionX(0);
        backButton.setPositionY(0);

        count.setPositionX((getRoom().getWidth() - count.getWidth()) / 2);
        count.setPositionY((getRoom().getHeight() - count.getHeight()) / 2);

        bounds.add(new ButtonBounds<Area>(Area.count30, 270, 10, 120, 120));
        bounds.add(new ButtonBounds<Area>(Area.count25, 140, 10, 120, 120));
        bounds.add(new ButtonBounds<Area>(Area.count20, 10, 10, 120, 120));
        bounds.add(new ButtonBounds<Area>(Area.count15, 270, 140, 120, 120));
        bounds.add(new ButtonBounds<Area>(Area.count10, 140, 140, 120, 120));
        bounds.add(new ButtonBounds<Area>(Area.count5, 10, 140, 120, 120));
        bounds.add(new ButtonBounds<Area>(Area.count1, 10, 270, 120, 120));
        bounds.add(new ButtonBounds<Area>(Area.count2, 140, 270, 120, 120));
        bounds.add(new ButtonBounds<Area>(Area.count3, 270, 270, 120, 120));

        for (ButtonBounds<Area> buttonBounds : bounds) {
            buttonBounds.shift(count.getPositionX(), count.getPositionY());
        }

        bounds.add(new ButtonBounds<Area>(Area.back, 0, 0, 480, 70));
    }

    @Override
    protected void computeDimensions() {

    }

    @Override
    public void draw() {
        background.draw();
        count.draw();
        backButton.draw();
        title.draw();
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
