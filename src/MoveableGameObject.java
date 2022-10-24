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

    public double getTop() {
        return object.getY();
    }

    public double getBottom() {
        return object.getY()+object.getHeight();
    }

    public double getLeft() {
        return object.getX();
    }

    public double getRight() {
        return object.getX()+object.getWidth();
    }

    public abstract boolean checkCollision(Player player);
}
