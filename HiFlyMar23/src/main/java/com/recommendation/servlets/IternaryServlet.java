package com.recommendation.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.recommendation.airport.HiFlyService;

import com.recommendation.airline.dto.FlightDisplay;

/**
 * Servlet implementation class IternaryServlet
 */
@WebServlet("/IternaryServlet")
public class IternaryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			HiFlyService service = new HiFlyService();

			HashMap<String, String> map = createRequestMap(request);
			System.out.println(map);

			ArrayList<FlightDisplay> flightDisplayList = (ArrayList<FlightDisplay>) service
					.getRealTimeFlightData(map);

			request.setAttribute("displayList", flightDisplayList);
			RequestDispatcher dispatch = request.getRequestDispatcher("index.jsp");
			dispatch.forward(request, response);
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