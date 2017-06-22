package net.rizov.shufflepuzzle.room.play.challenge.input;

import net.rizov.shufflepuzzle.room.play.PlayRoom;
import net.rizov.shufflepuzzle.room.play.challenge.ChallengePlayRoom;
import net.rizov.shufflepuzzle.room.play.input.PlayRoomInputProcessor;

public abstract class ChallengePlayRoomInputProcessor extends PlayRoomInputProcessor {

    private ChallengePlayRoom challengePlayRoom;

    @Override
    public void setRoom(PlayRoom room) {
        super.setRoom(room);
        challengePlayRoom = (ChallengePlayRoom) room;
    }

    protected boolean isFinished() {
        return challengePlayRoom.isFinished();
    }

    protected boolean isWon() {
        return challengePlayRoom.isWon();
    }

    protected void next() {
        challengePlayRoom.next();
    }
}
