package net.rizov.shufflepuzzle.room.play.challenge;

import net.rizov.gameutils.scene.Game;
import net.rizov.shufflepuzzle.room.menu.MenuRoom;
import net.rizov.shufflepuzzle.room.play.challenge.entity.ArcadeChallengeWellDoneDialog;
import net.rizov.shufflepuzzle.room.play.challenge.entity.FailedDialog;
import net.rizov.shufflepuzzle.room.play.entity.Menu;
import net.rizov.shufflepuzzle.room.play.entity.PausedDialog;
import net.rizov.shufflepuzzle.utils.ChallengeDescriptor;
import net.rizov.shufflepuzzle.utils.Pack;
import net.rizov.shufflepuzzle.utils.save.SaveManager;

public class ArcadeChallengePlayRoom extends ChallengePlayRoom {

    private ChallengeDescriptor challengeDescriptor;

    public ArcadeChallengePlayRoom(Game game, Pack pack, ChallengeDescriptor challengeDescriptor) {
        super(game, pack, challengeDescriptor.getSize(), challengeDescriptor.getCount(), challengeDescriptor.getTime() * 1000);
        this.challengeDescriptor = challengeDescriptor;
    }

    @Override
    protected void setupMenu() {
        menu = new Menu(this);
    }

    @Override
    protected void setupDialogs() {

        pausedDialog = new PausedDialog(this);
        wellDoneDialog = new ArcadeChallengeWellDoneDialog(this);
        failedDialog = new FailedDialog(this);

        wellDoneDialog.setCallback(ArcadeChallengeWellDoneDialog.Action.next, new Runnable() {
            @Override
            public void run() {
                MenuRoom menuRoom = new MenuRoom(getGame());
                getGame().setCurrentRoom(menuRoom);
                menuRoom.setStatus(MenuRoom.MenuRoomStatus.arcadeSelectMenu);
            }
        });

        wellDoneDialog.setCallback(ArcadeChallengeWellDoneDialog.Action.restart, new Runnable() {
            @Override
            public void run() {
                restartGame();
            }
        });

        wellDoneDialog.setCallback(ArcadeChallengeWellDoneDialog.Action.quit, new Runnable() {
            @Override
            public void run() {
                getGame().setCurrentRoom(new MenuRoom(getGame()));
            }
        });

        failedDialog.setCallback(FailedDialog.Action.restart, new Runnable() {
            @Override
            public void run() {
                restartGame();
            }
        });

        failedDialog.setCallback(FailedDialog.Action.quit, new Runnable() {
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
                restartGame();
            }
        });

        pausedDialog.setCallback(PausedDialog.Action.quit, new Runnable() {
            @Override
            public void run() {
                getGame().setCurrentRoom(new MenuRoom(getGame()));
            }
        });

    }

    @Override
    public void winGame() {
        super.winGame();
        int level = SaveManager.getArcadeLevel();

        if (level == challengeDescriptor.getLevel()) {
            SaveManager.setArcadeLevel(challengeDescriptor.getLevel() + 1);
            SaveManager.save();
        }

    }

}
