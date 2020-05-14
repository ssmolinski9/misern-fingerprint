package com.misern.fingerprint.dao;

import com.misern.fingerprint.model.Sample;

import java.util.List;

public interface SampleDAO {
	void save(Sample sample);
	List<Sample> findByUserName(String userName);
}
