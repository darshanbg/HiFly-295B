package com.recommendation.travel.flyhigh;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

public class VisitPurpose2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
	
	public String findVisitPurpose2(String startDate, String returnDate){
		int diffInDays = 0;
		boolean isCasual = false;
		
//		System.out.println(" Enter the date you wish to travel in (YYYY-MM-DD) format");
//		Scanner scan1 = new Scanner(System.in);
//		String startDate = scan1.next();
//		System.out.println(" Your start date is " + startDate);
//		
//		System.out.println(" Enter the date you wish to return in (YYYY-MM-DD) format");
//		Scanner scan2 = new Scanner(System.in);
//		String returnDate = scan2.next();
//		System.out.println("Your return date is " + returnDate);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date startDateAsDate = null;
		Date returnDateAsDate = null;
	    try {
	        //date = format.parse("2014-10-30");
	    	startDateAsDate = format.parse(startDate);
	    	returnDateAsDate = format.parse(returnDate);
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    
	    isCasual = findDayOfTheWeek(startDateAsDate);
		if(isCasual == true){
			System.out.println(" Your start date is a Saturday! - CASUAL VISIT");
			return "CASUAL";
		}
		else{
			System.out.println("BUSINESS VISIT");
			return "BUSINESS";
		}

	}
	
	/*
	 * Returns true if the start date of the trip is Saturday (Casual visit).
	 * Else, returns false (Business visit)
	 * If a trip start date is a Saturday, it can't be a business trip considering no company wastes a
	 * Sunday providing accommodation to the employee.
	 */
	private boolean findDayOfTheWeek(Date date){
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    
	    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
	    calendar.setTime(date);
	    System.out.println("Day of the week = "
	            + (calendar.get(Calendar.DAY_OF_WEEK)));
	    
	  if((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)){
		  	return true;
	  }
	  else{
		  return false;
	  }
	}


}
