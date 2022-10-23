package src;

import acm.graphics.GFillable;
import acm.graphics.GRect;

import java.awt.*;

public class FinishLine extends MoveableGameObject {

    public FinishLine(double xMultiplier, int yIndex, double widthMultiplier, double heightMultiplier) {
        object = new GRect(
                GeometryDash_ShijuGoyal.kLocationConstant*xMultiplier,
                GeometryDash_ShijuGoyal.kHeightLevels[yIndex],
                GeometryDash_ShijuGoyal.kObjectSize*widthMultiplier,
                GeometryDash_ShijuGoyal.kObjectSize*heightMultiplier
        );

        ((GFillable) object).setFillColor(Color.GREEN);
        ((GFillable) object).setFilled(true);
        object.setColor(Color.BLACK);
    }

    @Override
    public boolean checkCollision(Player player) {
        return (player.getRight() >= object.getX() && player.getLeft() <= object.getX()+object.getWidth());
    }
}
