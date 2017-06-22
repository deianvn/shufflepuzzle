package net.rizov.shufflepuzzle.room.play.input;

import net.rizov.shufflepuzzle.entity.Dialog;
import net.rizov.shufflepuzzle.input.RoomInputProcessor;
import net.rizov.shufflepuzzle.room.play.PlayRoom;
import net.rizov.shufflepuzzle.room.play.entity.Preview;
import net.rizov.shufflepuzzle.room.play.entity.Puzzle;
import net.rizov.shufflepuzzle.room.play.entity.Menu;

public abstract class PlayRoomInputProcessor extends RoomInputProcessor<PlayRoom> {

    private Puzzle puzzle;

    private Dialog wellDoneDialog;

    private Dialog pausedDialog;

    private Dialog failedDialog;

    private Menu menu;

    private Preview preview;

    public void setPuzzle(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public void setWellDoneDialog(Dialog wellDoneDialog) {
        this.wellDoneDialog = wellDoneDialog;
    }

    public Dialog getWellDoneDialog() {
        return wellDoneDialog;
    }

    public void setFailedDialog(Dialog failedDialog) {
        this.failedDialog = failedDialog;
    }

    public Dialog getFailedDialog() {
        return failedDialog;
    }

    public void setPausedDialog(Dialog pausedDialog) {
        this.pausedDialog = pausedDialog;
    }

    public Dialog getPausedDialog() {
        return pausedDialog;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setPreview(Preview preview) {
        this.preview = preview;
    }

    public Preview getPreview() {
        return preview;
    }
}
