package src;

import acm.graphics.GFillable;
import acm.graphics.GRect;

import java.awt.*;

/**
 * Creates FinishLine object that ends game
 * once player collides with it.
 */
public class FinishLine extends MoveableGameObject {

    /**
     * Creates FinishLine with specified attributes.
     *
     * @param xMultiplier specifies x-coord of position
     * @param yIndex specifies y-coord of position
     * @param widthMultiplier specifies width of line
     * @param heightMultiplier specifies height of line
     */
    public FinishLine(double xMultiplier, int yIndex, double widthMultiplier, double heightMultiplier) {
        object = new GRect(
                GeometryDash_ShijuGoyal.kLocationConstant*xMultiplier,
                GeometryDash_ShijuGoyal.kHeightLevels[yIndex],
                GeometryDash_ShijuGoyal.kObjectSize*widthMultiplier,
                GeometryDash_ShijuGoyal.kObjectSize*heightMultiplier
        );

        ((GFillable) object).setFillColor(Color.GREEN);
        ((GFillable) object).setFilled(true);
        object.setColor(Color.BLACK);
    }

    /**
     * Checks if player has collided with
     * the FinishLine
     *
     * @param player defines player to check collision with
     * @return whether collision has occurred
     */
    @Override
    public boolean checkCollision(Player player) {
        return (player.getRight() >= object.getX() && player.getLeft() <= object.getX()+object.getWidth());
    }
}
