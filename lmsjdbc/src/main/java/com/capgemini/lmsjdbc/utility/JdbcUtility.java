package com.capgemini.lmsjdbc.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.capgemini.lmsjdbc.exception.LMSException;

public class JdbcUtility {

	private static Connection connection = null;

	public static Connection getConnection() {
		try {
			FileInputStream inputStream = new FileInputStream("db.properties");

			Properties properties = new Properties();
			properties.load(inputStream);

			String driver = properties.getProperty("driver");
			String dburl = properties.getProperty("dburl");

			Class.forName(driver);
			connection = DriverManager.getConnection(dburl);

			return connection;

		} catch (FileNotFoundException e) {
			throw new LMSException("File Not Exists");
		} catch (IOException e) {
			throw new LMSException("Unable to Read The Data From The File");
		} catch (ClassNotFoundException e) {
			throw new LMSException("Class Not Loaded");
		} catch (SQLException e) {
		}
		return connection;
	}
}