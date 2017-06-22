package net.rizov.shufflepuzzle.room.menu.input;

import net.rizov.shufflepuzzle.input.RoomInputProcessor;
import net.rizov.shufflepuzzle.room.menu.MenuRoom;
import net.rizov.shufflepuzzle.room.menu.entity.HardnessSelector;
import net.rizov.shufflepuzzle.room.menu.entity.ImageSelector;
import net.rizov.shufflepuzzle.room.menu.entity.MainMenu;
import net.rizov.shufflepuzzle.room.menu.entity.PuzzlesCountSelector;
import net.rizov.shufflepuzzle.room.menu.entity.TimeSelector;
import net.rizov.shufflepuzzle.room.menu.entity.ChallengeSelector;

public abstract class MenuRoomInputProcessor extends RoomInputProcessor<MenuRoom> {

    private MainMenu mainMenu;

    private HardnessSelector hardnessSelector;

    private ImageSelector imageSelector;

    private TimeSelector timeSelector;

    private PuzzlesCountSelector puzzlesCountSelector;

    private ChallengeSelector challengeSelector;

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }

    public void setHardnessSelector(HardnessSelector hardnessSelector) {
        this.hardnessSelector = hardnessSelector;
    }

    public HardnessSelector getHardnessSelector() {
        return hardnessSelector;
    }

    public void setImageSelector(ImageSelector imageSelector) {
        this.imageSelector = imageSelector;
    }

    public ImageSelector getImageSelector() {
        return imageSelector;
    }

    public void setTimeSelector(TimeSelector timeSelector) {
        this.timeSelector = timeSelector;
    }

    public TimeSelector getTimeSelector() {
        return timeSelector;
    }

    public void setPuzzlesCountSelector(PuzzlesCountSelector puzzlesCountSelector) {
        this.puzzlesCountSelector = puzzlesCountSelector;
    }

    public PuzzlesCountSelector getPuzzlesCountSelector() {
        return puzzlesCountSelector;
    }

    public void setChallengeSelector(ChallengeSelector challengeSelector) {
        this.challengeSelector = challengeSelector;
    }

    public ChallengeSelector getChallengeSelector() {
        return challengeSelector;
    }
}
