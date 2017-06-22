package net.rizov.shufflepuzzle.room.play.challenge;

import net.rizov.gameutils.scene.Game;
import net.rizov.shufflepuzzle.room.menu.MenuRoom;
import net.rizov.shufflepuzzle.room.play.challenge.entity.FailedDialog;
import net.rizov.shufflepuzzle.room.play.challenge.entity.SingleChallengeWellDoneDialog;
import net.rizov.shufflepuzzle.room.play.entity.Menu;
import net.rizov.shufflepuzzle.room.play.entity.PausedDialog;
import net.rizov.shufflepuzzle.utils.Pack;

public class SingleChallengePlayRoom extends ChallengePlayRoom {

    public SingleChallengePlayRoom(Game game, Pack pack, int size, int count, int time) {
        super(game, pack, size, count, time);
    }

    @Override
    protected void setupMenu() {
        menu = new Menu(this);
    }

    @Override
    protected void setupDialogs() {

        pausedDialog = new PausedDialog(this);
        wellDoneDialog = new SingleChallengeWellDoneDialog(this);
        failedDialog = new FailedDialog(this);

        wellDoneDialog.setCallback(SingleChallengeWellDoneDialog.Action.restart, new Runnable() {
            @Override
            public void run() {
                restartGame();
            }
        });

        wellDoneDialog.setCallback(SingleChallengeWellDoneDialog.Action.quit, new Runnable() {
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


}
