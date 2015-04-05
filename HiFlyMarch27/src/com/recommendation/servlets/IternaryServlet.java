package com.recommendation.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.recommendation.airline.dto.FlightDisplay;
import com.recommendation.airport.HiFlyService;
import com.recommendation.memcache.CrunchifyInMemoryCache;
import com.recommendation.utilities.StringConstants;

/**
 * Servlet implementation class IternaryServlet
 */
@WebServlet("/IternaryServlet")
public class IternaryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();
	String userId = "1";
	CrunchifyInMemoryCache<String, ArrayList<FlightDisplay>> cache = null;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IternaryServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HiFlyService service = new HiFlyService();
		PrintWriter pw = response.getWriter();
		HashMap<String, String> map = createRequestMap(request);
		ArrayList<FlightDisplay> flightDisplayLis = (ArrayList<FlightDisplay>) service
				.getRealTimeFlightData(map);
		 cache = (CrunchifyInMemoryCache<String, ArrayList<FlightDisplay>>)getServletContext().getAttribute("flightCache");
		 if(cache.get(userId)!=null){
			 flightDisplayLis.addAll(cache.get(userId));
		 }
		service.sortFlightDisplay(flightDisplayLis);
		cache.put(userId, flightDisplayLis);
		// pw.write("After==>"+
		// request.getParameter("source")+"===>"+request.getParameter("destination"));
		pw.write(gson.toJson(flightDisplayLis));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			HiFlyService service = new HiFlyService();
			String reqType = request.getParameter("reqType");
			PrintWriter pw = response.getWriter();
			if (reqType == null) {
				HashMap<String, String> map = createRequestMap(request);
				System.out.println(map);

				ArrayList<FlightDisplay> flightDisplayList = (ArrayList<FlightDisplay>) service
						.getRealTimeFlightData(map);

				request.setAttribute("displayList", flightDisplayList);
				RequestDispatcher dispatch = request.getRequestDispatcher("index.jsp");
				dispatch.forward(request, response);
			}
			else if (StringConstants.GET_AIRPORT_PAIR.equals(reqType)) {

				String source = request.getParameter("source");
				String destination = request.getParameter("destination");
				ArrayList<String> sourceCodes = HiFlyService.getAirportNearLocation(source);
				ArrayList<String> destinationCodes = HiFlyService
						.getAirportNearLocation(destination);
				HashMap<String, ArrayList<String>> sourceDestinationPair = new HashMap<String, ArrayList<String>>();
				sourceDestinationPair.put("sourceCodes", sourceCodes);
				sourceDestinationPair.put("destinationCodes", destinationCodes);

				pw.write(gson.toJson(sourceDestinationPair));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private HashMap<String, String> createRequestMap(HttpServletRequest request) {
		HashMap<String, String> map = new HashMap<String, String>();

		String source = request.getParameter("source");
		String destination = request.getParameter("destination");
		String date = request.getParameter("date");
		String adultCount = request.getParameter("adultCount");

		if (isEmpty(source) || isEmpty(destination) || isEmpty(date))
			return null;

		String moreOptions = request.getParameter("moreOptionCheckBox");
		String advanceOptionCheckBox = request.getParameter("advanceOptionCheckBox");

		if (moreOptions != null && moreOptions.equals("on")) {
			if (!isEmpty(request.getParameter("infantInLapCount"))) {
				map.put("infantInLapCount", request.getParameter("infantInLapCount"));
			}
			if (!isEmpty(request.getParameter("infantInSeatCount"))) {
				map.put("infantInSeatCount", request.getParameter("infantInSeatCount"));
			}
			if (!isEmpty(request.getParameter("seniorCount"))) {
				map.put("seniorCount", request.getParameter("seniorCount"));
			}
			if (!isEmpty(request.getParameter("childCount"))) {
				map.put("childCount", request.getParameter("childCount"));
			}
		}

		if (advanceOptionCheckBox != null && advanceOptionCheckBox.equals("on")) {

			if (!isEmpty(request.getParameter("maxPrice"))) {
				map.put("maxPrice", request.getParameter("maxPrice"));
			}

			if (!isEmpty(request.getParameter("earliestDeptTime"))) {
				map.put("earliestDeptTime", request.getParameter("earliestDeptTime"));
			}

			if (!isEmpty(request.getParameter("latestDepartureTime")))
				map.put("latestDepartureTime", request.getParameter("latestDepartureTime"));

			if (!isEmpty(request.getParameter("permittedCarriers"))) {
				map.put("permittedCarriers", request.getParameter("permittedCarriers"));
			}

			if (!isEmpty(request.getParameter("maxConnectionDuration"))) {
				map.put("maxConnectionDuration", request.getParameter("maxConnectionDuration"));
			}

			if (!isEmpty(request.getParameter("prohinitedCarriers"))) {
				map.put("prohinitedCarriers", request.getParameter("prohinitedCarriers"));
			}

			if (!isEmpty(request.getParameter("preferredCabin"))) {
				map.put("preferredCabin", request.getParameter("preferredCabin"));
			}

			if (!isEmpty(request.getParameter("refundable"))) {
				map.put("refundable", request.getParameter("refundable"));
			}

			if (!isEmpty(request.getParameter("nonStop"))) {
				map.put("nonStop", request.getParameter("nonStop"));
			}
		}

		// Default Fields
		map.put("sliceLength", "1");
		map.put("solutions", "5");
		map.put("refundable", "false");

		map.put("source", source);
		map.put("destination", destination);
		map.put("date", date);
		if (!isEmpty(adultCount))
			map.put("adultCount", adultCount);

		return map;
	}

	private boolean isEmpty(String input) {
		if (input == null || input.trim().length() == 0)
			return true;
		return false;
	}
}