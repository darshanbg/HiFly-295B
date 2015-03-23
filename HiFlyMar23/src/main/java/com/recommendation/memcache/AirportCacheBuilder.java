package com.recommendation.memcache;

import com.recommendation.internal.dto.AirportEntity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;

//import com.connection.SqlConnectionManager;
import com.mysql.jdbc.Connection;

public class AirportCacheBuilder {

	private static void initializeCache() {
		BufferedReader br = null;
		String filePath = "C:/Users/Darshan/Desktop/295A/airportsData.csv";

		//SqlConnectionManager connectionMgr = null;
		Connection connection = null;
		int count = 0;
		try {
			String sCurrentLine;

			br = new BufferedReader(new FileReader(filePath));

//			connectionMgr = new SqlConnectionManager();
//			connection = connectionMgr.getConnection();
			connection.setAutoCommit(false);
			String query = "insert into Airport_Data (airport_code, airport_type, airport_name, latitude, longitude, city, gps_code, iata_code) values (?,?,?,?,?,?,?,?)";
			PreparedStatement preparedStmt = connection.prepareStatement(query);

			sCurrentLine = br.readLine();
			while ((sCurrentLine = br.readLine()) != null) {
				sCurrentLine = sCurrentLine.replaceAll("\"", "");
				String[] attributeArray = sCurrentLine.split(",");
				int length = attributeArray.length;
				// AirportEntity airportEntity =
				// initializeObject(attributeArray);

				try {
					preparedStmt.setString(1, attributeArray[1]);
					preparedStmt.setString(2, attributeArray[2]);
					if (3 < length && attributeArray[3] != null)
						preparedStmt.setString(3, attributeArray[3]);
					if (4 < length && attributeArray[4] != null)
						preparedStmt.setString(4, attributeArray[4]);
					if (5 < length && attributeArray[5] != null)
						preparedStmt.setString(5, attributeArray[5]);
					if (9 < length && attributeArray[9] != null)
						preparedStmt.setString(6, attributeArray[9]);
					if (12 < length && attributeArray[12] != null)
						preparedStmt.setString(7, attributeArray[12]);
					if (13 < length && attributeArray[13] != null)
						preparedStmt.setString(8, attributeArray[13]);

					preparedStmt.addBatch();
					count++;

					System.out.println(count);
					if (count % 100 == 0) {
						preparedStmt.executeBatch();
						connection.commit();
						System.out.println(count);
					}

				}
				catch (Exception e) {
					e.printStackTrace();
					System.out.println("exception");
					continue;
				}

			}
		}
		catch (Exception e) {
		}
		finally {
			try {
				if (br != null)
					br.close();
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
			finally {
				//connectionMgr.endConnection(connection);
			}
		}
	}

	private AirportEntity initializeObject(String[] attributeArray) {

		AirportEntity airport = new AirportEntity();
		int length = attributeArray.length;

		if (1 < length && attributeArray[1] != null)
			airport.setAirportCode(attributeArray[1]);

		if (2 < length && attributeArray[2] != null)
			airport.setAirportType(attributeArray[2]);

		if (3 < length && attributeArray[3] != null)
			airport.setAirportName(attributeArray[3]);

		if (4 < length && attributeArray[4] != null)
			airport.setLatitude(attributeArray[4]);

		if (5 < length && attributeArray[5] != null)
			airport.setLongitude(attributeArray[5]);

		if (9 < length && attributeArray[9] != null)
			airport.setCity(attributeArray[9]);

		if (12 < length && attributeArray[12] != null)
			airport.setGpsCode(attributeArray[12]);

		if (13 < length && attributeArray[13] != null)
			airport.setIataCode(attributeArray[13]);

		return airport;
	}

	private static void dumpAirportData2Sql(String[] attributeArray) {
		try {

		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void main(String[] args) {
		AirportCacheBuilder.initializeCache();
	}
}
