package com.recommendation.travel.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySql {
	public Connection getConnection() {
		Connection con = null;
		String url = "jdbc:mysql://localhost/test";
		String uname = "root";
		String pwd = "root";
		try {

			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = (Connection) DriverManager.getConnection(url, uname, pwd);

			}
			catch (Exception e) {

				e.printStackTrace();
			}
			return con;

		}
		catch (Exception e) {
			System.out.println("Connection to DB could not be established");
			return con;
		}

	}

	public int getRowCount(ResultSet resultSet) {
		if (resultSet == null) {
			return 0;
		}
		try {
			resultSet.last();
			return resultSet.getRow();
		}
		catch (SQLException exp) {
			exp.printStackTrace();
		}
		finally {
			try {
				resultSet.beforeFirst();
			}
			catch (SQLException exp) {
				exp.printStackTrace();
			}
		}
		return 0;
	}

}
