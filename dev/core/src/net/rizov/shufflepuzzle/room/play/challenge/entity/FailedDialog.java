package net.rizov.shufflepuzzle.room.play.challenge.entity;

import com.badlogic.gdx.math.Rectangle;
import net.rizov.gameutils.scene.Room;
import net.rizov.shufflepuzzle.entity.Dialog;

public class FailedDialog extends Dialog<FailedDialog.Action> {

    public enum Action {restart, quit}

    public FailedDialog(Room room) {
        super(room);
        setBackground("failed-dialog", "play.pack");
        clearActionBounds();
        addActionBounds(FailedDialog.Action.restart, new Rectangle(51, 31, 165, 64));
        addActionBounds(FailedDialog.Action.quit, new Rectangle(233, 31, 103, 64));
    }

}
