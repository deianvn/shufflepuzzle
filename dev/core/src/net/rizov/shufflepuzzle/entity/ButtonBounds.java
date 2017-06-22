package net.rizov.shufflepuzzle.entity;

import net.rizov.shufflepuzzle.utils.helper.UIHelper;

public class ButtonBounds<Area> extends AreaBounds<Area> {

    public ButtonBounds(Area area, float x, float y, float width, float height) {
        super(area, x, y, width, height);
    }

    @Override
    public Area click(float x, float y) {
        Area area = super.click(x, y);

        if (area != null) {
            UIHelper.click();
        }

        return area;
    }
}
