package com.recommendation.airline.dto;

public class Flight {
	private String carrier;
	private String number;
	
	protected String getCarrier() {
		return carrier;
	}
	protected void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	protected String getNumber() {
		return number;
	}
	protected void setNumber(String number) {
		this.number = number;
	}
	
	
}
