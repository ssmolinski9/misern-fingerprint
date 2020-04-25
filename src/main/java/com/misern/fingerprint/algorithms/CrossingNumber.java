package com.misern.fingerprint.algorithms;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class CrossingNumber {
    public BufferedImage findMinutiae(BufferedImage orginal) {
        BufferedImage deepCopy = deepCopy(orginal);
        int width = deepCopy.getWidth();
        int height = deepCopy.getHeight();
        double cn = 0;
        int[][] binArray = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (deepCopy.getRGB(i, j) == Color.BLACK.getRGB()) {
                    binArray[i][j] = 1;
                } else binArray[i][j] = 0;
            }
        }

        for (int i = 1; i < width-1; i++) {

            for (int j = 1; j < height-1; j++) {
                cn = 0;
                if (binArray[i][j] == 1) {
                    cn += Math.abs(binArray[i][j + 1] - binArray[i - 1][j + 1]);
                    cn += Math.abs(binArray[i - 1][j + 1] - binArray[i - 1][j]);
                    cn += Math.abs(binArray[i - 1][j] - binArray[i - 1][j - 1]);

                    cn += Math.abs(binArray[i - 1][j - 1] - binArray[i][j - 1]);

                    cn += Math.abs(binArray[i][j - 1] - binArray[i + 1][j - 1]);
                    cn += Math.abs(binArray[i + 1][j - 1] - binArray[i + 1][j]);
                    cn += Math.abs(binArray[i + 1][j] - binArray[i + 1][j + 1]);

                    cn += Math.abs(binArray[i + 1][j + 1] - binArray[i][j + 1]);

                    cn = cn * 0.5;

                    if (cn == 1 && checkIfNotFalse(i, j, deepCopy)) {
                        drawRactangle(i, j, deepCopy, Color.RED);
                        
                    }

                    if(cn == 3 && checkIfNotFalse(i, j, deepCopy)) {
                        drawRactangle(i, j, deepCopy, Color.BLUE);
                    }
                }
            }
        }
        return deepCopy;
    }

    private boolean checkIfNotFalse(int x, int y, BufferedImage deepCopy) {
        int marginWidth = (int) (deepCopy.getWidth() * 0.1);
        int marginHeight = (int) (deepCopy.getHeight() * 0.1);
        if(x - marginWidth > 0 && y - marginHeight > 0 && x + marginWidth < deepCopy.getWidth() && y + marginHeight < deepCopy.getHeight()) {
            return true;
        } else return false;
    }

    private void drawRactangle(int x, int y, BufferedImage image, Color color) {
        int size = 2;
        if ((x - size > 0) && (y - size > 0) && y + size < image.getHeight() && x + size < image.getWidth()) {
            for (int i = -size; i <= size; i++) {
                image.setRGB(x - i, y + size, color.getRGB());
                image.setRGB(x + i, y - size, color.getRGB());
                image.setRGB(x - size, y + i, color.getRGB());
                image.setRGB(x + size, y - i, color.getRGB());

            }
        }
    }

    private BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
