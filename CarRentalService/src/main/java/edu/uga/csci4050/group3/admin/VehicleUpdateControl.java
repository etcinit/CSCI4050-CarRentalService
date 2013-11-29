package edu.uga.csci4050.group3.admin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import edu.uga.csci4050.group3.core.InvalidInputException;
import edu.uga.csci4050.group3.core.InvalidUrlException;
import edu.uga.csci4050.group3.core.VehicleEntity;
import edu.uga.csci4050.group3.db.DatabaseAbstraction;
import edu.uga.csci4050.group3.db.RecordNotFoundException;

public class VehicleUpdateControl {

	public void updateVehicle(HttpServletRequest request) throws InvalidInputException, InvalidUrlException{
		VehicleEntity vehicle = new VehicleEntity();
		
		// Load vehicle data from form
		vehicle.loadFromForm(request.getParameterMap());
		
		// Get UID from URL
		Map<String, String[]> params = request.getParameterMap();
		
		if(!params.containsKey("uid")){
			throw new InvalidUrlException();
		}
		
		vehicle.setUid(params.get("uid")[0]);
		
		// Validate vehicle
		vehicle.validate();
		
		// Send vehicle to database
		DatabaseAbstraction.updateVehicle(vehicle);
	}
	
	public boolean isDbPopulated(){
		// Check that there is at least one location
		try {
			DatabaseAbstraction.getLocations();
		} catch (RecordNotFoundException e) {
			// There are zero locations
			return false;
		}
		
		// Check that there is at least one type
		try {
			DatabaseAbstraction.getVehicleTypes();
		} catch (RecordNotFoundException e) {
			// There are zero types
			return false;
		}
		
		return true;
	}
	
	public VehicleEntity getVehicle(HttpServletRequest request) throws InvalidUrlException, RecordNotFoundException{
		if(!request.getParameterMap().containsKey("uid")){
			throw new InvalidUrlException();
		}
		
		return DatabaseAbstraction.getVehicle(request.getParameter("uid"));
	}
}
