package src;

import acm.graphics.GPolygon;

public class GTriangle extends GPolygon {
    public GTriangle(double x, double y, double size, double angle) {
        super(x, y);
        addVertex(size/2, 0);
        addEdge(-size/2, -size);
        addEdge(-size/2, size);
        addEdge(size, 0);
        rotate(angle);
    }
}
