package com.recommendation.comparators;

import java.util.Comparator;

import com.recommendation.airline.dto.FlightDisplay;

public class FlightDurationComparator implements Comparator<FlightDisplay> {

	@Override
	public int compare(FlightDisplay o1, FlightDisplay o2) {

		return o1.getFlightDuration() - o2.getFlightDuration();
	}

}
