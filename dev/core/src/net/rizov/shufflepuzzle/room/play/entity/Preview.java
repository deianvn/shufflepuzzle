package net.rizov.shufflepuzzle.room.play.entity;

import com.badlogic.gdx.math.Rectangle;
import net.rizov.gameutils.scene.Room;
import net.rizov.gameutils.scene.TextureEntity;

public class Preview extends TextureEntity {

    private TextureEntity cover;

    private TextureEntity fadedCover;

    private Rectangle bounds;

    private float revealFullTime = 1f;

    private float revealTime = 3f;

    private float revealedFullTime;

    private float revealedTime;

    private boolean revealed;

    public Preview(Room room) {
        super(room);
        cover = new TextureEntity(room);
        cover.setTextureRegion("preview-cover", "play.pack");
        fadedCover = new TextureEntity(room);
        fadedCover.setTextureRegion("preview-faded-cover", "play.pack");
        setPositionX(235);
        setPositionY(485);
        setWidth(235);
        setHeight(235);
        cover.setPositionX(getPositionX());
        cover.setPositionY(getPositionY());
        fadedCover.setPositionX(getPositionX());
        fadedCover.setPositionY(getPositionY());
        bounds = new Rectangle();
        bounds.setPosition(getPositionX(), getPositionY());
        bounds.setSize(getWidth(), getHeight());
    }

    @Override
    protected void onRegionChanged() {
        setScaleX(getWidth() / getTextureRegion().getRegionWidth());
        setScaleY(getHeight() / getTextureRegion().getRegionHeight());
    }

    @Override
    public void draw() {
        super.draw();

        if (revealed) {
            getSpriteBatch().setColor(1f, 1f, 1f, revealedTime / revealTime);
            fadedCover.draw();
        } else {
            cover.draw();
        }

        if (revealed) {
            getSpriteBatch().setColor(1f, 1f, 1f, 1f);
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (revealed) {
            revealedFullTime -= deltaTime;

            if (revealedFullTime <= 0) {
                revealedTime += deltaTime;

                if (revealedTime >= revealTime) {
                    revealed = false;
                }
            }
        }
    }

    public void click(float x, float y) {
        if (!revealed && bounds.contains(x, y)) {
            revealed = true;
            revealedTime = 0;
            revealedFullTime = revealFullTime;
        }
    }

    public void cover() {
        revealed = false;
    }
}
