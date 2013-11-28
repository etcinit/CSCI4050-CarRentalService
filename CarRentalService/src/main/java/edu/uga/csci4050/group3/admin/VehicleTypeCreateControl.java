package edu.uga.csci4050.group3.admin;

import javax.servlet.http.HttpServletRequest;

import edu.uga.csci4050.group3.core.InvalidInputException;
import edu.uga.csci4050.group3.core.VehicleTypeEntity;
import edu.uga.csci4050.group3.db.DatabaseAbstraction;

public class VehicleTypeCreateControl {

	public void create(HttpServletRequest request) throws InvalidInputException{
		
		// Load type data from form
		VehicleTypeEntity type = new VehicleTypeEntity();
		
		type.loadFromForm(request.getParameterMap());
		
		// Validate form data
		type.validate();
		
		// Send to database
		DatabaseAbstraction.putVehicleType(type);
	}
}
