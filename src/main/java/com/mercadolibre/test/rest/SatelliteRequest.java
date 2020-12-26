package com.mercadolibre.test.rest;

public class SatelliteRequest {
	private String name;
	private double distance;
	private String[] message;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public String[] getMessage() {
		return message;
	}
	public void setMessage(String[] message) {
		this.message = message;
	}

	

}
