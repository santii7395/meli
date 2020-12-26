package com.mercadolibre.test.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.test.entity.Location;
import com.mercadolibre.test.service.SatellitesService;

@RestController
public class Controller {
	
	@Autowired
	SatellitesService service;
	
	List<SatelliteRequest> satellites = new ArrayList<SatelliteRequest>(); 
		
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/topsecret", method=RequestMethod.POST,consumes="application/json",produces="application/json")
	public ResponseEntity getMessageAndPosition(@RequestBody TopSecretRequest request) {
		
		ResponseEntity responseEntity = validateRequest(request);
		
		if (responseEntity != null) {
			return responseEntity;
		}
		
		List<SatelliteRequest> satelliteList = request.getSatellites();		
		Location targetLocation = service.getLocation(satelliteList);
		
		if (targetLocation == null) {
			return ResponseEntity.notFound().build();
		}
		
		List<String[]> messages = new ArrayList<String[]>();
		satelliteList.stream().forEach(satellite -> {
			messages.add(satellite.getMessage());
		});
		String finalMessage = service.getMessage(messages);
		
		TopSecretResponse response = new TopSecretResponse();
		response.setMessage(finalMessage);
		response.setPosition(targetLocation);
		
		if (finalMessage == null || finalMessage.isBlank()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(response);	
	}	

	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "/topsecret_split/{satellite}")
	public ResponseEntity addSatelliteSplit(@PathVariable(value="satellite") String name, @RequestBody SatelliteRequest request) {
		
		//Valido que sea una request valida
		if (request == null || request.getMessage() == null || request.getMessage().length == 0 || name == null || name.isBlank()) {
			return ResponseEntity.notFound().build();
		}
		
		request.setName(name);
		boolean found = false;
		
		//Busco si existe un satelite ya ingresado con el mismo nombre y si existe le hago update de la distancia
		for (SatelliteRequest satellite : satellites) {
			if (satellite.getName().equalsIgnoreCase(name)) {
				satellite.setDistance(request.getDistance());
				found = true;
			}
		}
		
		//Si no existe el satelite lo agrego
		if (!found) {
			satellites.add(request);
		}		
		
		return ResponseEntity.ok(null);
		
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.GET, value = "/topsecret_split")
	public ResponseEntity getMessageAndPositionSplit() {
		
		List<SatelliteRequest> satelliteList = satellites;		
		
		//Si hay menos de 3 satelites ingresados no tengo suficiente informacion para calcular la posicion y el mensaje
		if (satellites.size() < 3) {
			return ResponseEntity.badRequest().body("No hay suficiente informacion");
		}
		
		Location targetLocation = service.getLocation(satelliteList);
		
		if (targetLocation == null) {
			return ResponseEntity.notFound().build();
		}
		
		List<String[]> messages = new ArrayList<String[]>();
		satelliteList.stream().forEach(satellite -> {
			messages.add(satellite.getMessage());
		});
		String finalMessage = service.getMessage(messages);
		
		if (finalMessage == null || finalMessage.isBlank()) {
			return ResponseEntity.notFound().build();
		}
		
		TopSecretResponse response = new TopSecretResponse();
		response.setMessage(finalMessage);
		response.setPosition(targetLocation);
			
		return ResponseEntity.ok(response);	
	}
	
	@SuppressWarnings("rawtypes")
	private ResponseEntity validateRequest(TopSecretRequest request) {
		if (request == null || request.getSatellites() == null || request.getSatellites().isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		for (SatelliteRequest satellite : request.getSatellites()) {
			if (satellite.getName() == null || satellite.getName().isBlank() || satellite.getMessage() == null || satellite.getMessage().length == 0) {
				return ResponseEntity.notFound().build();
			}
		}
		
		return null;
	}
}
