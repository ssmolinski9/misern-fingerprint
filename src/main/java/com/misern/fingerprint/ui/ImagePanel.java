package com.misern.fingerprint.ui;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

/**
 * Panel where image output will be displayed after all operations.
 */
public class ImagePanel extends JPanel {

    private BufferedImage originalImage;

    private final JButton autoProcessButton = new JButton("Auto process");

    private final JButton otsuButton = new JButton("Otsu binarization");

    private final JButton filterButton = new JButton("Filter");

    private final JButton kmmButton = new JButton("KMM");

    private final JButton removeMinutiae = new JButton("Remove minutiae");

    public ImagePanel() {
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(1, 5));
        buttonPanel.add(autoProcessButton);
        buttonPanel.add(otsuButton);
        buttonPanel.add(filterButton);
        buttonPanel.add(kmmButton);
        buttonPanel.add(removeMinutiae);

        add(buttonPanel, BorderLayout.SOUTH);
    }

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
        repaint();
    }

    public BufferedImage getOriginalImage() {
        return originalImage;
    }

    public JButton getAutoProcessButton() {
        return autoProcessButton;
    }

    public JButton getOtsuButton() {
        return otsuButton;
    }

    public JButton getFilterButton() {
        return filterButton;
    }

    public JButton getKmmButton() {
        return kmmButton;
    }

    public JButton getRemoveMinutiae() {
        return removeMinutiae;
    }
}
