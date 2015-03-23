package com.recommendation.airline.dto;

import java.util.ArrayList;

public class Pricing {
	private ArrayList<SegmentPricing> segmentPricing;
	private String saleTotal;

	public ArrayList<SegmentPricing> getSegmentPricing() {
		return segmentPricing;
	}

	public void setSegmentPricing(ArrayList<SegmentPricing> segmentPricing) {
		this.segmentPricing = segmentPricing;
	}

	public String getSaleTotal() {
		return saleTotal;
	}

	public void setSaleTotal(String saleTotal) {
		this.saleTotal = saleTotal;
	}

}
