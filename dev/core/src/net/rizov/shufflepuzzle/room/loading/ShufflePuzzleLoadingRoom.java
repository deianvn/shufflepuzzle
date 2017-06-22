package net.rizov.shufflepuzzle.room.loading;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import net.rizov.gameutils.scene.Game;
import net.rizov.gameutils.scene.LoadingRoom;
import net.rizov.gameutils.scene.TextureEntity;

public class ShufflePuzzleLoadingRoom extends LoadingRoom {

    private TextureEntity background;

    public ShufflePuzzleLoadingRoom(Game game) {
        super(game);
        background = new TextureEntity(this);
        background.setTextureRegion(new Texture(Gdx.files.internal("loading.png")));
    }

    @Override
    protected InputProcessor getInputProcessor() {
        return null;
    }

    @Override
    protected void prepare() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void resize(int i, int i1) {

    }

    @Override
    protected void show() {

    }

    @Override
    protected void pause() {

    }

    @Override
    protected void resume() {

    }

    @Override
    public void draw() {
        background.draw();
    }

    @Override
    protected void dispose() {
        super.dispose();
        background.getTextureRegion().getTexture().dispose();
    }

    @Override
    protected void computeDimensions() {

    }
}
