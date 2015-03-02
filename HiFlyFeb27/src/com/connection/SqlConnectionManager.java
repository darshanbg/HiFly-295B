package com.connection;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class SqlConnectionManager {

	String url = "jdbc:mysql://localhost/test";
	String uname = "";
	String pwd = "";

	public Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = (Connection) DriverManager.getConnection(url, uname, pwd);

		}
		catch (Exception e) {

			e.printStackTrace();
		}
		return conn;

	}

	public void endConnection(Connection conn) {
		try {
			conn.commit();
			conn.close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
