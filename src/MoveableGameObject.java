package src;

import acm.graphics.GObject;

/**
 * Defines methods for MoveableGameObject.
 */
public abstract class MoveableGameObject {
    GObject object = null;

    /**
     * Updates position of MoveableGameObject as it
     * moves across screen.
     *
     * @return whether MoveableGameObject has fully
     * passed the screen.
     */
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

    /**
     * Checks if player has collided with
     * the MoveableGameObject
     *
     * @param player defines player to check collision with
     * @return whether collision has occurred
     */
    public abstract boolean checkCollision(Player player);
}
