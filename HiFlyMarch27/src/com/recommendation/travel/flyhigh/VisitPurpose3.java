package com.recommendation.travel.flyhigh;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

public class VisitPurpose3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int diffInDays = 0;
		boolean isBusinessTrip = false;
		
		VisitPurpose3 obj = new VisitPurpose3();
		
		System.out.println(" Enter the date you wish to travel in (YYYY-MM-DD) format");
		Scanner scan1 = new Scanner(System.in);
		String startDate = scan1.next();
		System.out.println(" Your start date is " + startDate);
		
		System.out.println(" Enter the date you wish to return in (YYYY-MM-DD) format");
		Scanner scan2 = new Scanner(System.in);
		String returnDate = scan2.next();
		System.out.println("Your return date is " + returnDate);
		
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
	    
	    
		diffInDays = Math.abs((int) ((startDateAsDate.getTime() - returnDateAsDate.getTime()) / (1000 * 60 * 60 * 24)));
		
		boolean isWeekend = obj.findDayOfTheWeek(startDate);
		if(isWeekend == true)
			System.out.println(" Your start date is a weekend!");
		else{
			System.out.println("Your start date is a week day");
			isBusinessTrip = true;
		}
		
		/*
		 * If start date is a weekday, then isBusinessTrip is set to true. 
		 * Then IF the return date is within the next 2 days, it is considered a business visit
		 *      ELSE, it is considered a casual visit.
		 */
		if(isBusinessTrip){ 
			if(diffInDays <=2){
				System.out.println("Difference between start and return dates is " + diffInDays + " and the travel start date is a weekday, so its most likely a business trip");
			}

			else{
				System.out.println("Your trip is most probably a casual one. Enjoy your vacation");
			}
		}

	}
	
	
	/*
	 * Returns true if the date is Friday, Saturday or Sunday
	 * Else, returns false
	 */
	private boolean findDayOfTheWeek(String dateArg){
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = null;
	    try {
	        //date = format.parse("2014-10-30");
	        date = format.parse(dateArg);
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    
	    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
	    calendar.setTime(date);
	    System.out.println("Day of the week = "
	            + (calendar.get(Calendar.DAY_OF_WEEK)));
	    
	  if((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) || (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) || (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) || (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY)){
		  	return false;
	  }
	  else{
		  return true;
	  }
	}
	

}
