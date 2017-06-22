package net.rizov.shufflepuzzle.room.play.timetrial;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import net.rizov.gameutils.scene.Game;
import net.rizov.shufflepuzzle.entity.Background;
import net.rizov.shufflepuzzle.room.menu.MenuRoom;
import net.rizov.shufflepuzzle.room.play.PlayRoom;
import net.rizov.shufflepuzzle.room.play.entity.PausedDialog;
import net.rizov.shufflepuzzle.room.play.entity.Preview;
import net.rizov.shufflepuzzle.room.play.entity.Puzzle;
import net.rizov.shufflepuzzle.room.play.input.PlayRoomInputProcessor;
import net.rizov.shufflepuzzle.room.play.timetrial.entity.InfoBoard;
import net.rizov.shufflepuzzle.room.play.entity.Menu;
import net.rizov.shufflepuzzle.room.play.timetrial.entity.WellDoneDialog;
import net.rizov.shufflepuzzle.room.play.timetrial.input.TimeTrialPlayRoomInputProcessor;
import net.rizov.shufflepuzzle.utils.ImageDescriptor;
import net.rizov.shufflepuzzle.utils.Pack;
import net.rizov.shufflepuzzle.utils.save.SaveManager;
import net.rizov.shufflepuzzle.utils.save.SingleRecord;

public class TimeTrialPlayRoom extends PlayRoom {

    private PlayRoomInputProcessor inputProcessor;

    private Menu menu;

    private Puzzle puzzle;

    private Preview preview;

    private InfoBoard infoBoard;

    private Background background;

    private PausedDialog pausedDialog;

    private WellDoneDialog wellDoneDialog;

    private Pack pack;

    private int size;

    public TimeTrialPlayRoom(Game game, Pack pack, int size) {
        super(game);
        this.pack = pack;
        this.size = size;
    }

    public void startGame() {
        ImageDescriptor imageDescriptor = pack.current();
        Texture texture = getAsset(imageDescriptor.getUri(), Texture.class);
        puzzle.create(imageDescriptor.getShortFileName(), texture, size);
        puzzle.shuffle();
        preview.setTextureRegion(imageDescriptor.getUri());
        puzzle.clearTimePlayed();
        infoBoard.clearTime();

        SingleRecord singleRecord = SaveManager.getSingleRecord(imageDescriptor.getShortFileName(), puzzle.getCount());

        if (singleRecord != null) {
            infoBoard.setBestTime(singleRecord.getTime());
        }

        wellDoneDialog.setImage(imageDescriptor.getUri());
        wellDoneDialog.setDelay(4);
        setStatus(PlayRoomStatus.playing);
    }

    public void next() {
        pack.next();
        getGame().setCurrentRoom(new TimeTrialPlayRoom(getGame(), pack, size));
    }

    @Override
    public void winGame() {
        super.winGame();

        float time = puzzle.getTimePlayed();
        int count = puzzle.getCount();

        SingleRecord singleRecord = SaveManager.getSingleRecord(puzzle.getName(), puzzle.getCount());

        float bestTime = singleRecord == null ? Float.MAX_VALUE : singleRecord.getTime();

        wellDoneDialog.setTime(bestTime, time);

        if (singleRecord == null || singleRecord.getTime() > time) {
            SaveManager.setSingleRecord(new SingleRecord(puzzle.getName(), puzzle.getCount(), time, count));
            SaveManager.save();
        }

        setStatus(PlayRoomStatus.winning);
        puzzle.setBlink(true);
    }

    @Override
    public void loseGame() {
        super.loseGame();
    }

    @Override
    protected void show() {
        super.show();

        menu = new Menu(this);
        puzzle = new Puzzle(this);
        preview = new Preview(this);
        infoBoard = new InfoBoard(this);
        background = new Background(this);
        background.setTextureRegion("puzzle-background", "play.pack");
        pausedDialog = new PausedDialog(this);
        wellDoneDialog = new WellDoneDialog(this);

        wellDoneDialog.setCallback(WellDoneDialog.Action.next, new Runnable() {
            @Override
            public void run() {
                next();
            }
        });

        wellDoneDialog.setCallback(WellDoneDialog.Action.restart, new Runnable() {
            @Override
            public void run() {
                getGame().setCurrentRoom(new TimeTrialPlayRoom(getGame(), pack, size));
            }
        });

        wellDoneDialog.setCallback(WellDoneDialog.Action.quit, new Runnable() {
            @Override
            public void run() {
                getGame().setCurrentRoom(new MenuRoom(getGame()));
            }
        });

        pausedDialog.setCallback(PausedDialog.Action.resume, new Runnable() {
            @Override
            public void run() {
                resumeGame();
            }
        });

        pausedDialog.setCallback(PausedDialog.Action.restart, new Runnable() {
            @Override
            public void run() {
                startGame();
            }
        });

        pausedDialog.setCallback(PausedDialog.Action.quit, new Runnable() {
            @Override
            public void run() {
                getGame().setCurrentRoom(new MenuRoom(getGame()));
            }
        });

        inputProcessor = getGame().inject(TimeTrialPlayRoomInputProcessor.class);
        inputProcessor.setRoom(this);

        inputProcessor.setMenu(menu);
        inputProcessor.setPuzzle(puzzle);
        inputProcessor.setWellDoneDialog(wellDoneDialog);
        inputProcessor.setPausedDialog(pausedDialog);
        inputProcessor.setPreview(preview);

        inputProcessor.setAsGdxInputProcessor();

        startGame();
    }

    @Override
    protected void loadData() {
        super.loadData();

        loadAsset(pack.current().getUri(), Texture.class);
        loadAsset("play.pack", TextureAtlas.class);
    }

    @Override
    protected void updatePlaying(float deltaTime) {
        puzzle.update(deltaTime);
        infoBoard.setTime(puzzle.getTimePlayed());
        infoBoard.setCount(puzzle.getMovesCount());
        preview.update(deltaTime);
    }

    @Override
    protected void updatePaused(float deltaTime) {

    }

    @Override
    protected void updateWinning(float deltaTime) {
        wellDoneDialog.update(deltaTime);

        if (wellDoneDialog.isDelayed()) {
            puzzle.update(deltaTime);
        } else {
            puzzle.setBlink(false);
            setStatus(PlayRoomStatus.won);
        }
    }

    @Override
    protected void updateWon(float deltaTime) {

    }

    @Override
    protected void updateLosing(float deltaTime) {

    }

    @Override
    protected void updateLost(float deltaTime) {

    }

    @Override
    protected void drawPlaying() {
        background.draw();
        preview.draw();
        infoBoard.draw();
        puzzle.draw();
    }

    @Override
    protected void drawPaused() {
        background.draw();
        puzzle.draw();
        infoBoard.draw();
        preview.draw();
        pausedDialog.draw();
    }

    @Override
    protected void drawWinning() {
        background.draw();
        puzzle.draw();
        infoBoard.draw();
        preview.draw();
    }

    @Override
    protected void drawWon() {
        background.draw();
        puzzle.draw();
        infoBoard.draw();
        preview.draw();
        wellDoneDialog.draw();
    }

    @Override
    protected void drawLosing() {

    }

    @Override
    protected void drawLost() {

    }

    @Override
    public void pauseGame() {
        setStatus(PlayRoomStatus.paused);
        puzzle.deselect();
    }

    @Override
    public void resumeGame() {
        setStatus(PlayRoomStatus.playing);
    }

    @Override
    protected void dispose() {
        super.dispose();

        if (puzzle != null) {
            puzzle.dispose();
        }
    }
}
