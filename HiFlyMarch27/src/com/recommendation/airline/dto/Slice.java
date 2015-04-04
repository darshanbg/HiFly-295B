package com.recommendation.airline.dto;

import java.util.ArrayList;

public class Slice {
	private ArrayList<Segment> segment;
	private int duration;

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public ArrayList<Segment> getSegment() {
		return segment;
	}

	public void setSegment(ArrayList<Segment> segment) {
		this.segment = segment;
	}

}
