package net.rizov.shufflepuzzle.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import net.rizov.gameutils.scene.PositionedEntity;
import net.rizov.gameutils.scene.Room;
import net.rizov.gameutils.scene.RoomEntity;
import net.rizov.gameutils.scene.TextureEntity;

public abstract class Dialog<A> extends ActionPane<A> {

    private TextureEntity background;

    private float delay;

    public Dialog(Room room) {
        super(room);
        background = new TextureEntity(this);
    }

    public Dialog(RoomEntity parent) {
        super(parent);
        background = new TextureEntity(this);
    }

    public void setDelay(float delay) {
        this.delay = delay;
    }

    public void setBackground(String regionName, String atlasName) {
        background.setTextureRegion(regionName, atlasName);
        onBackgroundChanged();
    }

    public void setBackground(String textureName) {
        background.setTextureRegion(textureName);
        onBackgroundChanged();
    }

    public void setBackground(Texture texture) {
        background.setTextureRegion(texture);
        onBackgroundChanged();
    }

    public void setBackground(TextureRegion textureRegion) {
        background.setTextureRegion(textureRegion);
        onBackgroundChanged();
    }

    @Override
    public void draw() {
        if (!isDelayed()) {
            background.draw();
        }
    }

    @Override
    public void update(float deltaTime) {
        delay -= deltaTime;
    }

    @Override
    public void click() {
        if (!isDelayed()) {
            super.click();
        }
    }

    public boolean isDelayed() {
        return delay > 0;
    }

    @Override
    protected void computeDimensions() {
        setWidth(background.getWidth());
        setHeight(background.getHeight());
    }

    protected void onBackgroundChanged() {
        computeDimensions();
        center();
    }

    protected void center() {
        PositionedEntity parent = getParent();
        setPositionX((parent.getWidth() - getWidth()) / 2);
        setPositionY((parent.getHeight() - getHeight()) / 2);
    }

}
