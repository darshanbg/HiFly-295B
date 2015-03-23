package com.recommendation.json.utilities;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/*
 * Mandatory in Map: sliceLength, solutions, refundable, origin, destination, date
 * 
 */
@SuppressWarnings("unchecked")
public class JsonBuilder {

	public JSONArray createTravelRequest(HashMap<String, String> map) {

		JSONArray jArray = new JSONArray();
		JSONObject mainReqObject = new JSONObject();
		for (int i = 0; i < Integer.parseInt(map.get("sliceLength")); i++) {

			JSONObject reqObject = new JSONObject();
			reqObject.put("passengers", getPassengerObject(map));
			reqObject.put("solutions", map.get("solutions"));
			reqObject.put("refundable", Boolean.parseBoolean(map.get("refundable")));
			reqObject.put("slice", getSliceArray(map, i));

			mainReqObject.put("request", reqObject);
			jArray.add(mainReqObject);
		}

		System.out.println(mainReqObject);
		return jArray;
	}

	private JSONObject getPassengerObject(HashMap<String, String> map) {
		JSONObject passObject = new JSONObject();
		passObject.put("adultCount", getValueForKey(map, "adultCount"));
		passObject.put("infantInLapCount", getValueForKey(map, "infantInLapCount"));
		passObject.put("infantInSeatCount", getValueForKey(map, "infantInSeatCount"));
		passObject.put("childCount", getValueForKey(map, "childCount"));
		passObject.put("seniorCount", getValueForKey(map, "seniorCount"));
		return passObject;
	}

	private JSONArray getSliceArray(HashMap<String, String> map, int i) {
		JSONArray sliceArray = new JSONArray();

		JSONObject sliceObj = new JSONObject();

		// Mandatory Fields
		sliceObj.put("origin", map.get("sourceCode" + (i + 1)));
		sliceObj.put("destination", map.get("destCode" + (i + 1)));
		sliceObj.put("date", map.get("date"));

		// Optional fields
		if (map.containsKey("maxStops"))
			sliceObj.put("maxStops", map.get("maxStops"));
		if (map.containsKey("maxConnectionDuration"))
			sliceObj.put("maxConnectionDuration", map.get("maxConnectionDuration"));
		if (map.containsKey("preferredCabin"))
			sliceObj.put("preferredCabin", map.get("preferredCabin"));
		if (map.containsKey("alliance"))
			sliceObj.put("alliance", map.get("alliance"));

		// optional objects
		if (map.containsKey("permittedDepartureTime"))
			sliceObj.put("permittedDepartureTime",
					getPermittedDepTimeObject(map.get("permittedDepartureTime")));
		sliceArray.add(sliceObj);

		return sliceArray;
	}

	private String getValueForKey(HashMap<String, String> map, String key) {
		return map.containsKey(key) == true ? map.get(key) : "0";
	}

	private JSONObject getPermittedDepTimeObject(String str) {
		JSONObject deptTimeObject = new JSONObject();
		String[] strArray = str.split(",");
		if (strArray.length == 2) {
			deptTimeObject.put("earliestTime", strArray[0]);
			deptTimeObject.put("latestTime", strArray[1]);
		}
		return deptTimeObject;
	}

	public static void main(String[] args) {
		JsonBuilder builder = new JsonBuilder();

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("adultCount", "1");
		map.put("adultCount", "1");
		map.put("sliceLength", "1");
		map.put("origin", "SJC");
		map.put("destination", "NYC");
		map.put("date", "2015-03-11");
		map.put("solutions", "4");
		builder.createTravelRequest(map);
	}
}