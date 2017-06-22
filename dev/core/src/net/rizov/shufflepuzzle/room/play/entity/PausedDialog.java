package net.rizov.shufflepuzzle.room.play.entity;

import com.badlogic.gdx.math.Rectangle;
import net.rizov.gameutils.scene.Room;

public class PausedDialog extends net.rizov.shufflepuzzle.entity.Dialog<PausedDialog.Action> {

    public enum Action {resume, restart, quit}

    public PausedDialog(Room room) {
        super(room);
        setBackground("paused-dialog", "play.pack");
        clearActionBounds();
        addActionBounds(Action.resume, new Rectangle(12, 30, 134, 67));
        addActionBounds(Action.restart, new Rectangle(147, 30, 140, 67));
        addActionBounds(Action.quit, new Rectangle(290, 30, 87, 67));
    }

}
