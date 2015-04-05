package com.recommendation.airport;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

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

import com.recommendation.airline.dto.Airline;
import com.recommendation.airline.dto.Flight;
import com.recommendation.airline.dto.FlightData;
import com.recommendation.airline.dto.FlightDisplay;
import com.recommendation.airline.dto.Leg;
import com.recommendation.airline.dto.Pricing;
import com.recommendation.airline.dto.Segment;
import com.recommendation.airline.dto.Slice;
import com.recommendation.comparators.ChainedComparator;
import com.recommendation.comparators.FlightDistanceComparator;
import com.recommendation.comparators.FlightDurationComparator;
import com.recommendation.comparators.FlightPriceComparator;
import com.recommendation.json.utilities.JSonParserAirline;
import com.recommendation.json.utilities.JsonBuilder;
import com.recommendation.travel.database.MySql;
import com.recommendation.travel.flyhigh.VisitPurpose1;
import com.recommendation.utilities.StringConstants;
import com.recommendation.utilities.StringUtilities;

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
		
		if(returnD == null || returnD == ""){
			purpose = "CASUAL";
			System.out.println(" There is no return date. Therefore, purpose determined**************" + purpose);
		}
		else{
			purpose = visObj.findVisitPurpose(startD, returnD, startT, returnT);
			System.out.println(" Purpose of visit is **********************" + purpose);
		}		/*
		 * Pushing user search results to database - To give recommendation
		 * based on search history Table "SEARCH_HISTORY"
		 * <=============================> CREATE TABLE IF NOT EXISTS
		 * SEARCH_HISTORY( username varchar(20), source varchar(100),
		 * destination varchar(100), startDate varchar(10), returnDate
		 * varchar(10), travelPurpose varchar(8) )
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

			// ArrayList<String> sourceAirportList =
			// getAirportNearLocation(map.get("source"));
			// ArrayList<String> destAiportList =
			// getAirportNearLocation(map.get("destination"));

			// To be removed: For testing
			ArrayList<String> sourceAirportList = new ArrayList<String>();
			// ArrayList<String> destAiportList = new ArrayList<String>();
			// sourceAirportList.add("SFO");
			// destAiportList.add("NYC");

			// map = updateMap(map, sourceAirportList, destAiportList);

			JSONArray jArray = jBuilder.createTravelRequest(map);
			flightDataArray = new FlightData[jArray.size()];
			FlightData flightData = null;

			for (int i = 0; i < jArray.size(); i++) {
				JSONObject jsonObject = (JSONObject) jArray.get(i);
				System.out.println("Request Object: " + jsonObject);

				HttpPost request = new HttpPost(StringConstants.STR_FLIGHT_API);
				StringEntity params = new StringEntity(jsonObject.toString());
				request.addHeader("content-type", "application/json");
				request.setEntity(params);
				request.addHeader("Accept", "application/json");

				HttpResponse response = httpClient.execute(request);
				InputStream is = response.getEntity().getContent();
				JSonParserAirline customParser = new JSonParserAirline();
				String result = StringUtilities.convertToString(is);
				flightData = customParser.parseAirlineData(result);

				ArrayList<FlightDisplay> list = initializeFlightDisplay(flightData,
						sourceAirportList);
				if (list != null)
					flightDataList.addAll(list);
			}

			long end = System.currentTimeMillis();

			System.out.println("Time: " + (end - start));

			if (purpose.equalsIgnoreCase("business"))
				sortFlightDisplay(flightDataList);
			else
				sortFlightDisplay(flightDataList);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
		}

		return flightDataList;
	}

	/**
	 * @param flightDataList
	 */
	public void sortFlightDisplay(ArrayList<FlightDisplay> flightDataList) {
		Collections.sort(flightDataList, new ChainedComparator(
				new FlightDistanceComparator(), new FlightPriceComparator(),
				new FlightDurationComparator()));
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

	public ArrayList<FlightDisplay> initializeFlightDisplay(FlightData data,
			ArrayList<String> sourceAirportList) {
		ArrayList<FlightDisplay> flightDataList = new ArrayList<FlightDisplay>();
		ArrayList<Airline> airlineList = data.getTripOption();
		String source = null;

		if (airlineList != null) {
			for (Airline airline : airlineList) {
				FlightDisplay flightDisplay = new FlightDisplay();
				ArrayList<FlightDisplay.TransitFlight> transitList = new ArrayList<FlightDisplay.TransitFlight>();
				ArrayList<Slice> sliceList = airline.getSlice();

				int duration = 0;
				ArrayList<Pricing> priceList = airline.getPricing();

				for (Slice slice : sliceList) {
					duration += slice.getDuration();
					ArrayList<Segment> segmentList = slice.getSegment();
					for (Segment segment : segmentList) {
						Flight flight = segment.getFlight();
						ArrayList<Leg> legList = segment.getLeg();
						for (Leg leg : legList) {
							if (source == null)
								source = new String(leg.getOrigin());
							FlightDisplay.TransitFlight transitFlight = flightDisplay.new TransitFlight();
							transitFlight.setAircraft(leg.getAircraft());
							transitFlight.setArrivalTime(leg.getArrivalTime());
							transitFlight.setDepartureTime(leg.getDepartureTime());
							transitFlight.setDestination(leg.getDestination());
							transitFlight.setDestinationTerminal(leg.getDestinationTerminal());
							transitFlight.setDuration(leg.getDuration());
							transitFlight.setOrigin(leg.getOrigin());
							transitFlight.setOriginTerminal(leg.getOriginTerminal());

							transitFlight.setCarrier(flight.getCarrier());
							transitFlight.setFlightNumber(flight.getNumber());
							transitList.add(transitFlight);
						}
					}
				}

				for (Pricing pricing : priceList) {
					flightDisplay.setSaleTotal(pricing.getSaleTotal());
					flightDisplay.setFlightDuration(duration);
					int distance = getDistance(source, sourceAirportList);
					flightDisplay.setDistance(distance);
				}

				flightDisplay.setTransitFlights(transitList);
				flightDataList.add(flightDisplay);
			}

			return flightDataList;
		}
		else {
			// Have to add the blocked airports, need to know if the src or dest
			// is the culprit
			return null;
		}

	}

	public static ArrayList<String> getAirportNearLocation(String location) {

		HttpClient httpClient = new DefaultHttpClient();
		ArrayList<String> airportCodes = new ArrayList<String>();
		try {
			location = location.replace(" ", "%20");
			HttpPost request = new HttpPost("http://airports.pidgets.com/v1/airports?near="
					+ location + "&n=3" + "&type=Airports");
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

	private static HashMap<String, String> updateMap(HashMap<String, String> map,
			ArrayList<String> sourceAirportList, ArrayList<String> destAirportList) {

		map.put("sliceLength", "" + sourceAirportList.size());
		for (int i = 0; i < sourceAirportList.size(); i++) {
			map.put("sourceCode" + (i + 1), sourceAirportList.get(i));
			map.put("destCode" + (i + 1), destAirportList.get(i));
		}

		return map;
	}

	private static String getTravelPurpose(HashMap<String, String> map) {
		String startD = map.get("date");
		VisitPurpose1 visObj = new VisitPurpose1();

		// Hardcoding values for username, return date, start time and return
		// time for now
		SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
		Date startT = new Date();
		Date returnT = new Date();

		System.out.println(" Start date is ***********************" + startD);
		String returnD = "2015-04-20";
		try {
			startT = parser.parse("17:00");
			returnT = parser.parse("12:00");
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return visObj.findVisitPurpose(startD, returnD, startT, returnT);
	}

	private int getDistance(String source, ArrayList<String> sourceAirportList) {
		int i = 0;
		for (; i < sourceAirportList.size(); i++) {
			if (sourceAirportList.get(i).equalsIgnoreCase(source))
				break;
		}
		return i + 1;
	}
}