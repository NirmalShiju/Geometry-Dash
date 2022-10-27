package src;

import acm.graphics.GPolygon;

import java.awt.*;

/**
 * Creates player that user needs to control
 * to play and win game.
 */
public class Player extends GPolygon {
    private double dy;
    private boolean grounded;
    private double angle;

    /**
     * Creates player at specified position with
     * fixed shpae, color, and size
     *
     * @param x defines x-coord of position
     * @param y defines y-coord of position
     */
    public Player(double x, double y) {
        super(x, y);
        addVertex(-GeometryDash_ShijuGoyal.kObjectSize/2, -GeometryDash_ShijuGoyal.kObjectSize/2);
        addEdge(GeometryDash_ShijuGoyal.kObjectSize, 0);
        addEdge(0, GeometryDash_ShijuGoyal.kObjectSize);
        addEdge(-GeometryDash_ShijuGoyal.kObjectSize, 0);
        addEdge(0, -GeometryDash_ShijuGoyal.kObjectSize);
        move(0, -GeometryDash_ShijuGoyal.kObjectSize/2);
        dy = 0;
        grounded = false;
        angle = 0;

        setColor(Color.BLACK);
        setFillColor(Color.GREEN);
        setFilled(true);
    }


    public double getBottom() {
        return getY() + GeometryDash_ShijuGoyal.kObjectSize/2;
    }

    public double getTop() {
        return getY() - GeometryDash_ShijuGoyal.kObjectSize/2;
    }

    public double getLeft() {
        return getX() - GeometryDash_ShijuGoyal.kObjectSize/2;
    }

    public double getRight() {
        return getX() + GeometryDash_ShijuGoyal.kObjectSize/2;
    }

    /**
     * Makes player jump.
     */
    public void jump() {
        dy = GeometryDash_ShijuGoyal.kJumpConstant;
    }

    public double getDy() {
        return dy;
    }

    /**
     * Sets new constant for movement in
     * y-direction.
     *
     * @param newDy new movement in y-direction
     */
    public void setDy(double newDy) {
        dy = newDy;
    }

    /**
     * Sets whether player is grounded or not.
     *
     * @param isGrounded whether player is grounded
     */
    public void setGrounded(boolean isGrounded) {
        grounded = isGrounded;
    }

    public boolean getGrounded() {
        return grounded;
    }

    /**
     * Rotates player theta degrees.
     *
     * @param theta is number of degrees
     *              to rotate player
     */
    @Override
    public void rotate(double theta) {
        angle += theta;
        super.rotate(theta);
    }

    /**
     * Sets player such that its base is
     * parallel to the bottom of the screen.
     */
    public void setFlat() {
        rotate(-angle);
    }

    /**
     * Allows you to create player at predefined x
     * and specified height.
     *
     * @param height defines height to set player y-coord
     */
    public void setLocation(double height) {
        setLocation(GeometryDash_ShijuGoyal.kPlayerStartLocation, height);
    }
}

