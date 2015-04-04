package com.recommendation.json.utilities;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.recommendation.airline.dto.FlightData;

public class JSonParserAirline {

	public FlightData parseAirlineData(String response) {
		JsonParser parser = new JsonParser();
		JsonObject flightObject = (JsonObject) parser.parse(response);
		FlightData flightData = null;

		try {
			flightObject = (JsonObject) flightObject.get("trips");
			Gson g = new Gson();
			flightData = g.fromJson(flightObject.toString(), FlightData.class);

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		if (flightData == null)
			return null;

		return flightData;
	}

	public String[] parseGeoCode(String response) {
		String[] latLon = new String[2];
		JsonParser parser = new JsonParser();
		JsonObject object = (JsonObject) parser.parse(response);

		try {
			object = (JsonObject) object.get("query");
			object = (JsonObject) ((JsonObject) object.get("results")).get("Result");

			if (object != null) {
				latLon[1] = object.get("longitude").toString().replaceAll("\\W", "");
				latLon[0] = object.get("latitude").toString().replaceAll("\\W", "");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return latLon;
	}

	//
	public void parseAirportDataForGeoCode(String response) {
		String[] latLon = new String[2];
		JsonParser parser = new JsonParser();
		JsonObject object = (JsonObject) parser.parse(response);

		try {

		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
}
