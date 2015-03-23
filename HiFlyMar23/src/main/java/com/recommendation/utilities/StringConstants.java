package com.recommendation.utilities;

public class StringConstants {

	// Google API for real time fligth data
	public static final String STR_FLIGHT_API = "https://www.googleapis.com/qpxExpress/v1/trips/search?key=AIzaSyAyxuBExWvwMpTeiaU1gNvQJVJ6SbzDroM";

	// Yahoo Geo Code
	public static final String STR_GEO_CODE_API = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20geo.placefinder%20where%20text%3D%22";
	public static final String STR_DIAGONISTIC_FLAG = "%22&diagnostics=true";

	// Airport API using geo code
	public static final String STR_AIRPORT_API = "http://airports.pidgets.com/v1/airports?near=";

}
