import acm.graphics.*;
import acm.program.GraphicsProgram;

import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.Color;

public class GeometryDash_ShijuGoyal extends GraphicsProgram implements KeyListener {
    // Background image constants
    public static final double kBackgroundPixelWidth = 1212;
    public static final double kBackgroundPixelHeight = 720;

    // Object size constants
    public static final double kStartBoxSize = 150;
    public static final double kStartRectangleSize = 75;
    public static final double kPlayerSize = 100;
    public static final double kPlayerStartLocation = 300;

    // Game Constants
    public static final int[] kObjectHeights = {700, 600, 500, 400, 300, 200};
    public static boolean playing = false;
    public static final int kGravity = 5;
    public static final int kLoopDt = 5;

    public static void main(String[] args) {
        new GeometryDash_ShijuGoyal().start(args);
    }

    public void run() {
        // Tell
        GLabel tip = new GLabel("Please maximize the screen");
        tip.setFont("SansSerif-30");
        add(tip, (getWidth() - tip.getWidth())/2, getHeight()/2);
        while (getWidth() < 1000) pause(100); // Wait until user maximizes screen to continue
        remove(tip);

        // TITLE SCREEN
        // Create background image
        GImage background = new GImage("background.jpeg");
        background.scale(getWidth()/kBackgroundPixelWidth, getHeight()/kBackgroundPixelHeight);
        add(background, (getWidth()-background.getWidth())/2, (getHeight()-background.getHeight())/2);

        // Create Title
        GLabel title = new GLabel("Geometry Dash");
        title.setFont("SansSerif-bold-70");
        add(title, (getWidth()- title.getWidth())/2, kObjectHeights[5]);

        // Create start button
        GCompound startButton = new GCompound();
        GRect startButtonPart1 = new GRect(
                (getWidth()-kStartBoxSize)/2,
                (getHeight()-kStartBoxSize)/2,
                kStartBoxSize,
                kStartBoxSize
        );
        startButtonPart1.setFillColor(Color.CYAN);
        startButtonPart1.setFilled(true);
        startButton.add(startButtonPart1);

        GRect startButtonPart2 = new GRect(
                (getWidth()-kStartBoxSize-kStartRectangleSize/2)/2,
                (getHeight()-kStartBoxSize+kStartRectangleSize)/2,
                kStartBoxSize+kStartRectangleSize/2,
                kStartBoxSize-kStartRectangleSize
        );
        startButtonPart2.setFillColor(Color.GREEN);
        startButtonPart2.setFilled(true);
        startButton.add(startButtonPart2);

        GRect startButtonPart3 = new GRect(
                (getWidth()-kStartBoxSize+kStartRectangleSize)/2,
                (getHeight()-kStartBoxSize-kStartRectangleSize/2)/2,
                kStartBoxSize-kStartRectangleSize,
                kStartBoxSize+kStartRectangleSize/2
        );
        startButtonPart3.setFillColor(Color.GREEN);
        startButtonPart3.setFilled(true);
        startButton.add(startButtonPart3);

        GRect startButtonPart4 = new GRect(
                (getWidth()-kStartBoxSize+kStartRectangleSize)/2,
                (getHeight()-kStartBoxSize+kStartRectangleSize)/2,
                kStartBoxSize-kStartRectangleSize,
                kStartBoxSize-kStartRectangleSize
        );
        startButtonPart4.setColor(Color.GREEN);
        startButtonPart4.setFillColor(Color.GREEN);
        startButtonPart4.setFilled(true);
        startButton.add(startButtonPart4);
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                remove(startButton);
                remove(title);
                playing = true;
            }
        });
        add(startButton);

        // Wait until player hits start
        while (!playing) pause(kLoopDt);

        // Start game - Draw player, platforms, and obstacles
        // Add player
        GRect player = new GRect(kPlayerSize*2, kObjectHeights[1]-kPlayerSize, kPlayerSize, kPlayerSize);
        player.setFillColor(Color.BLUE);
        player.setFilled(true);
        add(player);

        // Add platforms
        GObject[] platforms = {
                new GLine(0, kObjectHeights[0], getWidth(), kObjectHeights[0]),
        };
        for (GObject platform : platforms) {
            add(platform);
        }

        // Add obstacles
        GFillable[] obstacles = {

        };
        for (GFillable obstacle : obstacles) {
            obstacle.setFillColor(Color.RED);
            obstacle.setFilled(true);
            add((GObject) obstacle);
        }

        // Run game loop
        while (true) {
            // Update player's position
            boolean canFall = true;
            for (GObject platform : platforms) {
                if (player.getX()+player.getWidth() >= platform.getX()
                        && player.getX() <= platform.getX()+platform.getWidth()
                        && player.getY() <= platform.getY()+platform.getHeight()
                        && player.getY()+player.getHeight() >= platform.getY()
                ) {
                    canFall = false;
                }
            }
            if (canFall) {
                player.move(0, kGravity);
            }
            pause(kLoopDt);
        }
    }
}

class GTriangle extends GPolygon {
    
}
