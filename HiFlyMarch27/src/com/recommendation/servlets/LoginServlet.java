package com.recommendation.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.recommendation.airport.HiFlyService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
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

			if (request.getParameter("Login").equals("Login")) {
				System.out.println("Login");
				String uName = request.getParameter("userName");
				String password = request.getParameter("password");
				String result = service.login(uName, password);

				if (!result.equals("-1")) {
					String[] resArray = result.split(",");
					HttpSession session = request.getSession();
					session.setAttribute("userID", resArray[0]);
					session.setAttribute("fName", resArray[1]);
					session.setAttribute("lname", resArray[2]);
					session.setAttribute("userName", resArray[3]);
				}

				RequestDispatcher dispatch = request.getRequestDispatcher("index.jsp");
				dispatch.forward(request, response);

			}
			else if (request.getParameter("Login").equals("Sign up")) {
				String fName = request.getParameter("firstName");
				String lName = request.getParameter("lastName");
				String email = request.getParameter("email");
				String password = request.getParameter("password");
				service.register(fName, lName, email, password);
				RequestDispatcher dispatch = request.getRequestDispatcher("Login.jsp");
				dispatch.forward(request, response);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}

}
