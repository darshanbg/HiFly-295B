package com.recommendation.travel.flyhigh;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TimeZone;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//
//import com.airline.dto.Airline;
//import com.airline.dto.FlightData;
//import com.google.gson.Gson;

/**
 * @author fafu
 *
 */
public class VisitPurpose1 {
	/**
	 * @param args
	 */
	public int satSunCount;
	public List<String> holidayList;
	public int holidayCount;
	public int nonHolidayCount;
	public int totalDays;
	public int finalHolidayCount;
	
	public VisitPurpose1() {
		satSunCount = 0;
		holidayCount = 0;
		nonHolidayCount = 0;
		totalDays = 0;
		finalHolidayCount = 0;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
	
	public String findVisitPurpose(String startDate, String returnDate, Date startTime, Date returnTime){
		
//		int satSunCount = 0;
//		int holidayCount = 0;
//		int nonHolidayCount = 0;
//		int totalDays = 0;
//		int finalHolidayCount = 0;
		
//		System.out.println(" Enter the date you wish to travel in (YYYY-MM-DD) format");
//		Scanner scan1 = new Scanner(System.in);
//		String startDate = scan1.next();
//		System.out.println(" Your start date is " + startDate);
//		
//		
//		System.out.println(" Enter the date you wish to return in (YYYY-MM-DD) format");
//		String returnDate = scan1.next();
//		System.out.println("Your return date is " + returnDate);
//		scan1.close();
		
		DateFormat date = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
		Date startDate1 = new Date();
		Date returnDate1 = new Date();
		try {
			startDate1 = date.parse(startDate);
			
		    returnDate1 = date.parse(returnDate);
		}
		catch (ParseException e) {
		    // Invalid date was entered
			e.printStackTrace();
		}
		
		SimpleDateFormat parser = new SimpleDateFormat("HH:mm");

		try {
			
			/*
			 * TO-DO: Pass the start time from form parameter as selected by the user.
			 * Hardcoded for now
			 */
//			Date startTime = parser.parse("17:00");
//			Date returnTime = parser.parse("18:00");
			
			//minStartTime and maxReturnTime are hardcoded for the logic
			Date minStartTime = parser.parse("16:00");
			Date maxReturnTime = parser.parse("09:00");
			Calendar cal = Calendar.getInstance();
			
		    if (startTime.after(minStartTime)) {
		    	System.out.println(startDate1);
		    	
		        cal.setTime(startDate1);
		        cal.add(Calendar.DATE, 1); //increment the days by 1
		        startDate1 = cal.getTime();
		    	System.out.println(" Printing startDate1 &&&&&&&&&&" + startDate1);
		    }
		    if (returnTime.before(maxReturnTime)){
		    	System.out.println(returnDate1);
		    	
		        cal.setTime(returnDate1);
		        cal.add(Calendar.DATE, -1); //decrement the days by 1
		        returnDate1 = cal.getTime();
		    	System.out.println(" Printing startDate1 &&&&&&&&&&" + returnDate1);
		    	
		    }
		} catch (ParseException e) {
		    // Invalid date was entered
			e.printStackTrace();
		}
		
		
//	    obj.totalDays = obj.calculateTotalDays(startDate1,returnDate1);
//	    obj.satSunCount = obj.findWeekendCount(startDate1,returnDate1);
//	    obj.holidayList = obj.getHolidayListReturnList("2015");
//	    
//	    obj.holidayCount = obj.getHolidayCount(startDate1, returnDate1);
//	    obj.finalHolidayCount = obj.satSunCount + obj.holidayCount;
//	    
//	    obj.nonHolidayCount = obj.totalDays - obj.finalHolidayCount;
		
	    totalDays = calculateTotalDays(startDate1,returnDate1);
	    satSunCount = findWeekendCount(startDate1,returnDate1);
	    holidayList = getHolidayListReturnList("2015");
	    
	    holidayCount = getHolidayCount(startDate1, returnDate1);
	    finalHolidayCount = satSunCount + holidayCount;
	    
	    nonHolidayCount = totalDays - finalHolidayCount;
	    
	    System.out.println("totalDays " + totalDays);
	    System.out.println("satSunCount " + satSunCount);
	    System.out.println("holidayCount " + holidayCount);
	    System.out.println("finalHolidayCount " + finalHolidayCount);
	    System.out.println("nonHolidayCount " + nonHolidayCount);
	    
	    
	    if(nonHolidayCount > finalHolidayCount){
	    	System.out.println("Business trip");
	    	return "BUSINESS";
	    }
	    else if(nonHolidayCount < finalHolidayCount){
	    	System.out.println("Casual trip. Enjoy your vacation!");
	    	return "CASUAL";
	    }
	    else{
	    	System.out.println("Can be a business trip or a vacation....");
	    	VisitPurpose2 vis = new VisitPurpose2();
	    	String purpose2 = vis.findVisitPurpose2(startDate, returnDate);
	    	return purpose2;
	    }

		
	}
			    
	    
	    //TO-DO: nonHolidayCount = number of days between start and return dates - (satSunCount + holidayCount)
	    		
	
	
	/*
	 * Find the total number of days between start and return dates - including both
	 */
	private int calculateTotalDays(Date startDate, Date returnDate){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    Date sDate = null;
	    Date rDate = null;
	    
	    int totalDaysLocal = 0;
	    
	    try {
	        
	        sDate = startDate;
	        rDate = returnDate;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
	    calendar.setTime(sDate);
	    System.out.println("Day of the week = "
	            + (calendar.get(Calendar.DAY_OF_WEEK)));
	    
	  while(sDate.before(rDate) || sDate.equals(rDate)){ 
		  totalDaysLocal++;
		  calendar.add(Calendar.DATE, 1);
		  sDate = calendar.getTime();
	  }
	  return totalDaysLocal;

	}
	
	
	/*
	 * To check if Saturday and/or Sunday are a part of the travel duration.
	 * If they are, then the count of sum of Saturdays and Sundays is returned 
	 */
	private int findWeekendCount(Date startDate, Date returnDate){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    Date sDate = null;
	    Date rDate = null;
	    
	    int weekendCount = 0;
	    try {
	        //date = format.parse("2014-10-30");
	        sDate = startDate;
	        rDate = returnDate;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
	    calendar.setTime(sDate);
	    System.out.println("Day of the week = "
	            + (calendar.get(Calendar.DAY_OF_WEEK)));
	    
	  while(sDate.before(rDate) || sDate.equals(rDate)){  
	  if((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)){
		  weekendCount++;
	 
	  }
	  calendar.add(Calendar.DATE, 1);
	  sDate = calendar.getTime();
		  
	  }
	  return weekendCount;
	}
	
	
	
	/*
	 * Method which returns a list of holidays
	 */
	public List<String> getHolidayListReturnList(String year){
		HttpClient httpClient = new DefaultHttpClient();

		try {
			
			//TO-DO: Pass the year which is a method parameter to the API call. 2015 is hardcoded for now
			HttpGet request = new HttpGet("http://holidayapi.com/v1/holidays?country=US&year=2015");
			request.addHeader("content-type", "application/json");
			request.addHeader("Accept","application/json");
			HttpResponse response = httpClient.execute(request);

			BufferedReader br = new BufferedReader(
					new InputStreamReader((response.getEntity().getContent())));

			String output;
			List<String> holidayList = new ArrayList<String>();
			
			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(br);
			JSONObject jsonObject = (JSONObject) obj;
			System.out.println(" Now I'm printing JSON --------");
			System.out.println(jsonObject);
			
			JSONObject holidayObj = (JSONObject) jsonObject.get("holidays");
			Set<String> holiday = holidayObj.keySet();
						  
			Iterator iter = holiday.iterator();
			System.out.println(" Printing all keys of the set ----------");
			while(iter.hasNext()){
				String pickedHoliday = (String)iter.next();
				calendar.setTime(format.parse(pickedHoliday));
			    if((calendar.get(Calendar.DAY_OF_WEEK) != 7) || (calendar.get(Calendar.DAY_OF_WEEK) != 1)){
			    	System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
			    	holidayList.add(pickedHoliday);
			    }
					
			}
			return holidayList;
			}catch (Exception ex) {
				ex.printStackTrace();
				return null;
			} finally {
				httpClient.getConnectionManager().shutdown();
			}
		}
			
	
	
	/*
	 * Reads Holiday calendar API
	 * Returns true if either start or return dates are holidays or if holiday falls between start and return dates
	 * Returns false otherwise
	 */
	public int getHolidayCount(Date startDate, Date returnDate){
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    Date sDate = null;
	    Date rDate = null;
	    
	    int holidayCountLocal = 0;
	    
	    int holidayCount = 0;
	    try {
	        //date = format.parse("2014-10-30");
	        sDate = startDate;
	        rDate = returnDate;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
	    calendar.setTime(sDate);
	    System.out.println("Day of the week = "
	            + (calendar.get(Calendar.DAY_OF_WEEK)));
	    
	  while(sDate.before(rDate) || sDate.equals(rDate)){  
	  for(int index=0; index < holidayList.size(); index++){
		  if(sDate.equals(holidayList.indexOf(index)) ){
			  holidayCountLocal++;
		  }
	 
	  }
	  calendar.add(Calendar.DATE, 1);
	  sDate = calendar.getTime();
		  
	  }
	  return holidayCountLocal;
		
		
}

}
