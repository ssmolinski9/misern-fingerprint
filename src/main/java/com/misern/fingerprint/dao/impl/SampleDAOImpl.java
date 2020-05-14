package com.misern.fingerprint.dao.impl;

import com.misern.fingerprint.dao.SampleDAO;
import com.misern.fingerprint.model.Sample;
import com.misern.fingerprint.utils.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SampleDAOImpl implements SampleDAO {
	@Override
	public void save(Sample sample) {
		final String SQL = "insert into sample (lasttime, measuredtime, username) values (?,?,?)";
		try (Connection conn = ConnectionFactory.getConnection();
			 PreparedStatement statement = conn.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
			ResultSet rs = statement.getGeneratedKeys();
			statement.setDouble(1, sample.getLastTime());
			statement.setDouble(2, sample.getMeasuredTime());
			statement.setString(3, sample.getUserName());
			statement.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public List<Sample> findByUserName(String userName) {
		final String SQL = "select * from sample where username = ?";
		List<Sample> result = new ArrayList<>();
		try (Connection conn = ConnectionFactory.getConnection();
			 PreparedStatement statement = conn.prepareStatement(SQL)) {
			statement.setString(1, userName);

			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					result.add(Sample.builder()
							.id(rs.getLong(1))
							.lastTime(rs.getDouble(2))
							.measuredTime(rs.getDouble(3))
							.userName(rs.getString(4)).build());
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return result;
	}
}
