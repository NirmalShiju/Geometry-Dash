package src;

import acm.graphics.GCompound;
import acm.graphics.GObject;
import acm.graphics.GRect;

import java.awt.*;
import java.util.ArrayList;

/**
 * Creates progress bar for user to look
 * at as they progress through level.
 */
public class ProgressBarWrapper {
    private static ArrayList<GRect> progressBarParts;

    /**
     * Creates progress bar using specified attributes.
     *
     * @param screenWidth defines screen width
     * @param yMultiplier defines y-position
     * @param heightMultiplier defines height of bar
     */
    public ProgressBarWrapper(
            double screenWidth,
            double yMultiplier,
            double heightMultiplier
    ) {
        progressBarParts = new ArrayList<>();

        // Add red part of progress bar
        progressBarParts.add(
                new GRect(screenWidth/4,
                        GeometryDash_ShijuGoyal.kHeightLevels[0]+GeometryDash_ShijuGoyal.kObjectSize*yMultiplier,
                        screenWidth/2,
                        GeometryDash_ShijuGoyal.kObjectSize*heightMultiplier
                )
        );
        progressBarParts.get(progressBarParts.size()-1).setColor(Color.BLACK);
        progressBarParts.get(progressBarParts.size()-1).setFillColor(Color.RED);;
        progressBarParts.get(progressBarParts.size()-1).setFilled(true);

        // Add green part of progress bar
        progressBarParts.add(
                new GRect(screenWidth/4,
                        GeometryDash_ShijuGoyal.kHeightLevels[0]+GeometryDash_ShijuGoyal.kObjectSize*yMultiplier,
                        1,
                        GeometryDash_ShijuGoyal.kObjectSize*heightMultiplier
                )
        );
        progressBarParts.get(progressBarParts.size()-1).setColor(Color.BLACK);
        progressBarParts.get(progressBarParts.size()-1).setFillColor(Color.GREEN);;
        progressBarParts.get(progressBarParts.size()-1).setFilled(true);
    }

    /**
     * Method to get start button compound object composed by the different parts
     *
     * @return the GCompound start button
     */
    public GCompound getProgressBar() {
        GCompound progressBar = new GCompound();

        for (GObject part : progressBarParts) {
            progressBar.add(part);
        }

        return progressBar;
    }

    /**
     * Updates progress bar so it shows the
     * percentage of the level that user has completed.
     *
     * @param percent of level that user has completed
     */
    public void updateProgressBar(double percent) {
        double width = progressBarParts.get(0).getWidth();
        progressBarParts.get(1).scale(width*(1-percent)/progressBarParts.get(1).getWidth(), 1);
    }
}
