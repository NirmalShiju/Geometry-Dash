package src;

import acm.graphics.GFillable;
import acm.graphics.GLine;
import acm.graphics.GRect;

import java.awt.*;

public class Obstacle extends MoveableGameObject {
    public enum ObstacleType {
        RECTANGLE,
        TRIANGLE,
        LINE
    }

    private ObstacleType type;
    //private double xMultiplier;
    //private int yIndex;
    //private double param1;
    //private double param2;

    public Obstacle(Obstacle.ObstacleType type, double xMultiplier, int yIndex, double param1, double param2) {
        //for all types, xMultiplier specifies x-coord, & yIndex specifies y-coord using heights array

        //assigning params to instance vars so they can be accessed by other methods in class
        this.type = type;
        //this.xMultiplier = xMultiplier;
        //this.yIndex = yIndex;
        //this.param1 = param1;
        //this.param2 = param2;

        //actual creation of obstacle object
        switch (this.type) {
            case RECTANGLE:
                // For rectangles, param1 is the horizontal length of the rectangle
                // and param 2 is the vertical length of the rectangle
                object = new GRect(
                        GeometryDash_ShijuGoyal.kLocationConstant*xMultiplier,
                        GeometryDash_ShijuGoyal.kHeightLevels[yIndex],
                        GeometryDash_ShijuGoyal.kObjectSize*param1,
                        GeometryDash_ShijuGoyal.kObjectSize*param2
                );
                ((GFillable) object).setFillColor(Color.RED);
                ((GFillable) object).setFilled(true);
                object.setColor(Color.BLACK);

                break;
            case TRIANGLE:
                // For triangles, param1 is "size" which will make a triangle with both
                // base length and height equal to size. param2 is the number of degrees
                // counterclockwise that the triangle is rotated.
                object = new GTriangle(
                        GeometryDash_ShijuGoyal.kLocationConstant*xMultiplier,
                        GeometryDash_ShijuGoyal.kHeightLevels[yIndex],
                        GeometryDash_ShijuGoyal.kObjectSize*param1,
                        param2
                );
                ((GFillable) object).setFillColor(Color.RED);
                ((GFillable) object).setFilled(true);
                object.setColor(Color.BLACK);

                break;
            case LINE:
                // For lines, param1 just specifies ending x-coord on line
                // using the multiplier, and param2 specifies ending y-coord
                // on line using heights array.
                object = new GLine(
                        GeometryDash_ShijuGoyal.kLocationConstant*xMultiplier,
                        GeometryDash_ShijuGoyal.kHeightLevels[yIndex],
                        GeometryDash_ShijuGoyal.kLocationConstant*param1,
                        GeometryDash_ShijuGoyal.kHeightLevels[(int) param2]
                );
                object.setColor(Color.RED);

                break;
        }
    }

    @Override
    public boolean checkCollision(Player player) {
        // TODO - Dhruv

        //both overlaps need to be true for collision to occur
        boolean verticalOverlap = false;
        boolean horizontalOverlap = false;

        switch (type) {
            case RECTANGLE:
                //if collides return true
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
                System.out.println("Horizontal overlap: " + horizontalOverlap +" Vertical Overlap: " + verticalOverlap);

                return (verticalOverlap && horizontalOverlap);

            case TRIANGLE:
                return false;
            case LINE:
                return false;
        }

        return false;
    }
}
