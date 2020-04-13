package com.misern.fingerprint.ui;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Panel where image output will be displayed after all operations.
 */
public class ImagePanel extends JPanel {

    private BufferedImage originalImage;

    /**
     * Paints image inside panel.
     * Image to render can be set in ImagePanel.setImage(BufferedImage)
     * @param g Graphics to render
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (originalImage != null) {
            Graphics2D g2d = (Graphics2D) g;
            g.clearRect(0, 0, getWidth(), getHeight());

            int x = (this.getWidth() - originalImage.getWidth(null)) / 2;
            int y = (this.getHeight() - originalImage.getHeight(null)) / 2;


            g2d.drawImage(originalImage, x, y, null);
        }
    }

    /**
     * Sets image to render by image panel
     * @param image Loaded image
     */
    public void setImage(BufferedImage image) {
        this.originalImage = image;
    }

    public BufferedImage getOriginalImage() {
        return originalImage;
    }
}
