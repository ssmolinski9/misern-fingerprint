package com.misern.fingerprint.algorithms.ds;

import java.awt.Color;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.util.HashMap;
import java.util.Map;

/**
 * Histogram data structure. Stores histogram's data inside.
 */
public class Histogram {

    /**
     * Names of all possible histogram's channels
     */
    public interface CHANNELS {
        String RED = "R";
        String GREEN = "G";
        String BLUE = "B";
        String GRAYSCALE = "GS";
        String AVERAGE = "A";
    }

    private final double [] redChannel = new double[256];
    private final double [] greenChannel = new double[256];
    private final double [] blueChannel = new double[256];
    private final double [] grayscale = new double[256];
    private final double [] average = new double[256];

    /**
     * Creates histogram based on provided image for all channels.
     * @param image Data source for histogram
     */
    public Histogram(BufferedImage image) {
        for(int w = 0; w < image.getWidth(); w++) {
            for(int h = 0; h < image.getHeight(); h++) {
                Color color = new Color(image.getRGB(w, h));

                redChannel[color.getRed()]++;
                greenChannel[color.getGreen()]++;
                blueChannel[color.getBlue()]++;
                average[(color.getRed() + color.getGreen() + color.getBlue())/3]++;
            }
        }

        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ColorConvertOp op = new ColorConvertOp(cs, null);
        image = op.filter(image, null);

        for(int w = 0; w < image.getWidth(); w++) {
            for(int h = 0; h < image.getHeight(); h++) {
                Color color = new Color(image.getRGB(w, h));
                grayscale[color.getRed()]++;
            }
        }
    }

    /**
     * Get histogram with all channels as map
     * @return Map where key is channel name and value will be values array
     * @see CHANNELS
     */
    public Map<String, double []> getHistogram() {
        HashMap<String, double[]> result = new HashMap<>();
        result.put(CHANNELS.RED, this.redChannel);
        result.put(CHANNELS.GREEN, this.greenChannel);
        result.put(CHANNELS.BLUE, this.blueChannel);
        result.put(CHANNELS.GRAYSCALE, this.grayscale);
        result.put(CHANNELS.AVERAGE, this.average);

        return result;
    }
}
