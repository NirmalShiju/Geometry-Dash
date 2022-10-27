package src;

import acm.graphics.GObject;
import acm.graphics.GPoint;
import acm.graphics.GPolygon;

/**
 * Class for creating object. Is subclass of GPolygon.
 */
public class GTriangle extends GPolygon {
    private double triSize;
    private int angleIndex;

    /**
     * Creates GTriangle with specific attributes.
     *
     * @param x defines x-coord of position
     * @param y defines y-coord of position
     * @param size defines size of base and height of triangle
     * @param angleIndex defines number of degrees
     *                   counterclockwise that triagnle is rotated
     */
    public GTriangle(double x, double y, double size, int angleIndex) {
        super(x, y);
        this.triSize = size;
        this.angleIndex = angleIndex;
        addVertex(size/2, 0);
        addEdge(-size/2, -size);
        addEdge(-size/2, size);
        addEdge(size, 0);
        rotate(GeometryDash_ShijuGoyal.kTriangleAngles[angleIndex]);
    }

    @Override
    public double getX() {
        return super.getX();
    }

    @Override
    public double getY() {
        return super.getY();
    }

    public double getTriSize() {
        return triSize;
    }

    /**
     * Checks if point is within triangle.
     *
     * @param point defines the point to check
     * @return whether point is within triangle
     */
    public boolean containing(GPoint point) {
        double x0 = this.getX();
        double y0 = this.getY();
        //NEEDS TO BE MINUS BECAUSE MINUS Y GOES UP
        if (point.getY() > y0 || point.getY() <= y0 - this.getHeight()) {
            return false;
        }
        //If code reaches here, then GPoint y-coord is within triangle
        //vertical bounding box
        double distance = (point.getY() - (y0 - this.getHeight()))/2;
        if (point.getX() >= x0 - distance &&
                point.getX() <= x0 + distance) {
            return true;
        }
        return false;
    }

}
