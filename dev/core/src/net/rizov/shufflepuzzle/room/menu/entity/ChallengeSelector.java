package net.rizov.shufflepuzzle.room.menu.entity;

import com.badlogic.gdx.utils.Array;
import net.rizov.gameutils.scene.Room;
import net.rizov.gameutils.scene.RoomEntity;
import net.rizov.gameutils.scene.ScrollableGroupEntity;
import net.rizov.gameutils.scene.TextureEntity;
import net.rizov.shufflepuzzle.entity.AreaBounds;
import net.rizov.shufflepuzzle.entity.Background;
import net.rizov.shufflepuzzle.entity.ButtonBounds;
import net.rizov.shufflepuzzle.utils.ChallengeDescriptor;
import net.rizov.shufflepuzzle.utils.save.SaveManager;

public class ChallengeSelector extends ScrollableGroupEntity {

    public enum Area {title, backButton, challenges}

    private static final int[] TIME = {
            120, 360, 600,
            45, 135, 225,
            60, 180, 300,
            30, 90, 150,
            20, 60, 100,

            240, 720, 1200,
            180, 540, 900,
            120, 360, 600,
            90, 270, 450,
            60, 180, 300,

            360, 1080, 1800,
            300, 900, 1500,
            240, 720, 1200,
            180, 540, 900,
            120, 360, 600,

            420, 1260, 2100,
            360, 1080, 1800,
            300, 900, 1500,
            240, 720, 1200,
            180, 540, 900,

            10, 30, 100,
            30, 90, 300,
            60, 180, 600,
            90, 270, 900,

            5, 15, 50,
            15, 45, 150,
            30, 90, 300,
            45, 135, 450,

            80, 240, 500, 600
    };

    private static final int COUNT[] = {
            1, 3, 5,
            1, 3, 5,
            1, 3, 5,
            1, 3, 5,
            1, 3, 5,

            1, 3, 5,
            1, 3, 5,
            1, 3, 5,
            1, 3, 5,
            1, 3, 5,

            1, 3, 5,
            1, 3, 5,
            1, 3, 5,
            1, 3, 5,
            1, 3, 5,

            1, 3, 5,
            1, 3, 5,
            1, 3, 5,
            1, 3, 5,
            1, 3, 5,

            1, 3, 10,
            1, 3, 10,
            1, 3, 10,
            1, 3, 10,

            1, 3, 10,
            1, 3, 10,
            1, 3, 10,
            1, 3, 10,

            20, 20, 20, 20
    };

    private static final int[] HARDNESS = {
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
            4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
            5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
            6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
            3, 3, 3,
            4, 4, 4,
            5, 5, 5,
            6, 6, 6,
            3, 3, 3,
            4, 4, 4,
            5, 5, 5,
            6, 6, 6,
            3, 4, 5, 6
    };

    private float vMargin = 5;

    private Background background;

    private TextureEntity title;

    private TextureEntity backButton;

    private Array<Challenge> entities = new Array<Challenge>();

    private Array<AreaBounds> bounds = new Array<AreaBounds>();

    private float friction = 8000f;

    private Float currentSpeed;

    private Float currentFriction;

    public ChallengeSelector(Room<?, ?> room) {
        super(room);
        computeDimensions();
        createEntities();
        createChallenges();
    }

    public ChallengeSelector(RoomEntity parent) {
        super(parent);
        computeDimensions();
        createEntities();
        createChallenges();
    }

    public Area click(float x, float y) {

        Area area;

        for (AreaBounds<Area> buttonBounds : bounds) {
            area = buttonBounds.click(x, y);

            if (area != null) {
                return area;
            }
        }

        return null;
    }

    public ChallengeDescriptor getChallengeDescriptor(float x, float y) {

        for (Challenge challenge : entities) {
            if (challenge.contains(x, y)) {
                ChallengeDescriptor descriptor = challenge.getDescriptor();

                return descriptor.isLocked() ? null : descriptor;
            }
        }

        return null;

    }

    @Override
    protected void computeDimensions() {
        setWidth(getRoom().getWidth());
        setHeight(getRoom().getHeight());
    }

    @Override
    public void draw() {
        background.draw();

        for (Challenge challenge : entities) {
            challenge.draw();
        }

        title.draw();
        backButton.draw();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (currentSpeed != null && currentFriction != null) {
            scrollTo(getPositionY() - currentSpeed * deltaTime);

            if (currentSpeed > 0) {
                currentSpeed -= deltaTime * currentFriction;

                if (currentSpeed <= 0) {
                    stop();
                }
            } else {
                currentSpeed += deltaTime * currentFriction;

                if (currentSpeed >= 0) {
                    stop();
                }
            }
        }
    }

    public void move(float speed) {
        currentSpeed = speed * 50;
        currentFriction = friction;
    }

    public void stop() {
        currentSpeed = null;
        currentFriction = null;
    }

    private void createEntities() {
        Room<?, ?> room = getRoom();

        bounds.add(new AreaBounds(Area.title, 0, 730, 480, 70));
        bounds.add(new AreaBounds<Area>(Area.challenges, 0, 70, 480, 730));
        bounds.add(new ButtonBounds<Area>(Area.backButton, 0, 0, 480, 70));

        background = new Background(room);
        background.setTextureRegion("background", "menu.pack");
        title = new TextureEntity(room);
        title.setTextureRegion("challenges-title", "menu.pack");
        title.setPositionX(0);
        title.setPositionY(room.getHeight() - title.getHeight());
        backButton = new TextureEntity(getRoom());
        backButton.setTextureRegion("back-button", "menu.pack");
        backButton.setPositionX(0);
        backButton.setPositionY(0);

    }

    private void createChallenges() {
        Room<?, ?> room = getRoom();
        int level = SaveManager.getArcadeLevel();
        int levels = HARDNESS.length;
        float y = backButton.getHeight() + vMargin;
        float lastHeight = 0;
        float currentLevelY = y;

        for (int i = 0; i < levels; i++) {
            ChallengeDescriptor challengeDescriptor = new ChallengeDescriptor();
            challengeDescriptor.setSize(HARDNESS[i]);
            challengeDescriptor.setCount(COUNT[i]);
            challengeDescriptor.setTime(TIME[i]);

            if (level > i) {
                challengeDescriptor.setLocked(false);
                challengeDescriptor.setPassed(true);
            } else if (level == i) {
                challengeDescriptor.setLocked(false);
                challengeDescriptor.setPassed(false);
            } else {
                challengeDescriptor.setLocked(true);
                challengeDescriptor.setPassed(false);
            }

            challengeDescriptor.setLevel(i);

            Challenge challenge = new Challenge(this);
            challenge.setDescriptor(challengeDescriptor);
            challenge.setPositionX((room.getWidth() - challenge.getWidth()) / 2);
            challenge.setPositionY(y);

            if (level == i) {
                currentLevelY = y;
            }

            lastHeight = challenge.getHeight() + vMargin;
            y += lastHeight;

            entities.add(challenge);
        }

        setMin(-y - lastHeight + title.getHeight() - 3 * vMargin + getHeight());
        setMax(0);

        scrollTo(-currentLevelY + backButton.getHeight() + vMargin);
    }

}
