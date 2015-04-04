package com.recommendation.comparators;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.recommendation.airline.dto.FlightDisplay;

public class ChainedComparator implements Comparator<FlightDisplay> {

	private List<Comparator<FlightDisplay>> comparatorList;

	@SafeVarargs
	public ChainedComparator(Comparator<FlightDisplay>... listComp) {
		this.comparatorList = Arrays.asList(listComp);
	}

	@Override
	public int compare(FlightDisplay flight1, FlightDisplay flight2) {
		for (Comparator<FlightDisplay> comparator : comparatorList) {
			int result = comparator.compare(flight1, flight2);
			if (result != 0)
				return result;
		}
		return 0;
	}

}
