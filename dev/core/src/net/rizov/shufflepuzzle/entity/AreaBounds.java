package net.rizov.shufflepuzzle.entity;

import com.badlogic.gdx.math.Rectangle;
import net.rizov.shufflepuzzle.utils.helper.UIHelper;

public class AreaBounds<Area> {

    private Area area;

    public Rectangle bounds;

    public AreaBounds(Area area, float x, float y, float width, float height) {
        this.area = area;
        bounds = new Rectangle(x, y, width, height);
    }

    public Area click(float x, float y) {
        if (bounds.contains(x, y)) {
            return area;
        }

        return null;
    }

    public void shift(float x, float y) {
        bounds.x += x;
        bounds.y += y;
    }

}
