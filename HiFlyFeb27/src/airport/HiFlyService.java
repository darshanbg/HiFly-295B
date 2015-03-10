package airport;

import java.io.InputStream;
import java.util.ArrayList;

import json.utilities.JSonParserAirline;
import json.utilities.JsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;

import utilities.StringConstants;
import utilities.StringUtilities;

import com.airline.dto.Airline;
import com.airline.dto.FlightData;
import com.airline.dto.FlightDisplay;
import com.airline.dto.Leg;
import com.airline.dto.Pricing;
import com.airline.dto.Segment;
import com.airline.dto.Slice;

@SuppressWarnings("deprecation")
public class HiFlyService {

	// Given Source Destination, method gets real time flight details
	public ArrayList<FlightDisplay> getRealTimeFlightData() {

		long start = System.currentTimeMillis();
		HttpClient httpClient = new DefaultHttpClient();
		FlightData flightData = null;
		ArrayList<FlightDisplay> flightDataList = null;
		try {
			JsonBuilder jBuilder = new JsonBuilder();
			JSONObject jsonObject = (JSONObject) jBuilder
					.createTravelRequest(null);

			HttpPost request = new HttpPost(StringConstants.STR_FLIGHT_API);
			StringEntity params = new StringEntity(jsonObject.toString());
			request.addHeader("content-type", "application/json");
			request.addHeader("Accept", "application/json");
			request.setEntity(params);

			HttpResponse response = httpClient.execute(request);
			InputStream is = response.getEntity().getContent();

			JSonParserAirline customParser = new JSonParserAirline();
			flightData = customParser.parseAirlineData(StringUtilities
					.convertToString(is));

			flightDataList = this.initializeFlightDisplay(flightData);
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
			return customParser.parseGeoCode(StringUtilities
					.convertToString(is));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return latLon;
	}

	public void getAirportWithinGeoCode(String[] geoCode) {

		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpPost request = new HttpPost(StringConstants.STR_AIRPORT_API
					+ geoCode[0] + "," + geoCode[1]);
			request.addHeader("content-type", "application/json");
			request.addHeader("Accept", "application/json");
			HttpResponse response = httpClient.execute(request);
			InputStream is = response.getEntity().getContent();

			JSonParserAirline customParser = new JSonParserAirline();
			customParser.parseGeoCode(StringUtilities.convertToString(is));
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
						flightDisplay.setDestinationTerminal(leg
								.getDestinationTerminal());
						flightDisplay.setDuration(leg.getDuration());
						flightDisplay.setOrigin(leg.getOrigin());
						flightDisplay
								.setOriginTerminal(leg.getOriginTerminal());
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

		service.getGeoCode("201 S 4th Street, 95112");
		service.getAirportWithinGeoCode(service
				.getGeoCode("201 S 4th Street, 95112"));
	}
}
