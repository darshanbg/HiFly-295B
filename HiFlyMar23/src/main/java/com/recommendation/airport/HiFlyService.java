package com.recommendation.airport;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.sql.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import com.recommendation.json.utilities.JSonParserAirline;
import com.recommendation.json.utilities.JsonBuilder;
import com.recommendation.travel.database.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.recommendation.utilities.StringConstants;
import com.recommendation.utilities.StringUtilities;
import com.recommendation.airline.dto.Airline;
import com.recommendation.airline.dto.FlightData;
import com.recommendation.airline.dto.FlightDisplay;
import com.recommendation.airline.dto.Leg;
import com.recommendation.airline.dto.Pricing;
import com.recommendation.airline.dto.Segment;
import com.recommendation.airline.dto.Slice;
import com.recommendation.travel.flyhigh.*;

@SuppressWarnings("deprecation")
public class HiFlyService {
	
	String purpose;

	// Given Source Destination, method gets real time flight details
	public ArrayList<FlightDisplay> getRealTimeFlightData(HashMap<String, String> map) {
		
		System.out.println("Printing HashMap key value pairs...........");
		for (String key : map.keySet()) {
		    System.out.println("Key: " + key + "  " + "Value: " + map.get(key));
		}
		
		String startD = map.get("date");
		String source = map.get("source");
		String destination = map.get("destination");
		VisitPurpose1 visObj = new VisitPurpose1();
		
		//Hardcoding values for username, return date, start time and return time for now
		SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
		Date startT = new Date();
		Date returnT = new Date();
		
		System.out.println(" Start date is ***********************" + startD);
		String returnD = "2015-04-20";
		try{
		startT = parser.parse("17:00");
		returnT = parser.parse("12:00");
		}
		catch(Exception e){
			
		}
				
		purpose = visObj.findVisitPurpose(startD, returnD, startT, returnT);
		System.out.println(" Purpose of visit is **********************" + purpose);
		
		/*
		 * Pushing user search results to database - To give recommendation based on search history
		 * Table "SEARCH_HISTORY"
<=============================>
CREATE TABLE IF NOT EXISTS SEARCH_HISTORY(
username varchar(20),
source varchar(100),
destination varchar(100),
startDate varchar(10),
returnDate varchar(10),
travelPurpose varchar(8)
)
		 */
		
		MySql sqlCon = new MySql();
		Connection con = sqlCon.getConnection();
		
		PreparedStatement insStatement;
		try {
			insStatement = con.prepareStatement("insert into MOOC.SEARCH_HISTORY values(?,?,?,?,?,?)");
			insStatement.setString(1, "Roopa"); //username
			insStatement.setString(2, source);
			insStatement.setString(3, destination);
			insStatement.setString(4, startD);
			insStatement.setString(5, returnD);
			insStatement.setString(6, purpose);

			insStatement.execute();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		long start = System.currentTimeMillis();
		HttpClient httpClient = new DefaultHttpClient();
		FlightData[] flightDataArray = null;
		ArrayList<FlightDisplay> flightDataList = new ArrayList<FlightDisplay>();
		try {
			JsonBuilder jBuilder = new JsonBuilder();

			ArrayList<String> sourceAirportList = getAirportNearLocation(source);
			ArrayList<String> destAiportList = getAirportNearLocation(destination);
			map = updateMap(map, sourceAirportList, destAiportList);

			JSONArray jArray = jBuilder.createTravelRequest(map);
			flightDataArray = new FlightData[jArray.size()];
			FlightData flightData = null;

			for (int i = 0; i < jArray.size(); i++) {
				JSONObject jsonObject = (JSONObject) jArray.get(i);
				HttpPost request = new HttpPost(StringConstants.STR_FLIGHT_API);
				StringEntity params = new StringEntity(jsonObject.toString());
				request.addHeader("content-type", "application/json");
				request.addHeader("Accept", "application/json");
				request.setEntity(params);

				HttpResponse response = httpClient.execute(request);
				InputStream is = response.getEntity().getContent();

				JSonParserAirline customParser = new JSonParserAirline();
				flightData = customParser.parseAirlineData(StringUtilities.convertToString(is));
				flightDataList.addAll(this.initializeFlightDisplay(flightData));
			}

			long end = System.currentTimeMillis();

			System.out.println("Time: " + (end - start));
			System.out.println(flightDataList.get(0).getSaleTotal());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
		}

		return flightDataList;
	}

	// Get GeoCode for any given address
	public String[] getGeoCode(String address) {
		String[] latLon = null;

		HttpClient httpClient = new DefaultHttpClient();
		try {
			StringBuilder req = new StringBuilder();
			req.append(StringConstants.STR_GEO_CODE_API);
			address = address.replaceAll(" ", "%20");
			req.append(address);
			req.append(StringConstants.STR_DIAGONISTIC_FLAG);

			HttpPost request = new HttpPost(String.valueOf(req));
			request.addHeader("content-type", "application/json");
			request.addHeader("Accept", "application/json");
			HttpResponse response = httpClient.execute(request);
			InputStream is = response.getEntity().getContent();

			JSonParserAirline customParser = new JSonParserAirline();
			return customParser.parseGeoCode(StringUtilities.convertToString(is));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return latLon;
	}

	public void getAirportWithinGeoCode(String[] geoCode) {

		HttpClient httpClient = new DefaultHttpClient();

		try {
			// HttpPost request = new HttpPost(StringConstants.STR_AIRPORT_API
			// + geoCode[0] + "," + geoCode[1]);
			HttpPost request = new HttpPost(StringConstants.STR_AIRPORT_API);
			request.addHeader("content-type", "application/json");
			request.addHeader("Accept", "application/json");
			HttpResponse response = httpClient.execute(request);
			InputStream is = response.getEntity().getContent();

			JSonParserAirline customParser = new JSonParserAirline();
			System.out.println(StringUtilities.convertToString(is));
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ArrayList<FlightDisplay> initializeFlightDisplay(FlightData data) {
		ArrayList<FlightDisplay> flightDataList = new ArrayList<FlightDisplay>();

		ArrayList<Airline> airlineList = data.getTripOption();

		for (Airline airline : airlineList) {
			FlightDisplay flightDisplay = new FlightDisplay();
			ArrayList<Slice> sliceList = airline.getSlice();
			ArrayList<Pricing> priceList = airline.getPricing();

			for (Slice slice : sliceList) {
				ArrayList<Segment> segmentList = slice.getSegment();
				for (Segment segment : segmentList) {
					ArrayList<Leg> legList = segment.getLeg();
					for (Leg leg : legList) {
						flightDisplay.setAircraft(leg.getAircraft());
						flightDisplay.setArrivalTime(leg.getArrivalTime());
						flightDisplay.setDepartureTime(leg.getDepartureTime());
						flightDisplay.setDestination(leg.getDestination());
						flightDisplay.setDestinationTerminal(leg.getDestinationTerminal());
						flightDisplay.setDuration(leg.getDuration());
						flightDisplay.setOrigin(leg.getOrigin());
						flightDisplay.setOriginTerminal(leg.getOriginTerminal());
					}
				}
			}

			for (Pricing pricing : priceList) {
				flightDisplay.setSaleTotal(pricing.getSaleTotal());
			}

			flightDataList.add(flightDisplay);
		}

		return flightDataList;
	}

	public static ArrayList<String> getAirportNearLocation(String location) {

		HttpClient httpClient = new DefaultHttpClient();
		ArrayList<String> airportCodes = new ArrayList<String>();
		try {
			location = location.replace(" ", "%20");
			HttpPost request = new HttpPost("http://airports.pidgets.com/v1/airports?near="
					+ location + "&n=5" + "&type=Airports");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			request.addHeader("content-type", "application/json");
			request.addHeader("Accept", "application/json");
			HttpResponse response = httpClient.execute(request);

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));

			StringBuilder output = new StringBuilder();
			String line;

			while ((line = br.readLine()) != null) {
				output.append(line);
			}

			String xmlData = output.toString().substring(output.toString().indexOf("<?xml"));
			System.out.println(xmlData);
			InputSource is = new InputSource(new StringReader(xmlData));
			Document document = builder.parse(is);
			XPathFactory xpathfactory = XPathFactory.newInstance();
			XPath xPath = xpathfactory.newXPath();
			NodeList nodes = (NodeList) xPath
					.evaluate("airports", document, XPathConstants.NODESET);
			int nodeCount = nodes.getLength();

			Node airportList = nodes.item(0);
			NodeList airports = airportList.getChildNodes();
			for (int i = 0; i < airports.getLength(); i++) {
				NodeList airportDetails = airports.item(i).getChildNodes();
				for (int k = 0; k < airportDetails.getLength(); k++) {
					Node n = airportDetails.item(k);
					if (n.getNodeName().equals("code")) {
						airportCodes.add(n.getTextContent());
					}
				}
			}

		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			httpClient.getConnectionManager().shutdown();
		}
		return airportCodes;
	}

	public static void main(String[] str) {
		HiFlyService service = new HiFlyService();
		// service.getRealTimeFlightData();

		// MemcacheClient cacheClient = MemcacheClient.getCacheInstance();
		// System.out.println(MemcacheClient.getCache().get("Lat31.351621252"));
		// System.out.println(MemcacheClient.getCache().get("Lon-113.580001831"));
		//
		// System.out.println(MemcacheClient.getCache().get("Lon-71.3933029175"));
		//
		// System.out.println(MemcacheClient.getCache().get("AirEDDK"));

		// service.getGeoCode("201 S 4th Street, 95112");
		System.out.println(getAirportNearLocation("san jose"));
		
		
//		SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
//		Date startT = new Date();
//		Date returnT = new Date();
//		VisitPurpose1 vis = new VisitPurpose1();
//		try{
//			startT = parser.parse("17:00");
//			returnT = parser.parse("12:00");
//			}
//			catch(Exception e){
//				
//			}
//		String purp = vis.findVisitPurpose("2015-04-17", "2015-04-20", startT, returnT);
//		System.out.println("purpose of visit finally is " + purp);
		
	}

	private static HashMap<String, String> updateMap(HashMap<String, String> map,
			ArrayList<String> sourceAirportList, ArrayList<String> destAirportList) {

		map.put("sliceLength", "" + sourceAirportList.size());
		for (int i = 0; i < sourceAirportList.size(); i++) {
			map.put("sourceCode" + (i + 1), sourceAirportList.get(i));
			map.put("destCode" + (i + 1), destAirportList.get(i));
		}

		return map;
	}
}