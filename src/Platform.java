package src;

import acm.graphics.GFillable;
import acm.graphics.GLine;
import acm.graphics.GRect;

import java.awt.*;

public class Platform extends MoveableGameObject {
    public enum PlatformType {
        RECTANGLE,
        LINE
    }

    private PlatformType type;

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
