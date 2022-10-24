package src;

import acm.graphics.GPolygon;

import java.awt.*;

public class Player extends GPolygon {
    private double dy;
    private boolean grounded;
    private double angle;
    public Player(double x, double y) {
        super(x, y);
        addVertex(-GeometryDash_ShijuGoyal.kObjectSize/2, -GeometryDash_ShijuGoyal.kObjectSize/2);
        addEdge(GeometryDash_ShijuGoyal.kObjectSize, 0);
        addEdge(0, GeometryDash_ShijuGoyal.kObjectSize);
        addEdge(-GeometryDash_ShijuGoyal.kObjectSize, 0);
        addEdge(0, -GeometryDash_ShijuGoyal.kObjectSize);
        move(-GeometryDash_ShijuGoyal.kObjectSize/2, -GeometryDash_ShijuGoyal.kObjectSize/2);
        //move(0, -GeometryDash_ShijuGoyal.kObjectSize/2);
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

    public void jump() {
        dy = GeometryDash_ShijuGoyal.kJumpConstant;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double newDy) {
        dy = newDy;
    }

    public void setGrounded(boolean yes) {
        grounded = yes;
    }

    public boolean getGrounded() {
        return grounded;
    }

    @Override
    public void rotate(double theta) {
        angle += theta;
        super.rotate(theta);
    }

    public void setFlat() {
        rotate(-angle);
    }
}

