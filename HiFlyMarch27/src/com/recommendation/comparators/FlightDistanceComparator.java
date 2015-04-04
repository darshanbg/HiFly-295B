package com.recommendation.comparators;

import java.util.Comparator;

import com.recommendation.airline.dto.FlightDisplay;

public class FlightDistanceComparator implements Comparator<FlightDisplay> {

	@Override
	public int compare(FlightDisplay o1, FlightDisplay o2) {
		// TODO Auto-generated method stub
		return o1.getDistance() - o2.getDistance();
	}

}
