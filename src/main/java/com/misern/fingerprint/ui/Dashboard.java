package com.misern.fingerprint.ui;

import com.misern.fingerprint.actions.contorls.ExitActionHandler;
import com.misern.fingerprint.actions.contorls.OpenFileActionHandler;
import com.misern.fingerprint.algorithms.KMM;
import com.misern.fingerprint.algorithms.Otsu;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

/**
 * First and main view of application.
 * Includes image panel where output where be displayed.
 */
public class Dashboard extends JFrame {
    private final JMenuItem openFileItem = new JMenuItem("Load image");
    private final JMenuItem saveFileItem = new JMenuItem("Save image");
    private final JMenuItem exitItem = new JMenuItem("Exit");

    private final ImagePanel imagePanel = new ImagePanel();

    /**
     * Creates dashboard frame with menu bar and image panel
     */
    public Dashboard() {
        setLayout(new BorderLayout());
        setTitle("Misern — Fingerprint");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(800, 600));

        createMenuBar();
        createActionListeners();

        add(imagePanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu files = new JMenu("File");

        menuBar.add(files);
        files.add(openFileItem);
        files.add(saveFileItem);
        files.add(exitItem);

        add(menuBar, BorderLayout.NORTH);
    }

    private void createActionListeners() {
        openFileItem.addActionListener(new OpenFileActionHandler(this));
        exitItem.addActionListener(new ExitActionHandler(this));
    }

    public ImagePanel getImagePanel() {
        return imagePanel;
    }

    /**
     * Starts main application process:
     * Otsu's algorithm
     * Median filtering
     * KMM algorithm
     * Additional operations
     */
    public void handleImageChange() {
        Otsu otsuAlgorithm = new Otsu();
        KMM kmmAlgorithm = new KMM();

        if (imagePanel.getOriginalImage() != null) {
            BufferedImage image = imagePanel.getOriginalImage();
            image = otsuAlgorithm.binarize(image);
            imagePanel.setImage(image);

            kmmAlgorithm.calculate(image);
            imagePanel.setImage(image);
        }
    }
}
