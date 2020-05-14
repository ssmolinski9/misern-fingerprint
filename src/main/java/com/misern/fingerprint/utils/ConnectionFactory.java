package com.misern.fingerprint.utils;

import java.sql.*;

public class ConnectionFactory {

	static String url = "jdbc:postgresql://localhost:5432/biom";
	static String user = "postgres";
	static String password = "123";

	public static Connection getConnection(){
		try {
			Class.forName("org.postgresql.Driver");
			Connection connection =  DriverManager.getConnection(url, user, password);
			PreparedStatement ps = connection.prepareStatement("CREATE TABLE  IF NOT EXISTS sample (" +
					"   id SERIAL PRIMARY KEY," +
					"   lastTime real NOT NULL," +
					"   measuredTime real NOT NULL," +
					"   userName VARCHAR(50) NOT NULL" +
					");");
			ps.executeUpdate();
			ps.close();
			return connection;
		}catch (SQLException | ClassNotFoundException ex){
			ex.printStackTrace();
		}
		return null;
	}
}
