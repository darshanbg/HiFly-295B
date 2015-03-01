package com.airline.dto;

import java.util.ArrayList;

public class Segment {
	private ArrayList<Leg> leg;
	private Flight flight;
	private String id;

	public ArrayList<Leg> getLeg() {
		return leg;
	}

	public void setLeg(ArrayList<Leg> leg) {
		this.leg = leg;
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
