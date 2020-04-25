package com.misern.fingerprint.utils;

import org.apache.commons.io.FilenameUtils;

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

    /**
     * Saves file to specified file
     * @param file File to save image's data
     * @param originalImage Image to save
     */
    public static void saveImage(File file, BufferedImage originalImage) {
        try {
            ImageIO.write(originalImage, FilenameUtils.getExtension(file.getName()), file);
        } catch (IOException ex) {
            System.out.println("Failed to save image: " + ex.getMessage());
        }
    }
}
