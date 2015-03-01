package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import airport.HiFlyService;

import com.airline.dto.FlightDisplay;

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
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			HiFlyService service = new HiFlyService();
			ArrayList<FlightDisplay> flightDisplayList = (ArrayList<FlightDisplay>) service
					.getRealTimeFlightData();

			request.setAttribute("displayList", flightDisplayList);
			RequestDispatcher dispatch = request
					.getRequestDispatcher("index.jsp");
			dispatch.forward(request, response);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
}
