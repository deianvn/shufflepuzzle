package net.rizov.shufflepuzzle.room.play.challenge.entity;

import com.badlogic.gdx.math.Rectangle;
import net.rizov.gameutils.scene.Room;
import net.rizov.shufflepuzzle.entity.Dialog;

public class ArcadeChallengeWellDoneDialog extends Dialog<ArcadeChallengeWellDoneDialog.Action> {

    public enum Action {next, restart, quit}

    public ArcadeChallengeWellDoneDialog(Room room) {
        super(room);
        setBackground("welldone-arcade-challenge-dialog", "play.pack");
        clearActionBounds();
        addActionBounds(Action.next, new Rectangle(0, 30, 157, 70));
        addActionBounds(Action.restart, new Rectangle(159, 30, 139, 70));
        addActionBounds(Action.quit, new Rectangle(300, 30, 85, 70));
    }

}
