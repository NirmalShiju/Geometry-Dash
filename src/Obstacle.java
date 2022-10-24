package src;

import acm.graphics.*;

import java.awt.*;

public class Obstacle extends MoveableGameObject {
    public enum ObstacleType {
        RECTANGLE,
        TRIANGLE,
        LINE
    }

    private ObstacleType type;

    public Obstacle(Obstacle.ObstacleType type, double xMultiplier, int yIndex, double param1, double param2) {
        //for all types, xMultiplier specifies x-coord, & yIndex specifies y-coord using heights array

        //assigning params to instance vars so they can be accessed by other methods in class
        this.type = type;

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
                        (int)param2
                );
                ((GFillable) object).setFillColor(Color.RED);
                ((GFillable) object).setFilled(true);
                object.setColor(Color.BLACK);

                break;
            case LINE: //Lines should only be vertical or horizontal
                // For lines, param1 just specifies ending x-coord on line
                // using the multiplier, and param2 specifies ending y-coord
                // on line using heights array.

                /*
                object = new GLine(
                        GeometryDash_ShijuGoyal.kLocationConstant*xMultiplier,
                        GeometryDash_ShijuGoyal.kHeightLevels[yIndex],
                        GeometryDash_ShijuGoyal.kLocationConstant*param1,
                        GeometryDash_ShijuGoyal.kHeightLevels[(int) param2]
                );
                object.setColor(Color.RED);
                */

                //attempting to make lines as just skinny rectangles
                object = new GRect(
                        GeometryDash_ShijuGoyal.kLocationConstant*xMultiplier,
                        GeometryDash_ShijuGoyal.kHeightLevels[yIndex],
                        GeometryDash_ShijuGoyal.kObjectSize*param1,
                        GeometryDash_ShijuGoyal.kObjectSize*param2
                );
                ((GFillable) object).setFillColor(Color.RED);
                ((GFillable) object).setFilled(true);
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
                    horizontalOverlap = true;
                }
                if (player.getBottom() >= obstacleTop && player.getTop() <= obstacleBottom) {
                    verticalOverlap = true;
                }

                return (verticalOverlap && horizontalOverlap);

            case TRIANGLE:
                /*
                // (x0, y0) is the bottom left corner of player
                double x0 = player.getLeft();
                double y0 = player.getBottom();

                double potentialIntersection = getObject().getX() - (getObject().getY() + param1)/2;

                if (potentialIntersection >= player.getLeft() &&
                    potentialIntersection <= player.getLeft() + param1) {
                    horizontalOverlap = true;
                }
                if (potentialIntersection >= (getObject().getX() - param1/2) &&
                    potentialIntersection <= getObject().getX()) {
                    verticalOverlap = true;
                }

                return (verticalOverlap && horizontalOverlap);
                 */

                //CHECK IF ANY CORNER OF PLAYER IS CONTAINED IN TRIANGLE
                if (triContaining(getObject(), new GPoint(player.getLeft(), player.getBottom())) ||
                        triContaining(getObject(), new GPoint(player.getLeft(), player.getTop())) ||
                        triContaining(getObject(), new GPoint(player.getRight(), player.getTop())) ||
                        triContaining(getObject(), new GPoint(player.getRight(), player.getBottom()))) {
                    return true;
                }

                //CHECK IF ANY CORNER OF TRIANGLE IS CONTAINED IN SQUARE
                GPoint triPoint1 = new GPoint(getObject().getX() - getObject().getWidth()/2,getObject().getY());
                GPoint triPoint2 = new GPoint(getObject().getX() + getObject().getWidth()/2,getObject().getY());
                GPoint triPoint3 = new GPoint(getObject().getX(),getObject().getY() + getObject().getWidth());

                if (player.contains(triPoint1) ||
                        player.contains(triPoint2) ||
                        player.contains(triPoint3)) {
                    return true;
                }

                return false;

            case LINE:
                //if collides return true

                obstacleTop = getObject().getY();
                obstacleBottom = getObject().getY() + getObject().getHeight();
                obstacleLeft = getObject().getX();
                obstacleRight = getObject().getX() + getObject().getWidth();

                //if line is vertical, need to not consider its "top tip" to be
                //a collision, so we consider the new "top tip" to be a pixel lower
                if (getObject().getWidth() == 0) {
                    obstacleTop += 1;
                }

                if (player.getRight() >= obstacleLeft && player.getLeft() <= obstacleRight) {
                    horizontalOverlap = true;
                }
                if (player.getBottom() >= obstacleTop && player.getTop() <= obstacleBottom) {
                    verticalOverlap = true;
                }

                return (verticalOverlap && horizontalOverlap);
        }

        return false;
    }

    public boolean triContaining(GObject triangle, GPoint point) {
        double x0 = triangle.getX();
        double y0 = triangle.getY();
        //NEEDS TO BE MINUS BECAUSE MINUS Y GOES UP
        if (point.getY() > y0 || point.getY() <= y0 - triangle.getHeight()) {
            return false;
        }
        //If code reaches here, then GPoint y-coord is within triangle
        //vertical bounding box
        double distance = (point.getY() - (y0 - triangle.getHeight()))/2;
        if (point.getX() >= x0 - distance &&
                point.getX() <= x0 + distance) {
            return true;
        }
        return false;
    }
}
