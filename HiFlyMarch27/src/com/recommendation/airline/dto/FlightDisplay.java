package com.recommendation.airline.dto;

import java.util.ArrayList;

public class FlightDisplay {
	private String saleTotal;
	private int flightDuration;
	private int distance;

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getFlightDuration() {
		return flightDuration;
	}

	public void setFlightDuration(int flightDuration) {
		this.flightDuration = flightDuration;
	}

	ArrayList<TransitFlight> transitFlights;

	public ArrayList<TransitFlight> getTransitFlights() {
		return transitFlights;
	}

	public void setTransitFlights(ArrayList<TransitFlight> transitFlights) {
		this.transitFlights = transitFlights;
	}

	public void setSaleTotal(String saleTotal) {
		this.saleTotal = saleTotal;
	}

	public String getSaleTotal() {
		return saleTotal;
	}

	public class TransitFlight {
		private String origin;
		private String destination;
		private String aircraft;
		private String duration;
		private String arrivalTime;
		private String departureTime;
		private String originTerminal;
		private String destinationTerminal;
		private String carrier;
		private String flightNumber;

		public String getOrigin() {
			return origin;
		}

		public void setOrigin(String origin) {
			this.origin = origin;
		}

		public String getDestination() {
			return destination;
		}

		public void setDestination(String destination) {
			this.destination = destination;
		}

		public String getAircraft() {
			return aircraft;
		}

		public void setAircraft(String aircraft) {
			this.aircraft = aircraft;
		}

		public String getDuration() {
			return duration;
		}

		public void setDuration(String duration) {
			this.duration = duration;
		}

		public String getArrivalTime() {
			return arrivalTime;
		}

		public void setArrivalTime(String arrivalTime) {
			this.arrivalTime = arrivalTime;
		}

		public String getDepartureTime() {
			return departureTime;
		}

		public void setDepartureTime(String departureTime) {
			this.departureTime = departureTime;
		}

		public String getOriginTerminal() {
			return originTerminal;
		}

		public void setOriginTerminal(String originTerminal) {
			this.originTerminal = originTerminal;
		}

		public String getDestinationTerminal() {
			return destinationTerminal;
		}

		public void setDestinationTerminal(String destinationTerminal) {
			this.destinationTerminal = destinationTerminal;
		}

		public String getCarrier() {
			return carrier;
		}

		public void setCarrier(String carrier) {
			this.carrier = carrier;
		}

		public String getFlightNumber() {
			return flightNumber;
		}

		public void setFlightNumber(String flightNumber) {
			this.flightNumber = flightNumber;
		}
	}

}
