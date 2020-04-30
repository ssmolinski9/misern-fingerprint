package com.misern.fingerprint.ui;

import com.misern.fingerprint.actions.contorls.ExitActionHandler;
import com.misern.fingerprint.actions.contorls.OpenFileActionHandler;
import com.misern.fingerprint.actions.contorls.SaveFileActionHandler;
import com.misern.fingerprint.algorithms.CrossingNumber;
import com.misern.fingerprint.algorithms.KMM;
import com.misern.fingerprint.algorithms.MedianFilter;
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

    private final Otsu otsuAlgorithm = new Otsu();
    private final KMM kmmAlgorithm = new KMM();
    private final MedianFilter medianFilter = new MedianFilter();
    private final CrossingNumber cn = new CrossingNumber();

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
        saveFileItem.addActionListener(new SaveFileActionHandler(this));
        exitItem.addActionListener(new ExitActionHandler(this));

        imagePanel.getAutoProcessButton().addActionListener(ev -> autoprocess());
        imagePanel.getOtsuButton().addActionListener(ev -> binarize());
        imagePanel.getFilterButton().addActionListener(ev -> filter());
        imagePanel.getKmmButton().addActionListener(ev -> kmm());
        imagePanel.getRemoveMinutiae().addActionListener(ev -> minutiae());
    }

    public ImagePanel getImagePanel() {
        return imagePanel;
    }

    public void activateButtons() {
        imagePanel.getAutoProcessButton().setEnabled(true);
        imagePanel.getOtsuButton().setEnabled(true);
        imagePanel.getFilterButton().setEnabled(true);
        imagePanel.getKmmButton().setEnabled(true);
        imagePanel.getRemoveMinutiae().setEnabled(true);
    }

    private void deactivateButtons() {
        imagePanel.getAutoProcessButton().setEnabled(false);
        imagePanel.getOtsuButton().setEnabled(false);
        imagePanel.getFilterButton().setEnabled(false);
        imagePanel.getKmmButton().setEnabled(false);
        imagePanel.getRemoveMinutiae().setEnabled(false);
    }

    private void autoprocess() {
        BufferedImage image = imagePanel.getOriginalImage();
        image = otsuAlgorithm.binarize(image);
        imagePanel.setImage(image);

        image = medianFilter.filter(image, 3);
        imagePanel.setImage(image);

        kmmAlgorithm.calculate(image);
        imagePanel.setImage(image);

        image = cn.findMinutiae(image);
        imagePanel.setImage(image);

        deactivateButtons();
    }

    private void binarize() {
        BufferedImage image = imagePanel.getOriginalImage();
        image = otsuAlgorithm.binarize(image);
        imagePanel.setImage(image);

        imagePanel.getOtsuButton().setEnabled(false);
        imagePanel.getAutoProcessButton().setEnabled(false);
    }

    private void filter() {
        BufferedImage image = imagePanel.getOriginalImage();
        image = medianFilter.filter(image, 3);
        imagePanel.setImage(image);

        imagePanel.getFilterButton().setEnabled(false);
        imagePanel.getAutoProcessButton().setEnabled(false);
    }

    private void kmm() {
        BufferedImage image = imagePanel.getOriginalImage();
        kmmAlgorithm.calculate(image);
        imagePanel.setImage(image);

        imagePanel.getKmmButton().setEnabled(false);
        imagePanel.getAutoProcessButton().setEnabled(false);
    }

    private void minutiae() {
        BufferedImage image = imagePanel.getOriginalImage();
        image = cn.findMinutiae(image);
        imagePanel.setImage(image);

        imagePanel.getRemoveMinutiae().setEnabled(false);
        imagePanel.getAutoProcessButton().setEnabled(false);
    }
}
