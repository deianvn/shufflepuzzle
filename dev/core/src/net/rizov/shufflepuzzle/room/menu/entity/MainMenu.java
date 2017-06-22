package net.rizov.shufflepuzzle.room.menu.entity;

import com.badlogic.gdx.utils.Array;
import net.rizov.gameutils.scene.RoomEntity;
import net.rizov.gameutils.scene.Room;
import net.rizov.gameutils.scene.TextureEntity;
import net.rizov.shufflepuzzle.room.menu.MenuRoom;
import net.rizov.shufflepuzzle.entity.ButtonBounds;
import net.rizov.shufflepuzzle.utils.config.ConfigManager;

public class MainMenu extends RoomEntity {

    public enum Area {
        timeTrial,
        challenge,
        arcade,
        morePuzzles,
        rate,
        vibrate,
        sound,
        exit
    }

    private TextureEntity background;

    private TextureEntity soundTick;

    private TextureEntity vibrateTick;

    private Array<ButtonBounds<Area>> bounds = new Array<ButtonBounds<Area>>();

    private ButtonBounds<Area> timeTrialBounds = new ButtonBounds<Area>(Area.timeTrial, 80, 369, 320, 65);

    private ButtonBounds<Area> challengeBounds = new ButtonBounds<Area>(Area.challenge, 80, 292, 320, 65);

    private ButtonBounds<Area> arcadeBounds = new ButtonBounds<Area>(Area.arcade, 80, 214, 320, 65);

    private ButtonBounds<Area> morePuzzlesBounds = new ButtonBounds<Area>(Area.morePuzzles, 80, 141, 155, 60);

    private ButtonBounds<Area> rateBounds = new ButtonBounds<Area>(Area.rate, 244, 141, 155, 60);

    private ButtonBounds<Area> vibrateBounds = new ButtonBounds<Area>(Area.vibrate, 420, 76, 40, 40);

    private ButtonBounds<Area> soundBounds = new ButtonBounds<Area>(Area.sound, 420, 30, 40, 40);

    private ButtonBounds<Area> exitBounds = new ButtonBounds<Area>(Area.exit, 44, 31, 132, 85);

    public MainMenu(Room<MenuRoom.MenuRoomStatus, MenuRoom.MenuRoomEvent> room) {
        super(room);
        background = new TextureEntity(room);
        background.setTextureRegion("main-menu-page", "menu.pack");

        soundTick = new TextureEntity(room);
        soundTick.setTextureRegion("tick", "menu.pack");
        soundTick.setPositionX(soundBounds.bounds.x + (soundBounds.bounds.width - soundTick.getWidth()) / 2);
        soundTick.setPositionY(soundBounds.bounds.y + (soundBounds.bounds.height - soundTick.getHeight()) / 2);

        vibrateTick = new TextureEntity(room);
        vibrateTick.setTextureRegion("tick", "menu.pack");
        vibrateTick.setPositionX(vibrateBounds.bounds.x + (vibrateBounds.bounds.width - vibrateTick.getWidth()) / 2);
        vibrateTick.setPositionY(vibrateBounds.bounds.y + (vibrateBounds.bounds.height - vibrateTick.getHeight()) / 2);

        bounds.add(timeTrialBounds);
        bounds.add(challengeBounds);
        bounds.add(arcadeBounds);
        bounds.add(morePuzzlesBounds);
        bounds.add(rateBounds);
        bounds.add(vibrateBounds);
        bounds.add(soundBounds);
        bounds.add(exitBounds);
    }

    @Override
    protected void computeDimensions() {

    }

    @Override
    public void draw() {
        background.draw();

        if (ConfigManager.isSoundEnabled()) {
            soundTick.draw();
        }

        if (ConfigManager.isVibrationEnabled()) {
            vibrateTick.draw();
        }

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
