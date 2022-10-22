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

    public Obstacle(Obstacle.ObstacleType type, double xMultiplier, int yIndex, double param1, double param2) {
        this.type = type;
        switch (this.type) {
            case RECTANGLE:
                object = new GRect(
                        GeometryDash_ShijuGoyal.kLocationConstant*xMultiplier,
                        GeometryDash_ShijuGoyal.kHeightLevels[yIndex],
                        GeometryDash_ShijuGoyal.kLocationConstant*param1,
                        GeometryDash_ShijuGoyal.kObjectSize*param2
                );
                ((GFillable) object).setFillColor(Color.RED);
                ((GFillable) object).setFilled(true);
                object.setColor(Color.BLACK);

                break;
            case TRIANGLE:
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
        return false;
    }
}
