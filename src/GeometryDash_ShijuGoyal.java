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

        // START GAME
        // Add player
        Player player = new Player(kPlayerStartLocation, kHeightLevels[1]-kObjectSize);
        add(player);

        // Add jump listener
        addKeyListeners(new MyKeyListener());

        // Create obstacles and platform. While the parameters seem like magic numbers, they are just multipliers of
        // game constants (check constructors for specifics).
        Platform[] platforms = {
                new Platform(Platform.PlatformType.RECTANGLE, 0, 0, 40, 0.5),
                new Platform(Platform.PlatformType.RECTANGLE, 40, 1, 50, 0.5)
        };
        for (Platform platform : platforms) {
            add(platform.getObject());
        }

        Obstacle[] obstacles = {
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 30, 0, 1, 0),
                new Obstacle(Obstacle.ObstacleType.LINE, 40, 0, 40, 1)
        };
        for (Obstacle obstacle : obstacles) {
            add(obstacle.getObject());
        }

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

                // Check if player is hitting edges of screen
                if (player.getBottom() >= getHeight() || player.getTop() <= 0) break GameLoop;
            }

            // Update platform and obstacle positions and remove if offscreen
            for (Platform platform : platforms) {
                if (platform.updateLocation()) remove(platform.getObject());
            }
            for (Obstacle obstacle : obstacles) {
                if (obstacle.updateLocation()) remove(obstacle.getObject());
            }

            // Check for collisions
            for (Obstacle obstacle : obstacles) {
                if (obstacle.checkCollision(player)) {
                    break GameLoop;
                }
            }

            player.setGrounded(false);
            for (Platform platform : platforms) {
                if (platform.checkCollision(player)) {
                    player.setLocation(player.getX(), platform.getObject().getY()-kObjectSize/2);
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
