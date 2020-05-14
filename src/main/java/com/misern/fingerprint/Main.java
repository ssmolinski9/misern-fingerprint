package com.misern.fingerprint;

import com.misern.fingerprint.dao.impl.SampleDAOImpl;
import com.misern.fingerprint.model.Sample;
import com.misern.fingerprint.ui.Dashboard;

import java.util.List;

public class Main {
    public static void main(String [] args) {
        Sample sample = new Sample();
        sample.setLastTime(1.231233);
        sample.setMeasuredTime(1.231233);
        sample.setUserName("qwe");
        SampleDAOImpl sampleDAO = new SampleDAOImpl();
        sampleDAO.save(sample);
        List<Sample> samples = sampleDAO.findByUserName("qwe");
        new Dashboard();
    }
}
