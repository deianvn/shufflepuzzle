package net.rizov.shufflepuzzle.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import net.rizov.gameutils.scene.Room;
import net.rizov.gameutils.scene.RoomEntity;

public class Background extends RoomEntity {

    private TextureRegion textureRegion;

    private int repeatX;

    private int repeatY;

    private int width;

    private int height;

    public Background(Room<?, ?> room) {
        super(room);
    }

    public void setTextureRegion(String regionName, String atlasName) {
        setTextureRegion(getRoom().getAsset(atlasName, TextureAtlas.class).findRegion(regionName));
    }

    public void setTextureRegion(String textureName) {
        Texture texture = getRoom().getAsset(textureName, Texture.class);
        setTextureRegion(new TextureRegion(texture, texture.getWidth(), texture.getHeight()));
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
        width = textureRegion.getRegionWidth();
        height = textureRegion.getRegionHeight();
        repeatX = (int) Math.ceil(getRoom().getWidth() / width);
        repeatY = (int) Math.ceil(getRoom().getHeight() / height);
    }

    public void setTextureRegion(Texture texture) {
        setTextureRegion(new TextureRegion(texture, texture.getWidth(), texture.getHeight()));
    }

    @Override
    public void draw() {
        if (textureRegion == null) {
            return;
        }

        SpriteBatch sb = getSpriteBatch();
        sb.disableBlending();

        for (int i = 0; i < repeatX; i++) {
            for (int j = 0; j < repeatY; j++) {
                sb.draw(textureRegion, i * width, j * height);
            }
        }

        sb.enableBlending();
    }

    @Override
    public void update(float v) {

    }

    @Override
    protected void computeDimensions() {

    }
}
