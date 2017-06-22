package net.rizov.shufflepuzzle.room.play.challenge.entity;

import com.badlogic.gdx.math.Rectangle;
import net.rizov.gameutils.scene.Room;
import net.rizov.shufflepuzzle.entity.Dialog;

public class SingleChallengeWellDoneDialog extends Dialog<SingleChallengeWellDoneDialog.Action> {

    public enum Action {restart, quit}

    public SingleChallengeWellDoneDialog(Room room) {
        super(room);
        setBackground("welldone-single-challenge-dialog", "play.pack");
        clearActionBounds();
        addActionBounds(SingleChallengeWellDoneDialog.Action.restart, new Rectangle(44, 32, 170, 62));
        addActionBounds(SingleChallengeWellDoneDialog.Action.quit, new Rectangle(226, 32, 115, 62));
    }

}
