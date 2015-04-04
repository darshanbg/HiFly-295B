package com.recommendation.airline.dto;

public class Leg {
	private String origin;
	private String destination;
	private String aircraft;
	private String duration;
	private String arrivalTime;
	private String departureTime;
	private String originTerminal;
	private String destinationTerminal;
	
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
}
