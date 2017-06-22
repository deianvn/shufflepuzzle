package net.rizov.shufflepuzzle.room.menu;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import net.rizov.gameutils.scene.Game;
import net.rizov.gameutils.scene.Room;
import net.rizov.shufflepuzzle.room.menu.entity.ImageSelector;
import net.rizov.shufflepuzzle.room.menu.entity.MainMenu;
import net.rizov.shufflepuzzle.room.menu.entity.PuzzlesCountSelector;
import net.rizov.shufflepuzzle.room.menu.entity.TimeSelector;
import net.rizov.shufflepuzzle.room.menu.input.MenuRoomInputProcessor;
import net.rizov.shufflepuzzle.room.menu.entity.HardnessSelector;
import net.rizov.shufflepuzzle.room.menu.entity.ChallengeSelector;
import net.rizov.shufflepuzzle.utils.Pack;
import net.rizov.shufflepuzzle.utils.PackList;
import net.rizov.shufflepuzzle.utils.helper.UIHelper;

public class MenuRoom extends Room<MenuRoom.MenuRoomStatus, MenuRoom.MenuRoomEvent> {

    public enum MenuRoomEvent {}

    public enum MenuRoomStatus {
        mainMenu,
        arcadeSelectMenu,
        timeTrialImageSelectMenu,
        timeTrialHardnessSelectMenu,
        challengeHardnessSelectMenu,
        challengePuzzlesCountSelectMenu,
        challengeTimeSelectMenu
    }

    private MenuRoomInputProcessor menuRoomInputProcessor;

    private MainMenu mainMenu;

    private HardnessSelector hardnessSelector;

    private ImageSelector imageSelector;

    private TimeSelector timeSelector;

    private PuzzlesCountSelector puzzlesCountSelector;

    private ChallengeSelector challengeSelector;

    private Pack pack;

    public MenuRoom(Game game) {
        super(game);
        setStatus(MenuRoomStatus.mainMenu);
        pack = new Pack(getGame().inject(PackList.class));
    }

    @Override
    protected void loadData() {
        loadAsset(pack.getAtlas(), TextureAtlas.class);
        loadAsset("menu.pack", TextureAtlas.class);
        loadAsset("tap.mp3", Sound.class);
    }

    @Override
    protected void prepare() {

    }

    @Override
    protected void resize(int i, int i1) {

    }

    @Override
    protected void show() {

        UIHelper.setClickSample(getAsset("tap.mp3", Sound.class));

        mainMenu = new MainMenu(this);

        imageSelector = new ImageSelector(this);
        imageSelector.sethMargin(10);
        imageSelector.setvMargin(15);
        imageSelector.setPack(pack);
        imageSelector.scrollTo(imageSelector.getMin());

        hardnessSelector = new HardnessSelector(this);

        timeSelector = new TimeSelector(this);

        puzzlesCountSelector = new PuzzlesCountSelector(this);

        challengeSelector = new ChallengeSelector(this);

        menuRoomInputProcessor = getGame().inject(MenuRoomInputProcessor.class);
        menuRoomInputProcessor.setRoom(this);

        menuRoomInputProcessor.setMainMenu(mainMenu);
        menuRoomInputProcessor.setImageSelector(imageSelector);
        menuRoomInputProcessor.setHardnessSelector(hardnessSelector);
        menuRoomInputProcessor.setTimeSelector(timeSelector);
        menuRoomInputProcessor.setPuzzlesCountSelector(puzzlesCountSelector);
        menuRoomInputProcessor.setChallengeSelector(challengeSelector);

        menuRoomInputProcessor.setAsGdxInputProcessor();
    }

    @Override
    protected void hide() {

    }

    @Override
    protected void pause() {

    }

    @Override
    protected void resume() {

    }

    @Override
    public void draw() {
        switch (getStatus()) {
            case mainMenu:
                mainMenu.draw();
                break;
            case timeTrialImageSelectMenu:
                imageSelector.draw();
                break;
            case timeTrialHardnessSelectMenu:
                hardnessSelector.draw();
                break;
            case challengeHardnessSelectMenu:
                hardnessSelector.draw();
                break;
            case challengePuzzlesCountSelectMenu:
                puzzlesCountSelector.draw();
                break;
            case challengeTimeSelectMenu:
                timeSelector.draw();
                break;
            case arcadeSelectMenu:
                challengeSelector.draw();
                break;
        }
    }

    @Override
    public void update(float dt) {
        switch (getStatus()) {
            case mainMenu:
                mainMenu.update(dt);
                break;
            case timeTrialImageSelectMenu:
                imageSelector.update(dt);
                break;
            case timeTrialHardnessSelectMenu:
                hardnessSelector.update(dt);
                break;
            case challengeHardnessSelectMenu:
                hardnessSelector.update(dt);
                break;
            case challengePuzzlesCountSelectMenu:
                puzzlesCountSelector.update(dt);
                break;
            case challengeTimeSelectMenu:
                timeSelector.update(dt);
                break;
            case arcadeSelectMenu:
                challengeSelector.update(dt);
                break;
        }
    }

    @Override
    protected InputProcessor getInputProcessor() {
        return null;
    }

    @Override
    protected void computeDimensions() {

    }

}
