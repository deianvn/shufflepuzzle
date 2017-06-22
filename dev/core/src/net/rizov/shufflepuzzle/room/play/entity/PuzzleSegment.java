package net.rizov.shufflepuzzle.room.play.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import net.rizov.gameutils.scene.RoomEntity;
import net.rizov.gameutils.scene.Room;

public class PuzzleSegment extends RoomEntity {

    private int index;

    private TextureRegion textureRegion;

    private TextureRegion decorationRegion;

    private TextureRegion coverRegion;

    private int width;

    private int height;

    public PuzzleSegment(Room room) {
        super(room);
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
        computeDimensions();
        setOrigin(width / 2, height / 2);
    }

    public void setDecorationRegion(TextureRegion decorationRegion) {
        this.decorationRegion = decorationRegion;
    }

    public void setCoverRegion(TextureRegion coverRegion) {
        this.coverRegion = coverRegion;
    }

    public TextureRegion getCoverRegion() {
        return coverRegion;
    }

    @Override
    public Room<?, ?> getRoom() {
        return super.getRoom();
    }

    @Override
    public void draw() {
        if (textureRegion != null) {
            SpriteBatch sb = getSpriteBatch();
            drawTexture(sb, textureRegion);

            if (decorationRegion != null) {
                drawTexture(sb, decorationRegion);
            }

            if (coverRegion != null) {
                drawTexture(sb, coverRegion, true);
            }
        }
    }

    @Override
    protected void computeDimensions() {
        width = textureRegion.getRegionWidth();
        height = textureRegion.getRegionHeight();
        setWidth(width);
        setHeight(height);
    }

    public boolean intersect(float x, float y) {
        return x > getPositionX() && x <= getPositionX() + width &&
                y > getPositionY() && y <= getPositionY() + height;
    }

    private void drawTexture(SpriteBatch sb, TextureRegion textureRegion) {
        sb.draw(textureRegion,
                getPositionX(), getPositionY(),
                getOriginX(), getOriginY(),
                textureRegion.getRegionWidth(), textureRegion.getRegionHeight(),
                getScaleX(), getScaleY(), getRotation());
    }

    private void drawTexture(SpriteBatch sb, TextureRegion textureRegion, boolean stretch) {
        if (stretch) {
            sb.draw(textureRegion,
                    getPositionX(), getPositionY(),
                    getOriginX(), getOriginY(),
                    getWidth(), getHeight(),
                    getScaleX(), getScaleY(), getRotation());
        } else {
            drawTexture(sb, textureRegion);
        }
    }

}
