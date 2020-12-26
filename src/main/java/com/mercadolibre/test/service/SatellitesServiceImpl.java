package com.mercadolibre.test.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercadolibre.test.dao.SatellitesDao;
import com.mercadolibre.test.entity.Location;
import com.mercadolibre.test.entity.Satellite;
import com.mercadolibre.test.rest.SatelliteRequest;

@Service
public class SatellitesServiceImpl implements SatellitesService {
	
	@Autowired
	SatellitesDao dao;
	
	@Override
	public Location getLocation(List<SatelliteRequest> distances) {
		List<Satellite> satellites = dao.getSatellites();
		//Satelite 1
		String satellite1Name = satellites.get(0).getName();
		double satellite1X = satellites.get(0).getLocation().getPositionX();
		double satellite1Y = satellites.get(0).getLocation().getPositionY();
		//Satelite 2
		String satellite2Name = satellites.get(1).getName();
		double satellite2X = satellites.get(1).getLocation().getPositionX();
		double satellite2Y = satellites.get(1).getLocation().getPositionY();
		//Satelite 3
		String satellite3Name = satellites.get(2).getName();
		double satellite3X = satellites.get(2).getLocation().getPositionX();
		double satellite3Y = satellites.get(2).getLocation().getPositionY();
		
		double satellite1Distance = 0;
		double satellite2Distance = 0;
		double satellite3Distance = 0;
		
		//Asigno cada distancia a su correspondiente satellite
		for (SatelliteRequest satellite : distances) {
			if (satellite.getName() !=null && satellite.getName().equalsIgnoreCase(satellite1Name)) {
				satellite1Distance = satellite.getDistance();
				continue;
			}
			if (satellite.getName()!=null && satellite.getName().equalsIgnoreCase(satellite2Name)) {
				satellite2Distance = satellite.getDistance();
				continue;
			}
			if (satellite.getName() !=null && satellite.getName().equalsIgnoreCase(satellite3Name)) {
				satellite3Distance = satellite.getDistance();
				continue;
			}
		}		
		
		double d = (double) Math.sqrt((Math.pow((satellite2X - satellite1X), 2)) + Math.pow((satellite2Y - satellite1Y), 2));
		double a = (double) (Math.pow(satellite1Distance, 2) - Math.pow(satellite2Distance, 2) + Math.pow(d, 2)) / (2*d);
		double h = (double) Math.sqrt(Math.pow(satellite1Distance, 2) - Math.pow(a, 2));
		
		double x3 = satellite1X + a * (satellite2X - satellite1X) / d;
		double y3 = satellite1Y + a * (satellite2Y - satellite1Y) / d;
		
		//Punto 1 donde se intersecan las dos primeras circunsferencias
		double interX1 = x3 + h * (satellite2Y - satellite1Y)/d;
		double interY1 = y3 - h * (satellite2X - satellite1X)/d;
		
		//Punto 2 donde se intersecan las dos primeras circunsferencias		
		double interX2 = x3 - h * (satellite2Y - satellite1Y)/d;
		double interY2 = y3 + h * (satellite2X - satellite1X)/d;

		//Luego de obtener los puntos de interseccion de las primeras dos circunsferencias, reemplazo en la ecuacion de la tercera circunsferencia
		double test1 = (double) (Math.pow((interX1 - satellite3X), 2) + Math.pow((interY1 - satellite3Y), 2));
		if (test1 == Math.pow(satellite3Distance, 2)) {
			return new Location(interX1, interY1);
		}
		
		double test2 = (double) (Math.pow((interX2 - satellite3X), 2) + Math.pow((interY2 - satellite3Y), 2));
		if (test2 == Math.pow(satellite3Distance, 2)) {
			return new Location(interX2, interY2);
		}
		
		return null;
	}

	@Override
	public String getMessage(List<String[]> messages) {
		String finalMessage = "";
		Map<Integer, String> map = new HashMap<Integer, String>();
		messages.stream().forEach(message -> {
			for (int i = 0; i < message.length; i++) {
				String part = message[i];
				if (part != null && !part.isBlank()) {
					map.put(i, part);
				}
			}
		});
		
		for (int i = 0; i < map.size(); i++) {
			finalMessage = finalMessage.concat(map.get(i).concat(" "));
		}
		
		return finalMessage.trim();
	}
}
