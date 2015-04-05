package com.recommendation.memcache;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.recommendation.airline.dto.FlightDisplay;

@WebListener
public class InitializationListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		 ServletContext ctx = sce.getServletContext();
		 CrunchifyInMemoryCache<String, ArrayList<FlightDisplay>> cache = new CrunchifyInMemoryCache<String, ArrayList<FlightDisplay>>(100, 10, 500000);
		 ctx.setAttribute("flightCache", cache );
	}

}
