package src;

import acm.graphics.*;
import acm.program.GraphicsProgram;

import javax.sound.sampled.Port;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    public static final int[] kHeightLevels = {800, 700, 600, 500, 400, 300, 200, 150, 100, 50, 0};
    public static final int kLocationConstant = 100;
    public static int kRotationConstant = 9;
    public static int kJumpConstant = -20;
    public static int kGravityConstant = 1;
    public static int kMovementConstant = -10;
    public static final int kLoopDt = 9;
    public static boolean playing = false;
    public static boolean jump = false;
    public static boolean paused = false;

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
                new Platform(Platform.PlatformType.RECTANGLE, 0, 0, 128, 0.5),
                new Platform(Platform.PlatformType.RECTANGLE, 57, 1, 1, 1),
                new Platform(Platform.PlatformType.RECTANGLE, 61, 2, 1, 2),
                new Platform(Platform.PlatformType.RECTANGLE, 65, 3, 1, 3),
                new Platform(Platform.PlatformType.RECTANGLE, 90, 1, 10, 1),
                new Platform(Platform.PlatformType.RECTANGLE, 104, 1, 10, 1),
                new Platform(Platform.PlatformType.RECTANGLE, 118, 2, 10, 2),
                new Platform(Platform.PlatformType.RECTANGLE, 132, 0, 1, 0.25),
                new Platform(Platform.PlatformType.RECTANGLE, 136, 1, 1, 0.25),
                new Platform(Platform.PlatformType.RECTANGLE, 140, 2, 1, 0.25),
                new Platform(Platform.PlatformType.RECTANGLE, 144, 3, 1, 0.25),
                new Platform(Platform.PlatformType.RECTANGLE, 148, 4, 1, 0.25),
                new Platform(Platform.PlatformType.RECTANGLE, 152, 5, 1, 0.25),
                new Platform(Platform.PlatformType.RECTANGLE, 155, 2, 30, 2),
                new Platform(Platform.PlatformType.RECTANGLE, 163.5, 4, 2, 0.25),
                new Platform(Platform.PlatformType.RECTANGLE, 170.5, 4, 2, 0.25),
                new Platform(Platform.PlatformType.RECTANGLE, 185, 1, 12, 1),
                new Platform(Platform.PlatformType.RECTANGLE, 197, 2, 8, 2),

                //new Platform(Platform.PlatformType.RECTANGLE, 40, 1, 50, 0.5)
                //for rectangles, param1 refers to width, param2 refers to height
                //why does auto adjust player not work????
        };
        for (Platform platform : platforms) {
            add(platform.getObject());
        }

        Obstacle[] obstacles = {
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 30, 0, 1, 0),
                //new Obstacle(Obstacle.ObstacleType.RECTANGLE, 35, 3, 6, 1),

                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 40, 0, 0.5, 0),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 40 + (0.5 * (0.5+1)),
                        0, 1, 0),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 55.5, 0, 1, 0),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 56.5, 0, 1, 0),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 80, 0, 1, 0),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 81, 0, 1, 0),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 109, 1, 1, 0),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 123, 2, 1, 0),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 153, 10, 1, 180),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 154, 10, 1, 180),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 155, 10, 1, 180),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 163, 2, 1, 0),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 164, 2, 1, 0),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 165, 2, 1, 0),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 166, 2, 1, 0),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 170, 2, 1, 0),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 171, 2, 1, 0),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 172, 2, 1, 0),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 173, 2, 1, 0),
                new Obstacle(Obstacle.ObstacleType.RECTANGLE, 188, 4, 6, 1),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 204.5, 2, 1, 0),


                //helps need to multiply 0.5 by sum of last object and current object size (referring to 3rd obstacle)

                //new Obstacle(Obstacle.ObstacleType.LINE, 50, 0, 40, 1),
                //new Obstacle(Obstacle.ObstacleType.TRIANGLE, 60, 1, 1, 0),
                //new Obstacle(Obstacle.ObstacleType.TRIANGLE, 60, 1, 1, 0)
                //param1 is triangle size constant for height/base, param2 is triangle rotation (degrees)
        };
        for (Obstacle obstacle : obstacles) {
            add(obstacle.getObject());
        }

        Portal[] portals = {
                new Portal(Portal.PortalType.GRAVITY, 204.5, 5, 1, 3)
        };
        for (Portal portal : portals) {
            add(portal.getObject());
        }

        // Give user hint on how to jump
        GLabel tip1 = new GLabel("Hit the spacebar to jump!");
        tip1.setFont("SansSerif-60");
        add(tip1, (getWidth() - tip1.getWidth())/2, getHeight()/3);
        System.out.println(tip1.isVisible());

        // Give user hint on how to pause
        GLabel tip2 = new GLabel("Hit the p button to pause.");
        tip2.setFont("SansSerif-60");
        add(tip2, (getWidth() - tip2.getWidth())/2, getHeight()/2);
        System.out.println(tip2.isVisible());

        // Give user hint on how to resume
        GLabel tip3 = new GLabel("Hit the p button again to resume.");
        tip3.setFont("SansSerif-60");
        add(tip3, (getWidth() - tip3.getWidth())/2, getHeight()/2);
        tip3.setVisible(false);
        System.out.println(tip3.isVisible());

        // Run game loop
        GameLoop:
        while (true) {
            // Remove tips once player jumps
            if (jump && tip1.isVisible() || paused && tip1.isVisible()) {
                remove(tip1);
                //tip.setVisible(false);
                remove(tip2);
                //tip2.setVisible(false);
            }

            //pause and resume logic
            while(paused) {
                tip3.setVisible(true);
                //makes sure jumps during a pause do not work
                jump = false;
            }
            tip3.setVisible(false);

            // Move player
            if (!player.getGrounded()) {
                //fall due to gravity
                player.move(0, player.getDy());
                //rotation animation while falling
                player.rotate(-GeometryDash_ShijuGoyal.kRotationConstant);
                //updates velocity to allow for gravity-like acceleration
                player.setDy(player.getDy()+GeometryDash_ShijuGoyal.kGravityConstant);
                //makes sure a jump while in the air does not cause the
                //player to jump once it hits the ground again
                jump = false;

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
            for (Portal portal : portals) {
                if (portal.updateLocation()) remove(portal.getObject());
            }

            // Check for collisions
            for (Obstacle obstacle : obstacles) {
                if (obstacle.checkCollision(player)) {
                    System.out.print("collided!");
                    break GameLoop;
                }
            }

            player.setGrounded(false);
            for (Platform platform : platforms) {
                if (platform.checkCollision(player)) {
                    //to make player landings smooth
                    player.setLocation(player.getX(), platform.getObject().getY()-kObjectSize/2);
                    player.setFlat();
                    player.setGrounded(true);
                }
            }

            for (Portal portal : portals) {
                if (portal.checkCollision(player)) {
                    switch (portal.getType()) {
                        case GRAVITY:
                            invertGravity();
                            break;
                    }
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

    public void invertGravity() {
        kJumpConstant *= -1;
        kRotationConstant *= -1;
        kGravityConstant *= -1;
    }

    private class MyKeyListener implements KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                jump = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_P) {
                if (!paused) {
                    paused = true;
                } else {
                    paused = false;
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}

        @Override
        public void keyTyped (KeyEvent e) {}
    }
}
