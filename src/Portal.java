package src;

import acm.graphics.GFillable;
import acm.graphics.GRect;

import java.awt.*;

public class Portal extends MoveableGameObject {
    public enum PortalType {
        GRAVITY
    }

    private PortalType type;

    public Portal(PortalType type, double xMultiplier, int yIndex, double widthMultiplier, double heightMultiplier) {
        this.type = type;
        object = new GRect(
                GeometryDash_ShijuGoyal.kLocationConstant*xMultiplier,
                GeometryDash_ShijuGoyal.kHeightLevels[yIndex],
                GeometryDash_ShijuGoyal.kObjectSize*widthMultiplier,
                GeometryDash_ShijuGoyal.kObjectSize*heightMultiplier
        );
        switch (type) {
            case GRAVITY:
                ((GFillable) object).setFillColor(Color.CYAN);
                break;
        }
        ((GFillable) object).setFilled(true);
        object.setColor(Color.BLACK);
    }

    public PortalType getType() {
        return type;
    }

    @Override
    public boolean checkCollision(Player player) {
        return (player.getRight() >= getLeft()
                && player.getRight() < getLeft()-GeometryDash_ShijuGoyal.kMovementConstant);
    }
}
