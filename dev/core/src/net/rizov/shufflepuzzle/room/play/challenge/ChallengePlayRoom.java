package net.rizov.shufflepuzzle.room.play.challenge;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import net.rizov.gameutils.scene.Game;
import net.rizov.shufflepuzzle.entity.Background;
import net.rizov.shufflepuzzle.entity.Dialog;
import net.rizov.shufflepuzzle.room.play.PlayRoom;
import net.rizov.shufflepuzzle.room.play.challenge.entity.InfoBoard;
import net.rizov.shufflepuzzle.room.play.challenge.input.ChallengePlayRoomInputProcessor;
import net.rizov.shufflepuzzle.room.play.entity.Preview;
import net.rizov.shufflepuzzle.room.play.entity.Puzzle;
import net.rizov.shufflepuzzle.room.play.input.PlayRoomInputProcessor;
import net.rizov.shufflepuzzle.room.play.entity.Menu;
import net.rizov.shufflepuzzle.utils.ImageDescriptor;
import net.rizov.shufflepuzzle.utils.Pack;

import java.util.Random;

public abstract class ChallengePlayRoom extends PlayRoom {

    private PlayRoomInputProcessor inputProcessor;

    protected Menu menu;

    protected Puzzle puzzle;

    protected Preview preview;

    protected InfoBoard infoBoard;

    protected Background background;

    protected Dialog pausedDialog;

    protected Dialog wellDoneDialog;

    protected Dialog failedDialog;

    private int puzzlesFinished;

    final private Pack pack;

    final private int size;

    final private int count;

    final private float time;

    private Array<Integer> puzzleIndices;

    public ChallengePlayRoom(Game game, Pack pack, int size, int count, int time) {
        super(game);
        this.pack = pack;
        this.size = size;
        this.count = count;
        this.time = time / 1000f;

        generatePuzzleIndices();
    }

    public void setupPuzzle() {
        ImageDescriptor imageDescriptor = pack.current();
        loadAsset(pack.current().getUri(), Texture.class);
        getGame().getAssetManager().finishLoading();
        Texture texture = getAsset(imageDescriptor.getUri(), Texture.class);
        puzzle.create(imageDescriptor.getShortFileName(), texture, size);
        puzzle.shuffle();
        preview.setTextureRegion(imageDescriptor.getUri());
        preview.cover();
    }

    public void restartGame() {
        puzzlesFinished = 0;
        next();
        startGame();
    }

    public void startGame() {
        setupPuzzle();
        puzzle.clearTimePlayed();
        infoBoard.clearTime();

        wellDoneDialog.setDelay(4);
        failedDialog.setDelay(2);
        setStatus(PlayRoomStatus.playing);
    }

    public void next() {
        if (puzzlesFinished < count) {
            pack.positionTo(puzzleIndices.get(puzzlesFinished++));
            setupPuzzle();
        }
    }

    public boolean isFinished() {
        return puzzle.getTimePlayed() >= time || isWon();
    }

    public boolean isWon() {
        return puzzlesFinished >= count && puzzle.isFinished();
    }

    public boolean isLost() {
        return isFinished() && !isWon();
    }

    @Override
    public void winGame() {
        super.winGame();
        infoBoard.setPuzzlesLeft(0);
        setStatus(PlayRoomStatus.winning);
        puzzle.setBlink(true);
    }

    @Override
    public void loseGame() {
        super.loseGame();
        puzzle.deselect();
        infoBoard.setTimeLeft(0);
        setStatus(PlayRoomStatus.losing);
    }

    @Override
    protected void show() {
        super.show();

        puzzle = new Puzzle(this);
        preview = new Preview(this);
        infoBoard = new InfoBoard(this);
        background = new Background(this);
        background.setTextureRegion("puzzle-background", "play.pack");

        setupMenu();
        setupDialogs();

        inputProcessor = getGame().inject(ChallengePlayRoomInputProcessor.class);
        inputProcessor.setRoom(this);

        inputProcessor.setMenu(menu);
        inputProcessor.setPuzzle(puzzle);
        inputProcessor.setWellDoneDialog(wellDoneDialog);
        inputProcessor.setFailedDialog(failedDialog);
        inputProcessor.setPausedDialog(pausedDialog);
        inputProcessor.setPreview(preview);

        inputProcessor.setAsGdxInputProcessor();

        next();
        startGame();
    }

    protected abstract void setupMenu();

    protected abstract void setupDialogs();

    @Override
    protected void loadData() {
        super.loadData();

        loadAsset("play.pack", TextureAtlas.class);
    }

    @Override
    protected void prepare() {
        super.prepare();
    }

    @Override
    protected void updatePlaying(float deltaTime) {
        puzzle.update(deltaTime);
        infoBoard.setTimeLeft(time - puzzle.getTimePlayed());
        infoBoard.setPuzzlesLeft(count - puzzlesFinished + 1);
        infoBoard.setCount(puzzle.getMovesCount());
        preview.update(deltaTime);

        if (isLost()) {
            loseGame();
        }
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
        failedDialog.update(deltaTime);

        if (!failedDialog.isDelayed()) {
            setStatus(PlayRoomStatus.lost);
        }
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
        background.draw();
        puzzle.draw();
        infoBoard.draw();
        preview.draw();
    }

    @Override
    protected void drawLost() {
        background.draw();
        puzzle.draw();
        infoBoard.draw();
        preview.draw();
        failedDialog.draw();
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

    private void generatePuzzleIndices() {
        puzzleIndices = new Array<Integer>();
        Random random = new Random();
        int i = 0;

        while (i < count) {
            int number = random.nextInt(pack.size());

            if (!puzzleIndices.contains(number, false)) {
                puzzleIndices.add(number);
                i++;
            }
        }
    }

}

