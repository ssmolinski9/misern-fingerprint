package com.misern.fingerprint.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Class with operations on files
 * @see com.misern.fingerprint.actions.contorls.OpenFileActionHandler
 */
public class FileUtils {

    /**
     * Loads file from specified path
     * @param path Full path of image file to load
     * @return Loaded buffered image based on input file or null if something's wrong with the file (or path is incorrect).
     */
    public static BufferedImage loadImage(String path) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(path));
        } catch (IOException ex) {
            System.out.println("Error has occurred during file reading: " + ex.getMessage());
        }

        return image;
    }
}
