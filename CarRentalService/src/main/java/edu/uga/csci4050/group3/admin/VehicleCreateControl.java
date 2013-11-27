package edu.uga.csci4050.group3.admin;

import java.util.Map;

import edu.uga.csci4050.group3.core.InvalidInputException;
import edu.uga.csci4050.group3.core.VehicleEntity;
import edu.uga.csci4050.group3.db.DatabaseAbstraction;

public class VehicleCreateControl {
	public void create(){
		VehicleEntity vh = new VehicleEntity();
		DatabaseAbstraction.putVehicle(vh);
	}
	
	public void create(Map<String, String[]> map) throws InvalidInputException{
		VehicleEntity vh = new VehicleEntity();
		
		// Get form data a put it into a vehicle object
		vh.loadFromForm(map);
		
		// Validate form data
		vh.validate();
		
		// Check if we have any validation errors
		
		DatabaseAbstraction.putVehicle(vh);
	}
}
