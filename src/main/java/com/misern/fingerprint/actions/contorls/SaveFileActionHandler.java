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
 * Handler for saving image file action. Saves image in specified types.
 */
public class SaveFileActionHandler implements ActionListener {

    private final Dashboard dashboard;

    public SaveFileActionHandler(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    /**
     * Shows JFileChooser that allows to enter file specified type and saves image to provided path.
     * Allowed types: png, bmp, jpg, tiff
     * @param e Standard ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser imageSaver = new JFileChooser();
        imageSaver.setFileFilter(new FileNameExtensionFilter("*.png, *.bmp, *.jpg, *.tiff", "png", "tiff", "bmp", "jpg"));

        int returnValue = imageSaver.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            saveImage(imageSaver.getSelectedFile());
        }
    }

    private void saveImage(File file) {
        ImagePanel panel = dashboard.getImagePanel();
        FileUtils.saveImage(file, panel.getOriginalImage());
    }
}
