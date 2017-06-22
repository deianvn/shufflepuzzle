package net.rizov.shufflepuzzle.room.play.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import net.rizov.gameutils.scene.RoomEntity;
import net.rizov.gameutils.scene.Room;
import net.rizov.shufflepuzzle.utils.helper.UIHelper;

public class Puzzle extends RoomEntity {

    private String name;

    private int count;

    private int sizeX;

    private int sizeY;

    private int segmentSizeX;

    private int segmentSizeY;

    private int shiftX;

    private int shiftY;

    private boolean blink;

    private float blinkFrequency = 0.15f;

    private float timeToNextBlink;

    private TextureRegion segmentBorder;

    private TextureRegion selectedSegmentBorder;

    private TextureRegion targetSegmentBorder;

    private TextureRegion cover;

    private Array<Texture> decorationTextures = new Array<Texture>();

    private Array<PuzzleSegment> segments = new Array<PuzzleSegment>();

    private PuzzleSegment overSegment;

    private PuzzleSegment selectedSegment;

    private PuzzleSegment targetSegment;

    private int selectedSegmentIndex;

    private int targetSegmentIndex;

    private float selectedSegmentX;

    private float selectedSegmentY;

    private float selectedSegmentRelativeX;

    private float selectedSegmentRelativeY;

    private float timePlayed;

    private int movesCount;

    private Runnable onCreateCallback;

    public Puzzle(Room room) {
        super(room);
        sizeX = 460;
        sizeY = 460;
        setPositionX(10);
        setPositionY(10);
        setWidth(sizeX);
        setHeight(sizeY);
    }

    public void setOnCreateCallback(Runnable onCreateCallback) {
        this.onCreateCallback = onCreateCallback;
    }

    public void create(String name, Texture image, int count) {
        this.name = name;
        overSegment = null;
        selectedSegment = null;
        targetSegment = null;
        segments.clear();
        dispose();
        this.count = count;
        movesCount = 0;

        if (image.getWidth() != sizeX || image.getHeight() != sizeY) {
            resizeImage();
        }

        int regionSizeX = sizeX / count;
        int regionSizeY = sizeY / count;
        int index = 0;

        cover = getRoom().getAsset("play.pack", TextureAtlas.class).findRegion("segment-cover");

        segmentBorder = createDecoration(regionSizeX, regionSizeY, 1, new Color(0, 0, 0, 0.3f));
        selectedSegmentBorder = createDecoration(regionSizeX, regionSizeY, 3,
                new Color(0, 0.5f, 1f, 0.8f));
        targetSegmentBorder = createDecoration(regionSizeX, regionSizeY, 3,
                new Color(0, 1f, 0.5f, 0.8f));

        for (int y = 0; y < count; y++) {
            for (int x = 0; x < count; x++) {

                TextureRegion segmentRegion = new TextureRegion(image, x * regionSizeX,
                        y * regionSizeY, regionSizeX,
                        regionSizeY);

                PuzzleSegment segment = new PuzzleSegment(getRoom());
                segment.setTextureRegion(segmentRegion);
                segment.setIndex(index++);
                segment.setDecorationRegion(segmentBorder);
                segments.add(segment);
            }
        }

        computeDimensions();

        if (onCreateCallback != null) {
            onCreateCallback.run();
        }

    }

    public String getName() {
        return name;
    }

    public void shuffle() {
        int index = 0;

        if (overSegment != null) {
            index = segments.indexOf(overSegment, true);
        }

        do {
            segments.shuffle();
        } while (!isCompletelyShuffled());

        if (overSegment != null) {
            overSegment.setDecorationRegion(segmentBorder);
            overSegment = segments.get(index);
            overSegment.setDecorationRegion(targetSegmentBorder);
        }

        position();
    }

    @Override
    public void draw() {
        for (PuzzleSegment s : segments) {
            if (selectedSegment == s || targetSegment == s) {
                continue;
            }

            s.draw();
        }

        if (targetSegment != null) {
            targetSegment.draw();
        }

        if (selectedSegment != null) {
            selectedSegment.draw();
        }

        if (blink) {
            if (timeToNextBlink < 0) {
                getSpriteBatch().draw(cover, getPositionX(), getPositionY(), getWidth(), getHeight());
            }
        }
    }

    @Override
    public void update(float deltaTime) {

        if (!isFinished()) {
            for (PuzzleSegment segment : segments) {
                segment.update(deltaTime);
            }

            timePlayed += deltaTime;
        }

        if (blink) {
            timeToNextBlink -= deltaTime;

            if (timeToNextBlink < -blinkFrequency) {
                timeToNextBlink = blinkFrequency;
            }
        }
    }

    public void setBlink(boolean blink) {
        this.blink = blink;

        if (blink) {
            timeToNextBlink = blinkFrequency;
        }
    }

    @Override
    protected void computeDimensions() {
        setWidth(sizeX);
        setHeight(sizeY);
    }

    public void clearTimePlayed() {
        timePlayed = 0f;
    }

    public float getTimePlayed() {
        return timePlayed;
    }

    public int getMovesCount() {
        return movesCount;
    }

    public void position() {
        int count = (int) Math.sqrt(segments.size);
        segmentSizeX = sizeX / count;
        segmentSizeY = sizeY / count;
        shiftX = (sizeX - count * segmentSizeX) / 2;
        shiftY = (sizeY - count * segmentSizeY) / 2;

        for (int y = 0; y < count; y++) {
            for (int x = 0; x < count; x++) {
                PuzzleSegment segment = segments.get(y * count + x);
                segment.setPositionX(getPositionX() + shiftX + x * segmentSizeX);
                segment.setPositionY(getPositionY() + shiftY + (count - y - 1) * segmentSizeY);
            }
        }
    }

    public boolean intersects(float x, float y) {
        return x > getPositionX() && x <= getPositionX() + getWidth() &&
                y > getPositionY() && y <= getPositionY() + getHeight();
    }

    public boolean over(float x, float y) {

        int index = 0;

        for (PuzzleSegment segment : segments) {

            if (segment.intersect(x, y) && segment.getIndex() != index) {
                if (overSegment != null) {
                    overSegment.setDecorationRegion(segmentBorder);
                }

                overSegment = segment;
                overSegment.setDecorationRegion(targetSegmentBorder);

                return true;
            }

            index++;
        }

        if (overSegment != null) {
            overSegment.setDecorationRegion(segmentBorder);
            overSegment = null;
        }

        return false;
    }

    public boolean select(float x, float y) {

        if (overSegment != null) {
            selectedSegment = overSegment;
            selectedSegmentIndex = segments.indexOf(selectedSegment, true);
            selectedSegmentX = overSegment.getPositionX();
            selectedSegmentY = overSegment.getPositionY();
            selectedSegmentRelativeX = x - selectedSegmentX;
            selectedSegmentRelativeY = y - selectedSegmentY;
            selectedSegment.setDecorationRegion(selectedSegmentBorder);
            overSegment = null;

            UIHelper.pick();

            return true;
        }

        return false;
    }

    public void deselect() {
        if (selectedSegment != null) {
            selectedSegment.setPositionX(selectedSegmentX);
            selectedSegment.setPositionY(selectedSegmentY);
            selectedSegment.setDecorationRegion(segmentBorder);
            selectedSegment = null;
            UIHelper.drop();
        }

        if (targetSegment != null) {
            targetSegment.setDecorationRegion(segmentBorder);
            targetSegment = null;
        }
    }

    public void target(float x, float y) {
        if (selectedSegment != null) {
            int index = -1;
            boolean intersects = false;

            for (PuzzleSegment segment : segments) {
                index++;

                if (segment == selectedSegment || segment.getIndex() == index) {
                    continue;
                }

                if (segment.intersect(x, y)) {
                    if (targetSegment != null) {
                        targetSegment.setDecorationRegion(segmentBorder);
                    }

                    targetSegment = segment;
                    targetSegment.setDecorationRegion(targetSegmentBorder);
                    targetSegmentIndex = index;
                    intersects = true;
                    break;
                }
            }

            if (!intersects && targetSegment != null) {
                targetSegment.setDecorationRegion(segmentBorder);
                targetSegment = null;
            }
        }
    }

    public void move(float x, float y) {
        if (selectedSegment != null) {
            selectedSegment.setPositionX(x - selectedSegmentRelativeX);
            selectedSegment.setPositionY(y - selectedSegmentRelativeY);
            target(selectedSegment.getPositionX() + segmentSizeX / 2,
                    selectedSegment.getPositionY() + segmentSizeY / 2);
        }
    }

    public boolean drop() {

        if (targetSegment != null) {
            movesCount++;
            segments.swap(targetSegmentIndex, selectedSegmentIndex);
            selectedSegment.setPositionX(targetSegment.getPositionX());
            selectedSegment.setPositionY(targetSegment.getPositionY());
            targetSegment.setPositionX(selectedSegmentX);
            targetSegment.setPositionY(selectedSegmentY);

            if (targetSegment.getIndex() == selectedSegmentIndex) {
                targetSegment.setCoverRegion(cover);
                targetSegment.setDecorationRegion(null);
            } else {
                targetSegment.setDecorationRegion(segmentBorder);
            }

            if (selectedSegment.getIndex() == targetSegmentIndex) {
                selectedSegment.setCoverRegion(cover);
                selectedSegment.setDecorationRegion(null);
            } else {
                selectedSegment.setDecorationRegion(segmentBorder);
            }

            selectedSegment = null;
            targetSegment = null;

            if (isFinished()) {
                for (PuzzleSegment segment : segments) {
                    segment.setCoverRegion(null);
                }
            }

            UIHelper.swap();

            return true;
        }

        deselect();

        return false;
    }

    public boolean isSelected() {
        return selectedSegment != null;
    }

    public boolean isFinished() {
        int index = 0;

        for (PuzzleSegment segment : segments) {
            if (index++ != segment.getIndex()) {
                return false;
            }
        }

        return true;
    }

    public int getCount() {
        return count;
    }

    public int getSegmentSizeX() {
        return segmentSizeX;
    }

    public int getSegmentSizeY() {
        return segmentSizeY;
    }

    public void dispose() {
        for (Texture texture : decorationTextures) {
            texture.dispose();
        }

        decorationTextures.clear();
    }

    private void resizeImage() {

    }

    private TextureRegion createDecoration(int sizeX, int sizeY, int thickness, Color color) {
        Pixmap pixmap = new Pixmap(sizeX, sizeY, Pixmap.Format.RGBA4444);
        pixmap.setColor(color);

        for (int i = 0; i < thickness; i++) {
            pixmap.drawLine(0, i, sizeX - thickness - 1, i);
            pixmap.drawLine(sizeX - i - 1, 0, sizeX - i - 1, sizeY - thickness - 1);
            pixmap.drawLine(sizeX, sizeY - i - 1, thickness, sizeY - i - 1);
            pixmap.drawLine(i, sizeY - 1, i, thickness);
        }

        Texture decorationTexture = new Texture(pixmap);
        decorationTextures.add(decorationTexture);

        return new TextureRegion(decorationTexture, sizeX, sizeY);
    }

    private boolean isCompletelyShuffled() {
        int index = 0;

        for (PuzzleSegment segment : segments) {
            if (index++ == segment.getIndex()) {
                return false;
            }
        }

        return true;
    }

}
