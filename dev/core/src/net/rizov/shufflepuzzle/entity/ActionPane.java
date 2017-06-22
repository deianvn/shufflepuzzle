package net.rizov.shufflepuzzle.entity;

import com.badlogic.gdx.math.Rectangle;
import net.rizov.gameutils.scene.Room;
import net.rizov.gameutils.scene.RoomEntity;
import net.rizov.shufflepuzzle.utils.helper.UIHelper;

import java.util.LinkedHashMap;
import java.util.Set;

public abstract class ActionPane<A> extends RoomEntity {

    private LinkedHashMap<A, Rectangle> actionBounds = new LinkedHashMap<A, Rectangle>();

    private LinkedHashMap<A, Runnable> callbacks = new LinkedHashMap<A, Runnable>();

    private A selectedAction;

    public ActionPane(Room<?, ?> room) {
        super(room);
    }

    public ActionPane(RoomEntity parent) {
        super(parent);
    }

    public void setCallback(A a, Runnable callback) {
        callbacks.put(a, callback);
    }

    public void removeCallback(A a) {
        callbacks.remove(a);
    }

    public void clearCallbacks() {
        callbacks.clear();
    }

    protected void addActionBounds(A a, Rectangle bounds) {
        actionBounds.put(a, bounds);
    }

    protected void clearActionBounds() {
        actionBounds.clear();
    }

    public void over(float x, float y) {
        Set<A> keys = actionBounds.keySet();
        Rectangle realBounds = new Rectangle();

        for (A key : keys) {
            Rectangle bounds = actionBounds.get(key);

            if (bounds != null) {
                realBounds.set(getPositionX() + bounds.getX(), getPositionY() + bounds.getY(),
                        bounds.width, bounds.height);

                if (realBounds.contains(x, y)) {
                    selectedAction = key;
                    return;
                }
            }
        }

        selectedAction = null;
    }

    public void click() {
        if (selectedAction != null) {
            UIHelper.click();
            Runnable callback = callbacks.get(selectedAction);

            if (callback != null) {
                selectedAction = null;
                callback.run();
            }
        }
    }

}
