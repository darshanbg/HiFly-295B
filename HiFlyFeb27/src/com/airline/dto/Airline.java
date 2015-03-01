package com.airline.dto;

import java.util.ArrayList;

public class Airline {
	private ArrayList<Slice> slice;
	private ArrayList<Pricing> pricing;

	public ArrayList<Slice> getSlice() {
		return slice;
	}

	public void setSlice(ArrayList<Slice> slice) {
		this.slice = slice;
	}

	public ArrayList<Pricing> getPricing() {
		return pricing;
	}

	public void setPricing(ArrayList<Pricing> pricing) {
		this.pricing = pricing;
	}

}
