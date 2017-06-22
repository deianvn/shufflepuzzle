package net.rizov.shufflepuzzle.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import net.rizov.gameutils.scene.Room;
import net.rizov.gameutils.scene.TextureEntity;
import net.rizov.shufflepuzzle.utils.config.ConfigManager;

import java.util.ArrayList;
import java.util.List;

public class ImageButton extends TextureEntity {

    private Rectangle bounds = new Rectangle();

    private List<Runnable> actions = new ArrayList<Runnable>();

    public ImageButton(Room room) {
        super(room);
    }

    public boolean contains(float x, float y) {
        return bounds.contains(x, y);
    }

    public void click() {
        for (Runnable action : actions) {
            action.run();
        }
    }

    public void click(float x, float y) {
        if (contains(x, y)) {
            click();
        }
    }

    public void addAction(Runnable action) {
        actions.add(action);
    }

    @Override
    protected void onMove() {
        super.onMove();
        bounds.setPosition(getPositionX(), getPositionY());
    }

    @Override
    protected void onRegionChanged() {
        super.onRegionChanged();
        bounds.width = getTextureRegion().getRegionWidth();
        bounds.height = getTextureRegion().getRegionHeight();
    }
}
