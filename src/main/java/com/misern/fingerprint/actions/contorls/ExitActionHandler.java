package com.misern.fingerprint.actions.contorls;

import com.misern.fingerprint.ui.Dashboard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Handler for exit program action, just disposes dashboard view and exits application
 */
public class ExitActionHandler implements ActionListener {
    private final Dashboard dashboard;

    public ExitActionHandler(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dashboard.dispose();
        System.exit(0);
    }
}
