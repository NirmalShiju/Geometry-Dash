package src;

import acm.graphics.*;
import acm.program.GraphicsProgram;

import java.awt.event.KeyEvent;
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
    public static final double kStartTriangleOffset = 10;
    public static final double kStartTriangleSize = 90;
    public static final double kObjectSize = 100;
    public static final double kPlayerStartLocation = 300;

    // Game Constants
    public static final int[] kHeightLevels = {700, 600, 500, 400, 300, 200};
    public static final int kLocationConstant = 100;
    public static int kRotationConstant = 9;
    public static int kJumpConstant = -20;
    public static int kMovementConstant = -10;
    public static final int kGravity = 1;
    public static final int kLoopDt = 8;
    public static boolean playing = false;
    public static boolean jump = false;

    public static void main(String[] args) {
        new GeometryDash_ShijuGoyal().start(args);
    }

    public void run() {
        // Tell
        GLabel request = new GLabel("Please maximize the screen");
        request.setFont("SansSerif-30");
        add(request, (getWidth() - request.getWidth())/2, getHeight()/2);
        while (getWidth() < 1000) pause(100); // Wait until user maximizes screen to continue
        remove(request);

        // TITLE SCREEN
        // Create background image
        GImage background = new GImage("background.jpeg");
        background.scale(getWidth()/kBackgroundPixelWidth, getHeight()/kBackgroundPixelHeight);
        add(background, (getWidth()-background.getWidth())/2, (getHeight()-background.getHeight())/2);

        // Create Title
        GLabel title = new GLabel("Geometry Dash");
        title.setFont("SansSerif-bold-70");
        add(title, (getWidth()- title.getWidth())/2, kHeightLevels[5]);

        // Create start button
        GCompound startButton = new StartButtonWrapper(
                getWidth(),
                getHeight(),
                kStartBoxSize,
                kStartRectangleSize,
                kStartTriangleOffset,
                kStartTriangleSize
        ).getStartButton();
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
        Player player = new Player(kPlayerStartLocation, kHeightLevels[1]-kObjectSize);
        add(player);

        // Add platforms
        GObject[] platforms = {
                new GRect(kLocationConstant*0, kHeightLevels[0], kLocationConstant*40, kObjectSize/2),
                new GRect(kLocationConstant*40, kHeightLevels[1], kLocationConstant*50, kObjectSize/2)
        };
        for (GObject platform : platforms) {
            if (platform instanceof GFillable) {
                ((GFillable) platform).setFillColor(Color.MAGENTA);
                ((GFillable) platform).setFilled(true);
                platform.setColor(Color.BLACK);
            } else {
                platform.setColor(Color.MAGENTA);
            }

            add(platform);
        }

//        Platform[] platforms = {
//                new Platform(Platform.PlatformType.RECTANGLE, 0, 0, 40, 0.5),
//                new Platform(Platform.PlatformType.RECTANGLE, 40, 1, 50, 0.5)
//        };

        // Add obstacles
        GObject[] obstacles = {
            new GTriangle(kLocationConstant*30, kHeightLevels[0], kObjectSize, 0),
            new GLine(kLocationConstant*40, kHeightLevels[0], kLocationConstant*40, kHeightLevels[1]),
        };
        for (GObject obstacle : obstacles) {
            if (obstacle instanceof GFillable) {
                ((GFillable) obstacle).setFillColor(Color.RED);
                ((GFillable) obstacle).setFilled(true);
                obstacle.setColor(Color.BLACK);
            } else {
                obstacle.setColor(Color.RED);
            }

            add(obstacle);
        }

        // Add jump listener
        addKeyListeners(new MyKeyListener());

        // Give user hint on how to move
        GLabel tip = new GLabel("Hit the spacebar to jump!");
        tip.setFont("SansSerif-60");
        add(tip, (getWidth() - tip.getWidth())/2, getHeight()/2);
        System.out.println(tip.isVisible());

        // Run game loop
        GameLoop:
        while (true) {
            // Remove tip once player jumps
            if (jump && tip.isVisible()) {
                remove(tip);
                tip.setVisible(false);
            }

            // Move player
            if (!player.getGrounded()) {
                player.move(0, player.getDy());
                player.rotate(-GeometryDash_ShijuGoyal.kRotationConstant);

                player.setDy(player.getDy()+GeometryDash_ShijuGoyal.kGravity);
            }

            // Update platform and obstacle positions
            for (GObject obstacle : obstacles) {
                obstacle.move(kMovementConstant, 0);
                if (obstacle.getX()+obstacle.getWidth() < 0) {
                    remove(obstacle);
                }
            }
            for (GObject platform : platforms) {
                platform.move(kMovementConstant, 0);
                if (platform.getX()+platform.getWidth() < 0) {
                    remove(platform);
                }
            }

            // Check for collisions
            for (GObject obstacle : obstacles) {
                // tbd - Dhruv
                if (false) {
                    break GameLoop;
                }
            }
            player.setGrounded(false);
            for (GObject platform : platforms) {
                if (player.getRight() >= platform.getX()
                        && player.getLeft() <= platform.getX()+platform.getWidth()
                        && player.getTop() < platform.getY()
                        && player.getBottom() >= platform.getY()
                ) {
                    player.setLocation(player.getX(), platform.getY()-kObjectSize/2);
                    player.setFlat();
                    player.setGrounded(true);
                }
            }

            // Update player state
            if (player.getGrounded()) {
                player.setDy(0);
                if (jump) {
                    player.jump();
                    player.setGrounded(false);
                    jump = false;
                }
            }

            // Pause for game loop
            pause(kLoopDt);
        }
    }

    private class MyKeyListener implements KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                jump = true;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}

        @Override
        public void keyTyped (KeyEvent e) {}
    }
}
