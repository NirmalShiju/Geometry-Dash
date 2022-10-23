package src;

import acm.graphics.GPoint;
import acm.graphics.GPolygon;

public class GTriangle extends GPolygon {
    private double size;

    public GTriangle(double x, double y, double size, int angleIndex) {
        super(x, y);
        this.size = size;
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

    public boolean containing(GPoint point) {
        double x0 = getX();
        double y0 = getY();
        if (point.getY() >= y0 || point.getY() <= y0 + size) {
            return false;
        }
        //If code reaches here, then GPoint y-coord is within triangle
        //vertical bounding box
        double distance = ((y0 + size) - point.getY())/2;
        if (point.getX() >= x0 - distance &&
                point.getX() <= x0 + distance) {
            return true;
        }
        return false;
    }

    //@Override
    public double getTriSize() {
        return size;
    }
}
