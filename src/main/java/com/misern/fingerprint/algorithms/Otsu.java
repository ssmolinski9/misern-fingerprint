package com.misern.fingerprint.algorithms;

import com.misern.fingerprint.algorithms.ds.Histogram;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Otsu {
    /**
     * Binarize provided image with Otsu's method.
     * @param image Image to binarize. Before processing will be converted to grayscale.
     */
    public BufferedImage binarize(BufferedImage image) {
        image = getGrayscaleCopy(image);

        Integer threshold = getOtsuThreshold(image);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color pixelColor = new Color(image.getRGB(x, y));
                int colorValue = pixelColor.getRed();
                image.setRGB(x, y, colorValue > threshold ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
            }
        }

        return image;
    }

    private BufferedImage getGrayscaleCopy(BufferedImage image) {
        BufferedImage grayscale = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < grayscale.getWidth(); x++) {
            for (int y = 0; y < grayscale.getHeight(); y++) {
                Color pixelColor = new Color(image.getRGB(x, y));

                int colorValue = (pixelColor.getRed() + pixelColor.getBlue() + pixelColor.getGreen()) / 3;
                grayscale.setRGB(x, y, new Color(colorValue, colorValue, colorValue).getRGB());
            }
        }

        return grayscale;
    }

    private Integer getOtsuThreshold(BufferedImage image) {
        Histogram histograms = new Histogram(image);
        double[] histogram = histograms.getHistogram().get(Histogram.CHANNELS.RED);

        int imagePixels = image.getHeight() * image.getWidth();

        float sum = 0;
        for (int px = 0; px < 256; px++) {
            sum += px * histogram[px];
        }

        float sumB = 0;
        int wB = 0, wF;

        float varMax = 0;
        int finalThreshold = 0;

        for (int t = 0; t < 256; t++) {
            wB += histogram[t];
            if (wB == 0) continue;

            wF = imagePixels - wB;
            if (wF == 0) break;

            sumB += (float) (t * histogram[t]);

            float mB = sumB / wB;
            float mF = (sum - sumB) / wF;

            float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);

            if (varBetween > varMax) {
                varMax = varBetween;
                finalThreshold = t;
            }
        }

        return finalThreshold;
    }
}
