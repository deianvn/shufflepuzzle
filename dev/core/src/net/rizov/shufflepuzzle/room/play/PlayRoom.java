package net.rizov.shufflepuzzle.room.play;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import net.rizov.gameutils.scene.Game;
import net.rizov.gameutils.scene.Room;
import net.rizov.shufflepuzzle.utils.helper.UIHelper;

public abstract class PlayRoom extends Room<PlayRoom.PlayRoomStatus, PlayRoom.PlayRoomEvent> {

    public enum PlayRoomEvent {}

    public enum PlayRoomStatus {playing, paused, winning, won, losing, lost}

    public PlayRoom(Game game) {
        super(game);
        setStatus(PlayRoomStatus.playing);
    }

    @Override
    protected void resize(int i, int i1) {

    }

    @Override
    protected void hide() {

    }

    @Override
    protected void pause() {
        if (getStatus() == PlayRoomStatus.playing) {
            pauseGame();
        }
    }

    @Override
    protected void resume() {
        if (getStatus() == PlayRoomStatus.paused) {
            resumeGame();
        }
    }

    @Override
    public void draw() {
        switch (getStatus()) {
            case playing:
                drawPlaying();
                break;
            case paused:
                drawPaused();
                break;
            case winning:
                drawWinning();
            case won:
                drawWon();
                break;
            case losing:
                drawLosing();
                break;
            case lost:
                drawLost();
                break;
            default:
                break;
        }
    }

    @Override
    public void update(float deltaTime) {
        switch (getStatus()) {
            case playing:
                updatePlaying(deltaTime);
                break;
            case paused:
                updatePaused(deltaTime);
                break;
            case winning:
                updateWinning(deltaTime);
                break;
            case won:
                updateWon(deltaTime);
                break;
            case losing:
                updateLosing(deltaTime);
                break;
            case lost:
                updateLost(deltaTime);
            default:
                break;
        }
    }

    public void winGame() {
        UIHelper.winGame();
    }

    public void loseGame() {
        UIHelper.loseGame();
    }

    @Override
    protected InputProcessor getInputProcessor() {
        return null;
    }

    public abstract void pauseGame();

    public abstract void resumeGame();

    protected abstract void updatePlaying(float deltaTime);

    protected abstract void updatePaused(float deltaTime);

    protected abstract void updateWinning(float deltaTime);

    protected abstract void updateWon(float deltaTime);

    protected abstract void updateLosing(float deltaTime);

    protected abstract void updateLost(float deltaTime);

    protected abstract void drawPlaying();

    protected abstract void drawPaused();

    protected abstract void drawWinning();

    protected abstract void drawWon();

    protected abstract void drawLost();

    protected abstract void drawLosing();

    @Override
    protected void computeDimensions() {

    }

    @Override
    protected void show() {
        UIHelper.setWinSample(getAsset("win.mp3", Sound.class));
        UIHelper.setLoseSample(getAsset("lose.mp3", Sound.class));
        UIHelper.setClickSample(getAsset("tap.mp3", Sound.class));
        UIHelper.setDropSample(getAsset("tap.mp3", Sound.class));
        UIHelper.setSwapSample(getAsset("swap.mp3", Sound.class));
    }

    @Override
    protected void loadData() {
        loadAsset("win.mp3", Sound.class);
        loadAsset("lose.mp3", Sound.class);
        loadAsset("tap.mp3", Sound.class);
        loadAsset("swap.mp3", Sound.class);
    }

    @Override
    protected void prepare() {

    }
}
