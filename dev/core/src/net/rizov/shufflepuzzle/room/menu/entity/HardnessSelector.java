package net.rizov.shufflepuzzle.room.menu.entity;

import com.badlogic.gdx.utils.Array;
import net.rizov.gameutils.scene.RoomEntity;
import net.rizov.gameutils.scene.Room;
import net.rizov.gameutils.scene.TextureEntity;
import net.rizov.shufflepuzzle.entity.Background;
import net.rizov.shufflepuzzle.entity.ButtonBounds;

public class HardnessSelector extends RoomEntity {

    public enum Area {
        three,
        four,
        five,
        six,
        back
    }

    private Background background;

    private TextureEntity title;

    private TextureEntity backButton;

    private TextureEntity difficulty;

    private Array<ButtonBounds<Area>> bounds = new Array<ButtonBounds<Area>>();

    private ButtonBounds<Area> threeBounds = new ButtonBounds<Area>(Area.three, 20, 210, 170, 170);

    private ButtonBounds<Area> fourBounds = new ButtonBounds<Area>(Area.four, 210, 210, 170, 170);

    private ButtonBounds<Area> fiveBounds = new ButtonBounds<Area>(Area.five, 20, 20, 170, 170);

    private ButtonBounds<Area> sixBounds = new ButtonBounds<Area>(Area.six, 210, 20, 170, 170);

    private ButtonBounds<Area> backButtonBounds = new ButtonBounds<Area>(Area.back, 0, 0, 480, 70);

    public HardnessSelector(Room room) {
        super(room);

        background = new Background(room);
        background.setTextureRegion("background", "menu.pack");

        title = new TextureEntity(room);
        title.setTextureRegion("select-difficulty-title", "menu.pack");
        backButton = new TextureEntity(room);
        backButton.setTextureRegion("back-button", "menu.pack");
        difficulty = new TextureEntity(room);
        difficulty.setTextureRegion("difficulty-selector", "menu.pack");

        title.setPositionX(0);
        title.setPositionY(getRoom().getHeight() - title.getHeight());

        backButton.setPositionX(0);
        backButton.setPositionY(0);

        difficulty.setPositionX((getRoom().getWidth() - difficulty.getWidth()) / 2);
        difficulty.setPositionY((getRoom().getHeight() - difficulty.getHeight()) / 2);

        bounds.add(threeBounds);
        bounds.add(fourBounds);
        bounds.add(fiveBounds);
        bounds.add(sixBounds);

        for (ButtonBounds<Area> buttonBounds : bounds) {
            buttonBounds.shift(difficulty.getPositionX(), difficulty.getPositionY());
        }

        bounds.add(backButtonBounds);
    }

    @Override
    protected void computeDimensions() {

    }

    @Override
    public void draw() {
        background.draw();
        title.draw();
        backButton.draw();
        difficulty.draw();
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
