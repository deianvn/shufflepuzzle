package net.rizov.shufflepuzzle.room.play.challenge.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector3;
import net.rizov.shufflepuzzle.room.menu.MenuRoom;
import net.rizov.shufflepuzzle.room.play.PlayRoom;
import net.rizov.shufflepuzzle.room.play.entity.Menu;
import net.rizov.shufflepuzzle.utils.save.SaveManager;
import net.rizov.shufflepuzzle.utils.save.SingleRecord;

public class TouchScreenPlayRoomInputProcessor extends ChallengePlayRoomInputProcessor implements InputProcessor {

    @Override
    public void setAsGdxInputProcessor() {

        final InputProcessor INSTANCE = this;

        Gdx.input.setInputProcessor(
                new GestureDetector(new GestureDetector.GestureAdapter() {
                    @Override
                    public boolean tap(float x, float y, int count, int button) {
                        switch (getRoom().getStatus()) {
                            case playing: {
                                if (button > 0) {
                                    return false;
                                }

                                Vector3 coordinates = new Vector3(x, y, 0);
                                coordinates = getRoom().getViewport().unproject(coordinates);
                                handleTapPlaying(coordinates.x, coordinates.y);
                            }
                        }

                        return false;
                    }
                }) {
                    @Override
                    public boolean keyDown(int keycode) {
                        return INSTANCE.keyDown(keycode);
                    }

                    @Override
                    public boolean touchDown(int x, int y, int pointer, int button) {
                        INSTANCE.touchDown(x, y, pointer, button);
                        return super.touchDown(x, y, pointer, button);
                    }

                    @Override
                    public boolean touchUp(int x, int y, int pointer, int button) {
                        INSTANCE.touchUp(x, y, pointer, button);

                        return super.touchUp(x, y, pointer, button);
                    }

                    @Override
                    public boolean touchDragged(int x, int y, int pointer) {
                        return INSTANCE.touchDragged(x, y, pointer);
                    }
                }
        );
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (getRoom().getStatus()) {
            case playing:
                return handleKeyDownPlaying(keycode);
            case paused:
                return handleKeyDownPaused(keycode);
            case won:
                return handleKeyDownWon(keycode);
            default:
                break;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if (pointer > 0) {
            return false;
        }

        Vector3 coordinates = new Vector3(screenX, screenY, 0);
        coordinates = getRoom().getViewport().unproject(coordinates);

        switch (getRoom().getStatus()) {
            case playing:
                return handleTouchDownPlaying(coordinates.x, coordinates.y);
            case paused:
                return handleTouchDownPaused(coordinates.x, coordinates.y);
            case won:
                return handleTouchDownWon(coordinates.x, coordinates.y);
            case lost:
                return handleTouchDownLost(coordinates.x, coordinates.y);
            default:
                break;
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (pointer > 0) {
            return false;
        }

        Vector3 coordinates = new Vector3(screenX, screenY, 0);
        coordinates = getRoom().getViewport().unproject(coordinates);

        switch (getRoom().getStatus()) {
            case playing:
                return handleTouchUpPlaying(coordinates.x, coordinates.y);
            case paused:
                return handleTouchUpPaused(coordinates.x, coordinates.y);
            case won:
                return handleTouchUpWon(coordinates.x, coordinates.y);
            default:
                break;
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (pointer > 0) {
            return false;
        }

        Vector3 coordinates = new Vector3(screenX, screenY, 0);
        coordinates = getRoom().getViewport().unproject(coordinates);

        switch (getRoom().getStatus()) {
            case playing:
                return handleTouchDraggedPlaying(coordinates.x, coordinates.y);
            case paused:
                return handleTouchDraggedPaused(coordinates.x, coordinates.y);
            case won:
                return handleTouchDraggedWon(coordinates.x, coordinates.y);
            default:
                break;
        }

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    protected boolean handleKeyDownPlaying(int keycode) {

        if (keycode != Input.Keys.BACK && keycode != Input.Keys.ESCAPE) {
            return false;
        }

        if (getRoom().getStatus() != PlayRoom.PlayRoomStatus.won) {
            getRoom().pauseGame();
            return true;
        }

        return false;
    }

    protected boolean handleKeyDownPaused(int keycode) {
        if (keycode != Input.Keys.BACK && keycode != Input.Keys.ESCAPE) {
            return false;
        }

        if (getRoom().getStatus() == PlayRoom.PlayRoomStatus.paused) {
            getRoom().resumeGame();
            return true;
        }

        return false;
    }

    protected boolean handleKeyDownWon(int keycode) {
        if (keycode != Input.Keys.BACK && keycode != Input.Keys.ESCAPE) {
            return false;
        }

        getRoom().getGame().setCurrentRoom(new MenuRoom(getRoom().getGame()));
        return false;
    }

    protected boolean handleTouchDownPlaying(float x, float y) {
        if (getPuzzle().intersects(x, y) && getPuzzle().over(x, y) && getPuzzle().select(x, y)) {
            return true;
        }

        return false;
    }

    protected boolean handleTouchDownPaused(float x, float y) {
        getPausedDialog().over(x, y);
        getPausedDialog().click();

        return false;
    }

    protected boolean handleTouchDownWon(float x, float y) {
        getWellDoneDialog().over(x, y);
        getWellDoneDialog().click();

        return false;
    }

    protected boolean handleTouchDownLost(float x, float y) {
        getFailedDialog().over(x, y);
        getFailedDialog().click();

        return false;
    }

    protected boolean handleTouchUpPlaying(float x, float y) {
        if (getPuzzle().isSelected()) {
            drop();
            return true;
        }

        return false;
    }

    protected boolean handleTouchUpPaused(float x, float y) {
        return false;
    }

    protected boolean handleTouchUpWon(float x, float y) {
        return false;
    }

    protected boolean handleTouchDraggedPlaying(float x, float y) {
        if (getPuzzle().isSelected()) {
            getPuzzle().move(x, y);
            return true;
        }

        return false;
    }

    protected boolean handleTouchDraggedPaused(float x, float y) {
        return false;
    }

    protected boolean handleTouchDraggedWon(float x, float y) {
        return false;
    }

    protected boolean handleTapPlaying(float x, float y) {
        Menu.Area area = getMenu().click(x, y);

        if (area == null) {
            getPreview().click(x, y);
            return false;
        }

        switch (area) {
            case menu:
                getRoom().pauseGame();
                break;
            case quit:
                getRoom().getGame().setCurrentRoom(new MenuRoom(getRoom().getGame()));
                break;
        }

        return false;
    }

    private void drop() {
        getPuzzle().drop();

        if (isFinished()) {
            if (isWon()) {
                getRoom().winGame();
            } else {
                getRoom().loseGame();
            }
        } else if (getPuzzle().isFinished()) {
            next();
        }
    }

}
