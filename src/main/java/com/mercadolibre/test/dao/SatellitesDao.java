package com.mercadolibre.test.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mercadolibre.test.entity.Location;
import com.mercadolibre.test.entity.Satellite;

@Repository
public class SatellitesDao {
	
	//Inicializo lo satellites de prueba
	public List<Satellite> getSatellites(){
		List<Satellite> satellites = new ArrayList<Satellite>();
		Satellite satellite = new Satellite();
		satellite.setName("Kenobi");
		satellite.setLocation(new Location(0, 0));
		satellites.add(satellite);
		satellite = new Satellite();
		satellite.setName("Skywalker");
		satellite.setLocation(new Location(10, 10));
		satellites.add(satellite);
		satellite = new Satellite();
		satellite.setName("Sato");
		satellite.setLocation(new Location(20, 0));
		satellites.add(satellite);
		return satellites;		 
	}	
}
