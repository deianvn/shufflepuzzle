package net.rizov.shufflepuzzle.room.menu.entity;

import com.badlogic.gdx.utils.Array;
import net.rizov.gameutils.scene.Room;
import net.rizov.gameutils.scene.RoomEntity;
import net.rizov.gameutils.scene.ScrollableGroupEntity;
import net.rizov.gameutils.scene.TextureEntity;
import net.rizov.shufflepuzzle.entity.AreaBounds;
import net.rizov.shufflepuzzle.entity.Background;
import net.rizov.shufflepuzzle.entity.ButtonBounds;
import net.rizov.shufflepuzzle.utils.ImageButton;
import net.rizov.shufflepuzzle.utils.ImageDescriptor;
import net.rizov.shufflepuzzle.utils.Pack;

import java.util.Iterator;

public class ImageSelector extends ScrollableGroupEntity {

    public enum Area {
        title,
        backButton,
        imageArea
    }

    private TextureEntity title;

    private ImageButton backButton;

    private Background background;

    private Array<AreaBounds<Area>> bounds = new Array<AreaBounds<Area>>();

    private float vMargin;

    private float hMargin;

    private float friction = 8000f;

    private Float currentSpeed;

    private Float currentFriction;

    public ImageSelector(Room<?, ?> room) {
        super(room);
        title = new TextureEntity(room);
        backButton = new ImageButton(room);
        title.setTextureRegion("select-image-title", "menu.pack");
        title.setPositionX(0);
        title.setPositionY(room.getHeight() - title.getHeight());
        backButton.setTextureRegion("back-button", "menu.pack");
        backButton.setPositionX(0);
        backButton.setPositionY(0);
        background = new Background(room);
        background.setTextureRegion("background", "menu.pack");

        bounds.add(new AreaBounds<Area>(Area.title, 0, 730, 480, 70));
        bounds.add(new AreaBounds<Area>(Area.imageArea, 0, 70, 480, 730));
        bounds.add(new ButtonBounds<Area>(Area.backButton, 0, 0, 480, 70));

        computeDimensions();
    }

    @Override
    protected void computeDimensions() {
        setWidth(getRoom().getWidth());
        setHeight(getRoom().getHeight());
    }

    @Override
    public void draw() {
        background.draw();

        for (RoomEntity image : getEntities()) {
            image.draw();
        }

        title.draw();
        backButton.draw();
    }

    public void setPack(Pack pack) {
        Iterator<ImageDescriptor> ie = pack.iterator();

        while (ie.hasNext()) {
            ImageDescriptor desc = ie.next();
            Image image = new Image(this);
            image.setTextureRegion(desc.getShortFileName(), pack.getAtlas());
            image.setId(desc.getShortFileName());
            image.setvSpace(5);
            addEntity(image);
        }

        positionImages();
    }

    public void sethMargin(float hMargin) {
        this.hMargin = hMargin;
    }

    public float gethMargin() {
        return hMargin;
    }

    public void setvMargin(float vMargin) {
        this.vMargin = vMargin;
    }

    public float getvMargin() {
        return vMargin;
    }

    public void scrollTo(float position) {

        if (position > getMax()) {
            position = getMax();
        }

        if (position < getMin()) {
            position = getMin();
        }

        setPositionY(position);

    }

    public void setScroll(float scroll) {

        if (scroll > 0f) {
            scroll = 0f;
        }

        if (scroll > 1f) {
            scroll = 1f;
        }

        scrollTo(getMin() + getRange() * scroll);
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

    public Area click(float x, float y) {
        Area area;

        for (AreaBounds<Area> areaBounds : bounds) {
            area = areaBounds.click(x, y);

            if (area != null) {
                return area;
            }
        }

        return null;
    }

    public String getImageName(float x, float y) {
        if (y < backButton.getHeight() || y > getRoom().getHeight() - title.getHeight()) {
            return null;
        }

        for (RoomEntity entity : getEntities()) {
            Image image = (Image) entity;

            if (image.contains(x, y)) {
                return image.getId();
            }
        }

        return null;
    }

    private void positionImages() {
        Iterator<RoomEntity> i = getEntities().iterator();
        float width = getWidth();
        float rowY = getHeight() - vMargin - title.getHeight();
        float fullHeight = 0;

        while (i.hasNext()) {

            RoomEntity image1 = i.next();
            RoomEntity image2 = i.hasNext() ? i.next() : null;
            fullHeight = Math.max(image1.getHeight(), image2.getHeight());
            float fullWidth = image2 != null ? image1.getWidth() + hMargin + image2.getWidth() : image1.getWidth();
            rowY -= fullHeight;
            image1.setPositionX((width - fullWidth) / 2);
            image1.setPositionY(rowY);

            if (image2 != null) {
                image2.setPositionX(image1.getPositionX() + hMargin + image1.getWidth());
                image2.setPositionY(rowY);
            }

            rowY -= vMargin;
        }

        setMin(0);
        setMax(-rowY + backButton.getHeight() + vMargin);
    }

}
