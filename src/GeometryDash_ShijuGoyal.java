package src;

import acm.graphics.*;
import acm.program.GraphicsProgram;

import java.awt.*;
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
    public static final int[] kTriangleAngles = {0, 90, 180, 270};
    public static final int kLocationConstant = 100;
    public static final double kEndLineDistance = 220*kLocationConstant - kPlayerStartLocation;
    public static int kRotationConstant = 4;
    public static double kJumpConstant = -20.5;
    public static int kGravityConstant = 1;
    public static int kMovementConstant = -10;
    public static final int kLoopDt = 9;
    public static boolean playing = false;
    public static boolean jump = false;
    public static boolean paused = false;
    public static boolean invertedGravity = false;

    // End of game sequence constants
    public static final int kDeathCircleInitSize = 1;
    public static final int kDeathAnimationDt = 20;
    public static final int kDeathExplosionScale = 2;
    public static final int kTwo = 2;
    public static final int kNumMessageFlashes = 3;
    public static final int kMessageDisplayDt = 500;

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
        GImage background = new GImage("images/background.jpeg");
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

        // Plays until the player wins
        boolean completedGame;
        do {
            removeAll();
            add(background, (getWidth()-background.getWidth())/2, (getHeight()-background.getHeight())/2);
            if (invertedGravity) {
                invertGravity();
                invertedGravity = false;
            }
            paused = false;
            completedGame = play();
        } while (!completedGame);
    }

    public boolean play() {
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
                new Platform(Platform.PlatformType.RECTANGLE, 206, 10, 15, 1),

                //why does auto adjust player not work????
        };
        for (Platform platform : platforms) {
            add(platform.getObject());
        }

        Obstacle[] obstacles = {
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 30, 0, 1, 0),
                //new Obstacle(Obstacle.ObstacleType.RECTANGLE, 35, 3, 6, 1),

                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 40, 0, 0.5, 0),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 40.75, 0, 1, 0),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 55.5, 0, 1, 0),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 56.5, 0, 1, 0),
                new Obstacle(Obstacle.ObstacleType.LINE, 58, 0, 3, 0),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 80, 0, 1, 0),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 81, 0, 1, 0),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 109, 1, 1, 0),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 123, 2, 1, 0),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 153, 10, 1, 2),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 154, 10, 1, 2),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 155, 10, 1, 2),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 163, 2, 1, 0),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 164, 2, 1, 0),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 165, 2, 1, 0),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 166, 2, 1, 0),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 170, 2, 1, 0),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 171, 2, 1, 0),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 172, 2, 1, 0),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 173, 2, 1, 0),
                new Obstacle(Obstacle.ObstacleType.RECTANGLE, 188, 4, 6, 1),
                //new Obstacle(Obstacle.ObstacleType.TRIANGLE, 204.5, 2, 1, 0),
                new Obstacle(Obstacle.ObstacleType.TRIANGLE, 212, 8, 1, 2),


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

        // Add finish line
        FinishLine ending = new FinishLine(220, 10, 1, 10);
        add(ending.getObject());

        // Add progress bar
        ProgressBarWrapper progressBar = new ProgressBarWrapper(getWidth(), 0.125, 0.25);
        add(progressBar.getProgressBar());

        // Give user hint on how to jump
        GLabel tip1 = new GLabel("Hit the spacebar to jump!");
        tip1.setFont("SansSerif-60");
        add(tip1, (getWidth() - tip1.getWidth())/2, getHeight()/2);

        // Give user hint on how to pause
        GLabel tip2 = new GLabel("Hit the p button to pause.");
        tip2.setFont("SansSerif-60");
        add(tip2, (getWidth() - tip2.getWidth())/2, getHeight()/2);
        tip2.setVisible(false);

        // Give user hint on how to resume
        GLabel tip3 = new GLabel("Hit the p button again to resume.");
        tip3.setFont("SansSerif-60");
        add(tip3, (getWidth() - tip3.getWidth())/2, getHeight()/2);
        tip3.setVisible(false);

        // Run game loop
        GameLoop:
        while (true) {
            // Update progress bar
            progressBar.updateProgressBar(ending.getLeft()/(kEndLineDistance-getWidth()));

            // Remove tips once player has followed them and pause/resume logic
            if (jump && tip1.isVisible()) {
                tip1.setVisible(false);
                remove(tip1);
                tip2.setVisible(true);
            } else if (paused) {
                if (tip2.isVisible()) {
                    tip2.setVisible(false);
                    remove(tip2);
                }
                tip3.setVisible(true);

                // Pause and resume logic
                while (paused) {
                    // Ensure jumps during a pause do not work
                    jump = false;
                    pause(kLoopDt);
                }
            }
            tip3.setVisible(false);

            // Update all non-player positions and remove if offscreen
            for (Platform platform : platforms) {
                if (platform.updateLocation()) remove(platform.getObject());
            }
            for (Obstacle obstacle : obstacles) {
                if (obstacle.updateLocation()) remove(obstacle.getObject());
            }
            for (Portal portal : portals) {
                if (portal.updateLocation()) remove(portal.getObject());
            }
            if (ending.updateLocation()) remove(ending.getObject());

            // Check for collisions
            player.setGrounded(false);
            for (Platform platform : platforms) {
                if (platform.checkCollision(player)) {
                    remove(tip1);
                    remove(tip2);
                    deathAnimation(player);
                    return false;
                }
            }

            for (Obstacle obstacle : obstacles) {
                if (obstacle.checkCollision(player)) {
                    remove(tip1);
                    remove(tip2);
                    deathAnimation(player);
                    return false;
                }
            }

            for (Portal portal : portals) {
                if (portal.checkCollision(player)) {
                    switch (portal.getType()) {
                        case GRAVITY:
                            invertedGravity = !invertedGravity;
                            invertGravity();
                            break;
                    }
                }
            }

            // Game completion logic
            if (ending.checkCollision(player)) {
                remove(tip1);
                remove(tip2);
                progressBar.updateProgressBar(0);
                winningAnimation(player);
                return true;
            }

            // Update player state
            if (player.getGrounded()) {
                player.setDy(0);
                if (jump) {
                    player.jump();
                    player.setGrounded(false);
                }
            }
            if (!player.getGrounded()) {
                // Update position
                player.move(0, player.getDy());
                // Rotation animation while falling
                player.rotate(-GeometryDash_ShijuGoyal.kRotationConstant);
                // Updates velocity to allow for gravity-like acceleration
                player.setDy(player.getDy() + GeometryDash_ShijuGoyal.kGravityConstant);

                // Check if player is hitting edges of screen
                if (player.getBottom() >= getHeight() || player.getTop() <= 0) {
                    remove(tip1);
                    remove(tip2);
                    deathAnimation(player);
                    return false;
                }
            }

            // Pause for game loop
            pause(kLoopDt);
        }
    }

    public void deathAnimation (Player player) {
        double currentX = player.getX();
        double currentY = player.getY();
        remove(player);
        //params are x-position, y-position, width, height
        GOval explosion = new GOval(currentX, currentY, kDeathCircleInitSize, kDeathCircleInitSize);
        explosion.setFillColor(Color.GREEN);
        explosion.setColor(Color.BLACK);
        explosion.setFilled(true);
        add(explosion);

        double radius;

        while (explosion.getWidth() < kBackgroundPixelWidth) {
            explosion.scale(kDeathExplosionScale);
            radius = explosion.getWidth()/kTwo;
            pause(kDeathAnimationDt);
            explosion.move(-radius,-radius);
        }
        remove(explosion);

        //
        GLabel lossMessage = new GLabel("Sorry! You lost! Maybe try again?");
        lossMessage.setFont("SansSerif-60");
        add(lossMessage, (getWidth() - lossMessage.getWidth())/2, getHeight()/2);
        for (int i = 0; i < kNumMessageFlashes; i++) {
            lossMessage.setVisible(true);
            pause(kMessageDisplayDt);
            lossMessage.setVisible(false);
            pause(kMessageDisplayDt);
        }
    }

    public void winningAnimation(Player player) {
        GLabel winMessage = new GLabel("Congratulations! You Won!");
        winMessage.setFont("SansSerif-60");
        winMessage.setColor(Color.YELLOW);
        add(winMessage, (getWidth() - winMessage.getWidth())/2, getHeight()/2);
        while (true) {
            player.setVisible(true);
            winMessage.setVisible(true);
            pause(kMessageDisplayDt);
            player.setVisible(false);
            winMessage.setVisible(false);
            pause(kMessageDisplayDt);
        }
    }

    public void invertGravity() {
        kJumpConstant *= -1;
        kRotationConstant *= -1;
        kGravityConstant *= -1;
    }

    private static class MyKeyListener implements KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                jump = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_P) {
                paused = !paused;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                jump = false;
            }
        }

        @Override
        public void keyTyped (KeyEvent e) {}
    }
}
