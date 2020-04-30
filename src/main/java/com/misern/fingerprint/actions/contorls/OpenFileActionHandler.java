package com.misern.fingerprint.actions.contorls;

import com.misern.fingerprint.ui.Dashboard;
import com.misern.fingerprint.ui.ImagePanel;
import com.misern.fingerprint.utils.FileUtils;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Handler for opening image file action. Loads specified types of images.
 */
public class OpenFileActionHandler implements ActionListener {

    private final Dashboard dashboard;

    public OpenFileActionHandler(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    /**
     * Shows JFileChooser that allows to enter file specified type and loads image to panel in dashboard.
     * Allowed types: png, bmp, jpg, tiff
     * @param e Standard ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser imageOpener = new JFileChooser();
        imageOpener.setFileFilter(new FileNameExtensionFilter("*.png, *.bmp, *.jpg, *.tiff", "png", "tiff", "bmp", "jpg"));

        int returnValue = imageOpener.showDialog(null, "Select image");
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            loadImage(imageOpener.getSelectedFile());
        }

        dashboard.activateButtons();
    }

    private void loadImage(File file) {
        ImagePanel panel = dashboard.getImagePanel();
        panel.setImage(FileUtils.loadImage(file.getPath()));
        panel.repaint();
    }
}
