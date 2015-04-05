/**
 * 
 */
package com.recommendation.travel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.jongo.MongoCollection;

import com.recommendation.travel.database.MongoDb;
import com.recommendation.travel.database.MySql;

/**
 * @author fafu
 *
 */
public class UserDAO {

	public static MongoCollection collection;
	Connection con;
	MySql sqlCon;
	//replace the userName with the session value of the logged in user
	String userName = "roopa";

	
	public UserDAO(){
		//collection = new MongoDb().airportCollection;
		sqlCon = new MySql();
		con = sqlCon.getConnection();
	}
	
	
	/*
	 * To get the carrier maximum travelled by the user
	select carrier, max(maxCount) from(
select  a.carrier, count(1) as maxCount
from transit_details a
where a.bookingId like 'kperry@gmail.com%'
group by a.carrier) b;

To get the top 3 carriers booked by the user
select  a.carrier, count(1) as maxCount
from transit_details a
where a.bookingId like 'kperry@gmail.com%'
group by a.carrier
order by maxCount desc
limit 3;
	 */
	
	public List getCarrierRecommendationData(){
		PreparedStatement selStatement;
		String query = "select carrier, max(maxCount) from(select  a.carrier, count(1) as maxCount from transit_details a where a.bookingId like ? group by a.carrier) b";
		List<String> carrierList = new ArrayList<String>();
		try{
			selStatement = con.prepareStatement(query);
			selStatement.setString(1, userName);
			
			ResultSet rs = selStatement.executeQuery();
			int rowCount = sqlCon.getRowCount(rs);
			if(rowCount > 0){
			while (rs.next()){
				carrierList.add(rs.getString("carrier"));
			}
			}

				return carrierList;
		}
		catch(Exception e){
			e.printStackTrace();
			return carrierList;
		}

	}
	
	/*
	 * To get the max src-destination pair count 
	 select b.pair, max(b.pairCount) from (
select distinct(CONCAT(source, ' ', destination)) as pair, count(1) as pairCount
from transit_details 
where bookingId like 'pwalker%'
group by (CONCAT(source, ' ', destination))) b;

To get the top 3 src-destnations booked by the user
select distinct(CONCAT(source, ' ', destination)) as pair, count(1) as pairCount
from transit_details 
where bookingId like 'pwalker%'
group by (CONCAT(source, ' ', destination))
order by pairCount desc
limit 3

	 */
	
	public List getSrcDestRecommendationData(){
		PreparedStatement selStatement;
		List<String> srcDestList = new ArrayList<String>();

		String query = "select b.pair, max(b.pairCount) from (select distinct(CONCAT(source, ' ', destination)) as pair, count(1) as pairCount from transit_details  where bookingId like ? group by (CONCAT(source, ' ', destination))) b;";
		try{
			selStatement = con.prepareStatement(query);
			selStatement.setString(1, userName);
			
			ResultSet rs = selStatement.executeQuery();
			int rowCount = sqlCon.getRowCount(rs);
			if(rowCount > 0){
				while (rs.next()){
					srcDestList.add(rs.getString(0));
				}
			}
			return srcDestList;
		}
		catch(Exception e){
			e.printStackTrace();
			return srcDestList;
		}

	}
	
	/*
	 * select b.pair, max(b.pairCount) from (
select distinct(CONCAT(source, ' ', destination)) as pair, count(1) as pairCount
from transit_details 
where (TIME_TO_SEC(TIMEDIFF(arrivalTime, 'Oct 23 2012 11:01AM'))/60 < 120) and bookingId like 'pwalker%'
group by (CONCAT(source, ' ', destination))) b;

select distinct(CONCAT(source, ' ', destination)) as pair, count(1) as pairCount
from transit_details 
where (TIME_TO_SEC(TIMEDIFF(arrivalTime, 'Oct 23 2012 11:01AM'))/60 < 120) and bookingId like 'pwalker%'
group by (CONCAT(source, ' ', destination))
order by pair
limit 3

	 */
	public List getSrcDestTimeRecommendationData(){
		PreparedStatement selStatement;
		List<String> srcDestTimeList = new ArrayList<String>();
	
		String query = "select b.pair, max(b.pairCount) from (select distinct(CONCAT(source, ' ', destination)) as pair, count(1) as pairCount from transit_details where (TIME_TO_SEC(TIMEDIFF(arrivalTime, 'Oct 23 2012 11:01AM'))/60 < 120) and bookingId like 'pwalker%' group by (CONCAT(source, ' ', destination))) b;";
		try{
			selStatement = con.prepareStatement(query);
			selStatement.setString(1, userName);
			
			ResultSet rs = selStatement.executeQuery();
			int rowCount = sqlCon.getRowCount(rs);
			if(rowCount > 0){
				while (rs.next()){
					srcDestTimeList.add(rs.getString(0));
				}
			}
			return srcDestTimeList;
		}
		catch(Exception e){
			e.printStackTrace();
			return srcDestTimeList;
		}

	}
	
	
	/**
	 * Insert the given user object in database.
	 * @param newUser a user to be created.
	 */
//	public void insert(User newUser){
//		collection.insert(newUser);
//	}
//
//	/**
//	 * Check if user is authentic or not.
//	 * @param inputEmail
//	 * @param inputPassword
//	 * @return true: if authentic
//	 * 		   false: if not authentic
//	 */
//	public boolean isAuthentic(String inputEmail, String inputPassword) {
//		String query = "{emailId:'" + inputEmail + "'}";
//		User dbDetails = collection.findOne(query).as(User.class);
//		if(dbDetails == null){
//			return false;
//		}
//		
//		System.out.println("database pwd: " + dbDetails.getPassword());
//		
//		if (dbDetails.getPassword().equals(inputPassword)) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//
//	public User getByEmail(String inputEmail) {
//		String query = "{emailId:'" + inputEmail + "'}";
//		User dbDetails = collection.findOne(query).as(User.class);
//		return dbDetails;
//	}
}
