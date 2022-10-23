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

        boolean horizontalOverlap = false;
        boolean verticalOverlap = false;

        double obstacleTop = getObject().getY();
        double obstacleBottom = getObject().getY() + getObject().getHeight();
        double obstacleLeft = getObject().getX();
        double obstacleRight = getObject().getX() + getObject().getWidth();

        if (player.getRight() >= obstacleLeft && player.getLeft() <= obstacleRight) {
            //System.out.println("Horizontal overlap!");
            horizontalOverlap = true;
        }
        if (player.getBottom() >= obstacleTop && player.getTop() <= obstacleBottom) {
            //System.out.println("Vertical overlap!");
            verticalOverlap = true;
        }
        //System.out.println("Horizontal overlap: " + horizontalOverlap +" Vertical Overlap: " + verticalOverlap);

        return (verticalOverlap && horizontalOverlap);

        /*
        return (
                player.getRight() >= object.getX()
                && player.getLeft() <= object.getX()+object.getWidth()
                && player.getTop() < object.getY()
                && player.getBottom() >= object.getY()
        );
        */
    }
}
