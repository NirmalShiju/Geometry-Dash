package src;

import acm.graphics.GFillable;
import acm.graphics.GLine;
import acm.graphics.GRect;

import java.awt.*;

/**
 * Creates platforms that player can jump onto
 * but cannot jump into.
 */
public class Platform extends MoveableGameObject {
    public enum PlatformType {
        RECTANGLE,
        LINE
    }

    private PlatformType type;

    /**
     * Creates platform with specified attributes.
     *
     * @param type specifies if platform is rectangle or line
     * @param xMultiplier specifies x-coord of position
     * @param yIndex specifies y-coord of position
     * @param param1 defines width of rectangle OR
     *               x-coord of last point on line
     * @param param2 defines height of rectangle OR
     *      *        y-coord of last point on line
     */
    public Platform(PlatformType type, double xMultiplier, int yIndex, double param1, double param2) {
        this.type = type;
        switch (this.type) {
            case RECTANGLE:
                object = new GRect(
                        GeometryDash_ShijuGoyal.kLocationConstant*xMultiplier,
                        GeometryDash_ShijuGoyal.kHeightLevels[yIndex],
                        GeometryDash_ShijuGoyal.kLocationConstant*param1,
                        GeometryDash_ShijuGoyal.kObjectSize*param2
                );
                ((GFillable) object).setFillColor(Color.MAGENTA);
                ((GFillable) object).setFilled(true);
                object.setColor(Color.BLACK);

                break;
            case LINE:
                object = new GLine(
                        GeometryDash_ShijuGoyal.kLocationConstant*xMultiplier,
                        GeometryDash_ShijuGoyal.kHeightLevels[yIndex],
                        GeometryDash_ShijuGoyal.kLocationConstant*param1,
                        GeometryDash_ShijuGoyal.kHeightLevels[(int) param2]
                );
                object.setColor(Color.MAGENTA);

                break;
        }
    }

    /**
     * Checks if player has collided with
     * the Platform. Player survives only if
     * it lands on top of platform.
     *
     * @param player defines player to check collision with
     * @return whether collision has occurred
     */
    @Override
    public boolean checkCollision(Player player) {
        // Check if there is no overlap at all
        if (
                player.getRight() < getLeft()
                || player.getLeft() > getRight()
                || player.getBottom() < getTop()
                || player.getTop() > getBottom()
        ) {
            return false;
        }

        // Check if player simply ran into side of platform
        if (!GeometryDash_ShijuGoyal.invertedGravity && player.getBottom()-getTop() > player.getDy()
                || GeometryDash_ShijuGoyal.invertedGravity && player.getTop()-getBottom() < player.getDy()
        ) {
            return true;
        }

        // Make player landings look smooth
        if (!GeometryDash_ShijuGoyal.invertedGravity) {
            player.setLocation(getTop()-GeometryDash_ShijuGoyal.kObjectSize/2);
        } else {
            player.setLocation(getBottom()+GeometryDash_ShijuGoyal.kObjectSize/2);
        }
        player.setFlat();
        player.setGrounded(true);
        return false;
    }
}
