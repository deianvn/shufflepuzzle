package net.rizov.shufflepuzzle.room.menu.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import net.rizov.shufflepuzzle.room.menu.entity.ChallengeSelector;
import net.rizov.shufflepuzzle.room.menu.entity.HardnessSelector;
import net.rizov.shufflepuzzle.room.menu.entity.ImageSelector;
import net.rizov.shufflepuzzle.room.menu.entity.MainMenu;
import net.rizov.shufflepuzzle.room.menu.MenuRoom;
import net.rizov.shufflepuzzle.room.menu.entity.PuzzlesCountSelector;
import net.rizov.shufflepuzzle.room.menu.entity.TimeSelector;
import net.rizov.shufflepuzzle.room.play.challenge.ArcadeChallengePlayRoom;
import net.rizov.shufflepuzzle.room.play.challenge.SingleChallengePlayRoom;
import net.rizov.shufflepuzzle.room.play.timetrial.TimeTrialPlayRoom;
import net.rizov.shufflepuzzle.utils.ChallengeDescriptor;
import net.rizov.shufflepuzzle.utils.LinkProvider;
import net.rizov.shufflepuzzle.utils.Pack;
import net.rizov.shufflepuzzle.utils.PackList;
import net.rizov.shufflepuzzle.utils.config.ConfigManager;
import net.rizov.shufflepuzzle.utils.helper.UIHelper;

public class TouchScreenMenuRoomInputProcessor extends MenuRoomInputProcessor implements GestureDetector.GestureListener {

    private LinkProvider linkProvider;

    private Float tapPosition;

    private Float downPosition;

    private Float panForce;

    private Pack pack;

    private Integer hardness;

    private Integer puzzlesCount;

    private Integer time;

    @Override
    public void setRoom(MenuRoom room) {
        super.setRoom(room);
        linkProvider = getRoom().getGame().inject(LinkProvider.class);
    }

    @Override
    public void setAsGdxInputProcessor() {
        Gdx.input.setInputProcessor(new GestureDetector(this) {
            @Override
            public boolean keyDown(int keycode) {

                if (keycode != Input.Keys.ESCAPE && keycode != Input.Keys.BACK) {
                    return false;
                }

                switch (getRoom().getStatus()) {
                    case mainMenu:
                        getRoom().getGame().quit();
                        break;
                    case arcadeSelectMenu:
                        getRoom().setStatus(MenuRoom.MenuRoomStatus.mainMenu);
                        break;
                    case timeTrialHardnessSelectMenu:
                        getRoom().setStatus(MenuRoom.MenuRoomStatus.timeTrialImageSelectMenu);
                        break;
                    case timeTrialImageSelectMenu:
                        getRoom().setStatus(MenuRoom.MenuRoomStatus.mainMenu);
                        break;
                    case challengeHardnessSelectMenu:
                        getRoom().setStatus(MenuRoom.MenuRoomStatus.mainMenu);
                        break;
                    case challengePuzzlesCountSelectMenu:
                        getRoom().setStatus(MenuRoom.MenuRoomStatus.challengeHardnessSelectMenu);
                        break;
                    case challengeTimeSelectMenu:
                        getRoom().setStatus(MenuRoom.MenuRoomStatus.challengePuzzlesCountSelectMenu);
                        break;
                }

                return false;
            }
        });
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {

        Vector3 coordinates = new Vector3(x, y, 0);
        getRoom().getCamera().unproject(coordinates);

        switch (getRoom().getStatus()) {
            case timeTrialImageSelectMenu:
                handleImagesTableTouchDown(coordinates.x, coordinates.y);
                break;
            case arcadeSelectMenu:
                handleChallengesTouchDown(coordinates.x, coordinates.y);
                break;
        }

        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        Vector3 coordinates = new Vector3(x, y, 0);
        getRoom().getCamera().unproject(coordinates);

        switch (getRoom().getStatus()) {
            case mainMenu:
                handleMainMenuTap(coordinates.x, coordinates.y);
                break;
            case timeTrialHardnessSelectMenu:
                handleTimeTrialHardnessTap(coordinates.x, coordinates.y);
                break;
            case timeTrialImageSelectMenu:
                handleImagesTableTap(coordinates.x, coordinates.y);
                break;
            case challengeHardnessSelectMenu:
                handleChallengeHardnessTap(coordinates.x, coordinates.y);
                break;
            case challengePuzzlesCountSelectMenu:
                handleChallengePuzzlesCountTap(coordinates.x, coordinates.y);
                break;
            case challengeTimeSelectMenu:
                handleChallengeTimeSelectorTap(coordinates.x, coordinates.y);
                break;
            case arcadeSelectMenu:
                handleArcadeSelectorTap(coordinates.x, coordinates.y);
                break;
        }

        cleanData();
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {

        Vector3 coordinates = new Vector3(x, y, 0);
        getRoom().getCamera().unproject(coordinates);

        switch (getRoom().getStatus()) {
            case timeTrialImageSelectMenu:
                handleImagesTablePan(coordinates.x, coordinates.y);
                break;
            case arcadeSelectMenu:
                handleChallengesPan(coordinates.x, coordinates.y);
                break;
        }

        panForce = Math.abs(deltaY) > 1f ? deltaY : 0f;

        if (panForce > 100) {
            panForce = 100f;
        }

        if (panForce < -500) {
            panForce = -500f;
        }

        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {

        Vector3 coordinates = new Vector3(x, y, 0);
        getRoom().getCamera().unproject(coordinates);

        switch (getRoom().getStatus()) {
            case timeTrialImageSelectMenu:
                handleImagesTablePanStop(coordinates.x, coordinates.y);
                break;
            case arcadeSelectMenu:
                handleChallengesPanStop(coordinates.x, coordinates.y);
                break;
        }

        cleanData();
        return false;

    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

    private void handleImagesTableTap(float x, float y) {
        ImageSelector.Area area = getImageSelector().click(x, y);

        if (area == null) {
            return;
        }

        switch (area) {
            case imageArea:
                String id = getImageSelector().getImageName(x, y);

                if (id != null) {
                    if (pack == null) {
                        pack = new Pack(getRoom().getGame().inject(PackList.class));
                    }

                    pack.positionTo(id);
                    getRoom().setStatus(MenuRoom.MenuRoomStatus.timeTrialHardnessSelectMenu);
                    UIHelper.click();
                }

                break;
            case backButton:
                getRoom().setStatus(MenuRoom.MenuRoomStatus.mainMenu);
                break;
        }
    }

    private void handleMainMenuTap(float x, float y) {
        MainMenu.Area area = getMainMenu().click(x, y);

        if (area == null) {
            return;
        }

        switch (area) {
            case timeTrial:
                getRoom().setStatus(MenuRoom.MenuRoomStatus.timeTrialImageSelectMenu);
                break;
            case challenge:
                getRoom().setStatus(MenuRoom.MenuRoomStatus.challengeHardnessSelectMenu);
                break;
            case arcade:
                getRoom().setStatus(MenuRoom.MenuRoomStatus.arcadeSelectMenu);
                break;
            case morePuzzles:
                Gdx.net.openURI(linkProvider.getMoreLink());
                break;
            case rate:
                Gdx.net.openURI(linkProvider.getGameLink());
                break;
            case sound:
                ConfigManager.setSoundEnabled(!ConfigManager.isSoundEnabled());
                ConfigManager.save();
                break;
            case vibrate:
                ConfigManager.setVibrationEnabled(!ConfigManager.isVibrationEnabled());
                break;
            case exit:
                getRoom().getGame().quit();
                break;
        }
    }

    private void handleTimeTrialHardnessTap(float x, float y) {
        HardnessSelector.Area area = getHardnessSelector().click(x, y);

        if (area == null) {
            return;
        }

        switch (area) {
            case three: {
                TimeTrialPlayRoom playRoom = new TimeTrialPlayRoom(getRoom().getGame(), pack, 3);
                getRoom().getGame().setCurrentRoom(playRoom);
                break;
            }
            case four: {
                TimeTrialPlayRoom playRoom = new TimeTrialPlayRoom(getRoom().getGame(), pack, 4);
                getRoom().getGame().setCurrentRoom(playRoom);
                break;
            }
            case five: {
                TimeTrialPlayRoom playRoom = new TimeTrialPlayRoom(getRoom().getGame(), pack, 5);
                getRoom().getGame().setCurrentRoom(playRoom);
                break;
            }
            case six: {
                TimeTrialPlayRoom playRoom = new TimeTrialPlayRoom(getRoom().getGame(), pack, 6);
                getRoom().getGame().setCurrentRoom(playRoom);
                break;
            }
            case back:
                getRoom().setStatus(MenuRoom.MenuRoomStatus.timeTrialImageSelectMenu);
                break;
        }
    }

    private void handleChallengeHardnessTap(float x, float y) {
        HardnessSelector.Area area = getHardnessSelector().click(x, y);

        if (area == null) {
            return;
        }

        switch (area) {
            case three: {
                hardness = 3;
                getRoom().setStatus(MenuRoom.MenuRoomStatus.challengePuzzlesCountSelectMenu);
                break;
            }
            case four: {
                hardness = 4;
                getRoom().setStatus(MenuRoom.MenuRoomStatus.challengePuzzlesCountSelectMenu);
                break;
            }
            case five: {
                hardness = 5;
                getRoom().setStatus(MenuRoom.MenuRoomStatus.challengePuzzlesCountSelectMenu);
                break;
            }
            case six: {
                hardness = 6;
                getRoom().setStatus(MenuRoom.MenuRoomStatus.challengePuzzlesCountSelectMenu);
                break;
            }
            case back:
                getRoom().setStatus(MenuRoom.MenuRoomStatus.mainMenu);
                break;
        }
    }

    private void handleChallengePuzzlesCountTap(float x, float y) {
        PuzzlesCountSelector.Area area = getPuzzlesCountSelector().click(x, y);

        if (area == null) {
            return;
        }

        switch (area) {
            case count1: {
                puzzlesCount = 1;
                getRoom().setStatus(MenuRoom.MenuRoomStatus.challengeTimeSelectMenu);
                break;
            }
            case count2: {
                puzzlesCount = 2;
                getRoom().setStatus(MenuRoom.MenuRoomStatus.challengeTimeSelectMenu);
                break;
            }
            case count3: {
                puzzlesCount = 3;
                getRoom().setStatus(MenuRoom.MenuRoomStatus.challengeTimeSelectMenu);
                break;
            }
            case count5: {
                puzzlesCount = 5;
                getRoom().setStatus(MenuRoom.MenuRoomStatus.challengeTimeSelectMenu);
                break;
            }
            case count10: {
                puzzlesCount = 10;
                getRoom().setStatus(MenuRoom.MenuRoomStatus.challengeTimeSelectMenu);
                break;
            }
            case count15: {
                puzzlesCount = 15;
                getRoom().setStatus(MenuRoom.MenuRoomStatus.challengeTimeSelectMenu);
                break;
            }
            case count20: {
                puzzlesCount = 20;
                getRoom().setStatus(MenuRoom.MenuRoomStatus.challengeTimeSelectMenu);
                break;
            }
            case count25: {
                puzzlesCount = 25;
                getRoom().setStatus(MenuRoom.MenuRoomStatus.challengeTimeSelectMenu);
                break;
            }
            case count30: {
                puzzlesCount = 30;
                getRoom().setStatus(MenuRoom.MenuRoomStatus.challengeTimeSelectMenu);
                break;
            }

            case back:
                getRoom().setStatus(MenuRoom.MenuRoomStatus.challengeHardnessSelectMenu);
                break;
        }
    }

    private void handleChallengeTimeSelectorTap(float x, float y) {
        TimeSelector.Area area = getTimeSelector().click(x, y);

        if (area == null) {
            return;
        }

        switch (area) {
            case seconds10: {
                time = 10 * 1000;
                setChallengePlayRoom();
                break;
            }
            case seconds15: {
                time = 15 * 1000;
                setChallengePlayRoom();
                break;
            }
            case seconds30: {
                time = 30 * 1000;
                setChallengePlayRoom();
                break;
            }
            case minutes1: {
                time = 1 * 60 * 1000;
                setChallengePlayRoom();
                break;
            }
            case minutes2: {
                time = 2 * 60 * 1000;
                setChallengePlayRoom();
                break;
            }
            case minutes5: {
                time = 5 * 60 * 1000;
                setChallengePlayRoom();
                break;
            }
            case minutes10: {
                time = 10 * 60 * 1000;
                setChallengePlayRoom();
                break;
            }
            case minutes20: {
                time = 20 * 60 * 1000;
                setChallengePlayRoom();
                break;
            }
            case minutes30: {
                time = 30 * 60 * 1000;
                setChallengePlayRoom();
                break;
            }


            case back:
                getRoom().setStatus(MenuRoom.MenuRoomStatus.challengePuzzlesCountSelectMenu);
                break;
        }
    }

    private void handleArcadeSelectorTap(float x, float y) {
        ChallengeSelector.Area area = getChallengeSelector().click(x, y);

        if (area == null) {
            return;
        }

        switch (area) {
            case backButton:
                getRoom().setStatus(MenuRoom.MenuRoomStatus.mainMenu);
                break;
            case challenges:
                ChallengeDescriptor descriptor = getChallengeSelector().getChallengeDescriptor(x, y);

                if (descriptor != null) {
                    getRoom().getGame().setCurrentRoom(new ArcadeChallengePlayRoom(getRoom().getGame(), new Pack(getRoom().getGame().inject(PackList.class)), descriptor));
                    UIHelper.click();
                }

                break;
        }
    }

    private void setChallengePlayRoom() {
        getRoom().getGame().setCurrentRoom(new SingleChallengePlayRoom(getRoom().getGame(), new Pack(getRoom().getGame().inject(PackList.class)), hardness, puzzlesCount, time));
    }

    private void handleImagesTableTouchDown(float x, float y) {
        downPosition = y;
        tapPosition = getImageSelector().getPositionY();
        getImageSelector().stop();
    }

    private void handleImagesTablePan(float x, float y) {

        if (downPosition != null && tapPosition != null) {
            float position = tapPosition - downPosition + y;

            if (position < getImageSelector().getMin()) {
                position = getImageSelector().getMin();
                downPosition = y;
                tapPosition = getImageSelector().getPositionY();
            }

            if (position > getImageSelector().getMax()) {
                position = getImageSelector().getMax();
                downPosition = y;
                tapPosition = getImageSelector().getPositionY();
            }

            getImageSelector().scrollTo(position);
        }

    }

    private void handleImagesTablePanStop(float x, float y) {
        if (panForce != null) {
            getImageSelector().move(panForce);
        }
    }

    private void handleChallengesTouchDown(float x, float y) {
        downPosition = y;
        tapPosition = getChallengeSelector().getPositionY();
        getChallengeSelector().stop();
    }

    private void handleChallengesPan(float x, float y) {

        if (downPosition != null && tapPosition != null) {
            float position = tapPosition - downPosition + y;

            if (position < getChallengeSelector().getMin()) {
                position = getChallengeSelector().getMin();
                downPosition = y;
                tapPosition = getChallengeSelector().getPositionY();
            }

            if (position > getChallengeSelector().getMax()) {
                position = getChallengeSelector().getMax();
                downPosition = y;
                tapPosition = getChallengeSelector().getPositionY();
            }

            getChallengeSelector().scrollTo(position);
        }

    }

    private void handleChallengesPanStop(float x, float y) {
        if (panForce != null) {
            getChallengeSelector().move(panForce);
        }
    }

    private void cleanData() {
        downPosition = null;
        tapPosition = null;
        panForce = null;
    }

}
