package src;

import acm.graphics.GObject;

public abstract class MoveableGameObject {
    GObject object = null;

    public boolean updateLocation() {
        object.move(GeometryDash_ShijuGoyal.kMovementConstant, 0);
        return (object.getX()+object.getWidth() < 0);
    }

    public GObject getObject() {
        return object;
    }

    public abstract boolean checkCollision(Player player);
}
