package net.rizov.shufflepuzzle;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import net.rizov.gameutils.scene.Game;
import net.rizov.shufflepuzzle.room.loading.ShufflePuzzleLoadingRoom;
import net.rizov.shufflepuzzle.room.menu.MenuRoom;
import net.rizov.shufflepuzzle.utils.config.ConfigManager;

public class ShufflePuzzle extends ApplicationAdapter {

    private Game game;

    public ShufflePuzzle(Game game) {
        this.game = game;
    }

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        Gdx.input.setCatchBackKey(true);
        OrthographicCamera camera = new OrthographicCamera();
        camera.position.set(240, 400, 0);
        Viewport viewport = new FitViewport(480, 800, camera);
        game.setViewport(viewport);
        game.setLoadingRoom(new ShufflePuzzleLoadingRoom(game));
        game.setCurrentRoom(new MenuRoom(game));
    }

    @Override
    public void render() {
        game.process();
    }

    @Override
    public void resize(int width, int height) {
        game.resize(width, height);
    }

    @Override
    public void pause() {
        ConfigManager.save();
        game.pause();
    }

    @Override
    public void resume() {
        ConfigManager.load();
        game.resume();
    }

    @Override
    public void dispose() {
        game.dispose();
    }
}
