package src;

import acm.graphics.*;

import java.awt.*;

/**
 * Creates triangle, rectangle, and line obstacle objects
 * and defines collision logic for each type of obstacle.
 */
public class Obstacle extends MoveableGameObject {
    public enum ObstacleType {
        RECTANGLE,
        TRIANGLE,
        LINE
    }

    private ObstacleType type;

    /**
     * Creates obstacles with specified properties.
     *
     * @param type whether obstacle is triangle, rectangle, or line
     * @param xMultiplier defines x-coord of position
     * @param yIndex defines y-coord of position using final array of heights
     * @param param1 defines width of rectangle OR size of base and height of
     *               triangle OR x-coord of last point on line
     * @param param2 defines height of rectangle OR angle of rotation of
     *               triangle OR y-coord of last point on line
     */
    public Obstacle(Obstacle.ObstacleType type, double xMultiplier, int yIndex, double param1, double param2) {
        //for all types, xMultiplier specifies x-coord, & yIndex specifies y-coord using heights array
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
            case LINE:
                // For lines, param1 just specifies ending x-coord on line
                // using the multiplier, and param2 specifies ending y-coord

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

    /**
     * Contains collision logic for rectangle,
     * triangle, and line Obstacles.
     *
     * @param player object to check collision with
     * @return whether the Obstacle and Player have collided
     */
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
                //if collides return true
                GTriangle triangle = (GTriangle) getObject();

                //CHECK IF ANY CORNER OF PLAYER IS CONTAINED IN TRIANGLE
                if (triangle.containing(new GPoint(player.getLeft(), player.getBottom())) ||
                        triangle.containing(new GPoint(player.getLeft(), player.getTop())) ||
                        triangle.containing(new GPoint(player.getRight(), player.getTop())) ||
                        triangle.containing(new GPoint(player.getRight(), player.getBottom()))) {
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

}
