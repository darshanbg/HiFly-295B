package com.recommendation.comparators;

import java.util.Comparator;

import com.recommendation.airline.dto.FlightDisplay;

public class FlightPriceComparator implements Comparator<FlightDisplay> {

	@Override
	public int compare(FlightDisplay o1, FlightDisplay o2) {
		float price1 = Float.parseFloat(o1.getSaleTotal().replaceAll("[^0-9.]", ""));
		float price2 = Float.parseFloat(o2.getSaleTotal().replaceAll("[^0-9.]", ""));

		return (int) ((int) price1 - price2);
	}
}
