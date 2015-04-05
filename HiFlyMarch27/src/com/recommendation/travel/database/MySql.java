package com.recommendation.travel.database;

import java.lang.Exception;
import java.sql.*;

public class MySql {
	public Connection getConnection(){
		Connection con = null;

		try{

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MOOC","root","root");

			if(!con.isClosed()){
				return con;
			}
			return con;
		}
		catch(Exception e){
			System.out.println("Connection to DB could not be established");
			return con;
		}

	}


	private int getRowCount(ResultSet resultSet) {
		if (resultSet == null)
		{
			return 0;
		}
		try
		{
			resultSet.last();
			return resultSet.getRow();
		}
		catch (SQLException exp) 
		{
			exp.printStackTrace();
		} 
		finally
		{
			try 
			{
				resultSet.beforeFirst();
			}
			catch (SQLException exp)
			{
				exp.printStackTrace();
			}
		}
		return 0;
	}


}
