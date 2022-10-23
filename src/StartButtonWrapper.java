package src;

import acm.graphics.*;
import java.awt.Color;
import java.util.ArrayList;

public class StartButtonWrapper {
    private static ArrayList<GObject> startButtonParts;

    public StartButtonWrapper(
            double screenWidth,
            double screenHeight,
            double startBoxSize,
            double startRectangleSize,
            double startTriangleOffset,
            double startTriangleSize
            ) {
        startButtonParts = new ArrayList<>();

        // Add cyan box in black
        startButtonParts.add(
                new GRect(
                        (screenWidth-startBoxSize)/2,
                        (screenHeight-startBoxSize)/2,
                        startBoxSize,
                        startBoxSize
                )
        );
        startButtonParts.get(startButtonParts.size()-1).setColor(Color.BLACK);
        ((GFillable) startButtonParts.get(startButtonParts.size()-1)).setFillColor(Color.CYAN);;
        ((GFillable) startButtonParts.get(startButtonParts.size()-1)).setFilled(true);

        // Add horizontal green rectangle
        startButtonParts.add(
                new GRect(
                        (screenWidth-startBoxSize-startRectangleSize/2)/2,
                        (screenHeight-startBoxSize+startRectangleSize)/2,
                        startBoxSize+startRectangleSize/2,
                        startBoxSize-startRectangleSize
                )
        );
        startButtonParts.get(startButtonParts.size()-1).setColor(Color.BLACK);
        ((GFillable) startButtonParts.get(startButtonParts.size()-1)).setFillColor(Color.GREEN);;
        ((GFillable) startButtonParts.get(startButtonParts.size()-1)).setFilled(true);

        // Add vertical green rectangle
        startButtonParts.add(
                new GRect(
                        (screenWidth-startBoxSize+startRectangleSize)/2,
                        (screenHeight-startBoxSize-startRectangleSize/2)/2,
                        startBoxSize-startRectangleSize,
                        startBoxSize+startRectangleSize/2
                )
        );
        startButtonParts.get(startButtonParts.size()-1).setColor(Color.BLACK);
        ((GFillable) startButtonParts.get(startButtonParts.size()-1)).setFillColor(Color.GREEN);;
        ((GFillable) startButtonParts.get(startButtonParts.size()-1)).setFilled(true);

        // Add green box to remove borders crossing middle
        startButtonParts.add(
                new GRect(
                        (screenWidth-startBoxSize+startRectangleSize)/2,
                        (screenHeight-startBoxSize+startRectangleSize)/2,
                        startBoxSize-startRectangleSize,
                        startBoxSize-startRectangleSize
                )
        );
        startButtonParts.get(startButtonParts.size()-1).setColor(Color.GREEN);
        ((GFillable) startButtonParts.get(startButtonParts.size()-1)).setFillColor(Color.GREEN);;
        ((GFillable) startButtonParts.get(startButtonParts.size()-1)).setFilled(true);

        // Add yellow triangle in middle
        startButtonParts.add(
                new GTriangle(
                        (screenWidth-startBoxSize+startRectangleSize)/2 + startTriangleOffset,
                        (screenHeight-startBoxSize+startRectangleSize*2)/2,
                        startTriangleSize, // Perfectly sized constant to make start button look good
                        3 // rotate 270 degrees clockwise so triangle points right
                )
        );
        startButtonParts.get(startButtonParts.size()-1).setColor(Color.BLACK);
        ((GFillable) startButtonParts.get(startButtonParts.size()-1)).setFillColor(Color.YELLOW);;
        ((GFillable) startButtonParts.get(startButtonParts.size()-1)).setFilled(true);
    }

    /**
     * Method to get start button compound object composed by the different parts
     *
     * @return the GCompound start button
     */
    public GCompound getStartButton() {
        GCompound startButton = new GCompound();

        for (GObject part : startButtonParts) {
            startButton.add(part);
        }

        return startButton;
    }
}
