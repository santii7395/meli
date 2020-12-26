package com.mercadolibre.test.rest;

import com.mercadolibre.test.entity.Location;

public class TopSecretResponse {
	private Location position;
	private String message;
	
	public Location getPosition() {
		return position;
	}
	public void setPosition(Location position) {
		this.position = position;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
