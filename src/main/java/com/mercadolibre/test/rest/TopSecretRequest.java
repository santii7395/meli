package com.mercadolibre.test.rest;

import java.util.List;

public class TopSecretRequest {
	private List<SatelliteRequest> satellites;

	public List<SatelliteRequest> getSatellites() {
		return satellites;
	}

	public void setSatellites(List<SatelliteRequest> satellites) {
		this.satellites = satellites;
	}	
}
