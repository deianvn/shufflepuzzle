package net.rizov.shufflepuzzle.room.play.entity;

import com.badlogic.gdx.utils.Array;
import net.rizov.gameutils.scene.Room;
import net.rizov.gameutils.scene.RoomEntity;
import net.rizov.shufflepuzzle.entity.ButtonBounds;

public class Menu extends RoomEntity {

    public enum Area {
        menu, quit
    }

    private ButtonBounds<Area> quitButton;

    private ButtonBounds<Area> menuButton;

    private Array<ButtonBounds> buttons = new Array<ButtonBounds>();

    public Menu(Room<?, ?> room) {
        super(room);
        quitButton = new ButtonBounds<Area>(Area.quit, 0, 730, 225, 70);
        menuButton = new ButtonBounds<Area>(Area.menu, 230, 730, 250, 70);

        buttons.add(quitButton);
        buttons.add(menuButton);
    }

    public Menu(RoomEntity parent) {
        super(parent);
    }

    public Area click(float x, float y) {

        for (ButtonBounds<Area> bb : buttons) {
            Area area = bb.click(x, y);

            if (area != null) {
                return area;
            }
        }

        return null;
    }

    @Override
    protected void computeDimensions() {

    }

    @Override
    public void draw() {

    }
}
