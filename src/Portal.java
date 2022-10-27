package src;

import acm.graphics.GFillable;
import acm.graphics.GRect;

import java.awt.*;

/**
 * Creates portal that player can jump into.
 */
public class Portal extends MoveableGameObject {
    public enum PortalType {
        GRAVITY
    }

    private PortalType type;

    /**
     * Creates portal based on specified attributes.
     *
     * @param type what portal does (ex. inverts gravity)
     * @param xMultiplier specifies x-coord of position
     * @param yIndex specifies y-coord of position
     * @param widthMultiplier specifies width of portal
     * @param heightMultiplier specifies height of portal
     */
    public Portal(PortalType type, double xMultiplier, int yIndex, double widthMultiplier, double heightMultiplier) {
        this.type = type;
        object = new GRect(
                GeometryDash_ShijuGoyal.kLocationConstant*xMultiplier,
                GeometryDash_ShijuGoyal.kHeightLevels[yIndex],
                GeometryDash_ShijuGoyal.kObjectSize*widthMultiplier,
                GeometryDash_ShijuGoyal.kObjectSize*heightMultiplier
        );
        switch (type) {
            case GRAVITY:
                ((GFillable) object).setFillColor(Color.CYAN);
                break;
        }
        ((GFillable) object).setFilled(true);
        object.setColor(Color.BLACK);
    }

    public PortalType getType() {
        return type;
    }

    /**
     * Checks if player has collided with
     * the Portal
     *
     * @param player defines player to check collision with
     * @return whether collision has occurred
     */
    @Override
    public boolean checkCollision(Player player) {
        return (player.getRight() >= getLeft()
                && player.getRight() < getLeft()-GeometryDash_ShijuGoyal.kMovementConstant);
    }
}
