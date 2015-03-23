package com.recommendation.travel.database;

import java.net.UnknownHostException;

import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

/**
 * @author 
 *
 */
public class MongoDb {
	
	private MongoClient dbMapper;
	DBCollection coll = null;
	DB db = null;
	
	public MongoCollection airportCollection;
	
	public MongoDb(){
		
		MongoClient client = null;
		try {
			client = new MongoClient(new ServerAddress("54.67.61.143",27017));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("!!!!Error in connection");
			
		}
		DB database = client.getDB("musicDb");
//		String username = "";
//		String pwd = "";
//		char[] password = pwd.toCharArray();
//		boolean auth = database.authenticate(username, password);
		Jongo jongo = new Jongo(database);
		airportCollection = jongo.getCollection("carts");
		
	}

	public MongoClient getDbMapper() {
		return dbMapper;
	}

	public void setDbMapper(MongoClient dbMapper) {
		this.dbMapper = dbMapper;
	}	
	
	
}
