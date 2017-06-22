package net.rizov.shufflepuzzle.room.menu.entity;

import net.rizov.gameutils.scene.RoomEntity;
import net.rizov.gameutils.scene.TextureEntity;

public class Image extends TextureEntity {

    private String id;

    private float vSpace;

    public Image(RoomEntity parent) {
        super(parent);
    }

    public boolean contains(float x, float y) {
        return getBounds().contains(x, y);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setvSpace(float vSpace) {
        this.vSpace = vSpace;
        computeDimensions();
    }

    public float getvSpace() {
        return vSpace;
    }

    @Override
    protected void onRegionChanged() {
        super.onRegionChanged();
    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    protected void computeDimensions() {
        super.computeDimensions();
    }
}
