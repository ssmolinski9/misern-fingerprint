package com.misern.fingerprint.algorithms;

import com.misern.fingerprint.model.Minutia;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.Iterator;

public class CrossingNumber {

    public BufferedImage findMinutiae(BufferedImage orginal) {
        BufferedImage deepCopy = deepCopy(orginal);
        int width = deepCopy.getWidth();
        int height = deepCopy.getHeight();
        double cn = 0;
        int[][] binArray = new int[width][height];
        ArrayList<Minutia> minutias = new ArrayList<>();

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

                    if (cn == 1 ) {
                        minutias.add(new Minutia(i,j,1));

                    }

                    if(cn == 3) {
                        minutias.add(new Minutia(i,j,3));
                    }
                }
            }
        }
        removeFalseMinutiae(minutias, deepCopy);
        drawMinutiae(deepCopy, minutias);
        return deepCopy;
    }

    private void drawMinutiae(BufferedImage deepCopy, ArrayList<Minutia> minutias) {
        for (Minutia m:minutias) {
            if (m.getType() == 1 ) {
                drawRactangle(m.getX(), m.getY(), deepCopy, Color.RED);

            }

            if(m.getType()  == 3) {
                drawRactangle(m.getX(), m.getY(),  deepCopy, Color.BLUE);
            }
        }
    }

    public void removeFalseMinutiae(ArrayList<Minutia> minutias, BufferedImage deepCopy) {
        Iterator<Minutia> i = minutias.iterator();

        int[][] mask = new int[deepCopy.getWidth()][deepCopy.getHeight()];
        createMask(mask, deepCopy);
        while (i.hasNext()) {
            Minutia m = i.next();
            if(minutias.stream().anyMatch(e -> (Math.abs(e.getX() - m.getX()) < 6) && (Math.abs(e.getY() - m.getY()) < 6) && m != e)) {
                i.remove();
            } else if(mask[m.getX()][m.getY()] == 1) {
                i.remove();
            }

        }



    }

    private void createMask(int[][] mask, BufferedImage image) {

        for(int i = 0; i < image.getWidth(); i++) {
            for(int j = 0; j < image.getHeight(); j++) {
                if(image.getRGB(i,j) == Color.BLACK.getRGB()) {
                    mask[i][j] = 1;
                    break;
                } else mask[i][j] = 1;
            }
        }

        for(int i = image.getWidth()-1; i >= 0; i--) {
            for(int j = image.getHeight()-1; j >= 0; j--) {
                if(image.getRGB(i,j) == Color.BLACK.getRGB()) {
                    mask[i][j] = 1;
                    break;
                } else mask[i][j] = 1;
            }
        }

        for(int i = 0; i < image.getHeight(); i++) {
            for(int j = 0; j < image.getWidth(); j++) {
                if(image.getRGB(j,i) == Color.BLACK.getRGB()) {
                    mask[j][i] = 1;
                    break;
                } else mask[j][i] = 1;
            }
        }

        for(int i = image.getHeight()-1; i >= 0; i--) {
            for(int j = image.getWidth()-1; j >= 0; j--) {
                if(image.getRGB(j,i) == Color.BLACK.getRGB()) {
                    mask[j][i] = 1;
                    break;
                } else mask[j][i] = 1;
            }
        }
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
