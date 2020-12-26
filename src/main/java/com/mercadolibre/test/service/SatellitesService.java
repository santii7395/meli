package com.mercadolibre.test.service;

import java.util.List;

import com.mercadolibre.test.entity.Location;
import com.mercadolibre.test.rest.SatelliteRequest;

public interface SatellitesService {
	//Como necesito saber a que satelite pertenece cada distancia paso como parametro un objeto 
	//que tenga el satelite y su correspondiente distancia
	public Location getLocation(List<SatelliteRequest> distances);
	public String getMessage(List<String[]> messages);

}
