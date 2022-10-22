package src;

import acm.graphics.GFillable;
import acm.graphics.GLine;
import acm.graphics.GObject;
import acm.graphics.GRect;

import java.awt.*;

public class Platform implements MoveableGameObject {
    public static enum PlatformType {
        RECTANGLE,
        LINE
    }

    private PlatformType type;
    private GObject platform;

    public Platform(PlatformType type, double xMultiplier, int y, double widthMultiplier, double heightMultiplier) {
        this.type = type;
        switch (this.type) {
            case RECTANGLE:
                platform = new GRect(
                        GeometryDash_ShijuGoyal.kLocationConstant*xMultiplier,
                        GeometryDash_ShijuGoyal.kHeightLevels[y],
                        GeometryDash_ShijuGoyal.kLocationConstant*widthMultiplier,
                        GeometryDash_ShijuGoyal.kObjectSize*heightMultiplier
                );
                ((GFillable) platform).setFillColor(Color.MAGENTA);
                ((GFillable) platform).setFilled(true);
                platform.setColor(Color.BLACK);

                break;
            case LINE:
                platform = new GLine(
                        GeometryDash_ShijuGoyal.kLocationConstant*xMultiplier,
                        GeometryDash_ShijuGoyal.kHeightLevels[y],
                        GeometryDash_ShijuGoyal.kLocationConstant*widthMultiplier,
                        GeometryDash_ShijuGoyal.kHeightLevels[y]
                );
                platform.setColor(Color.MAGENTA);

                break;
        }
    }
}
