package edu.uga.csci4050.group3.admin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import edu.uga.csci4050.group3.core.InvalidInputException;
import edu.uga.csci4050.group3.core.InvalidUrlException;
import edu.uga.csci4050.group3.core.VehicleEntity;
import edu.uga.csci4050.group3.db.DatabaseAbstraction;

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
}
