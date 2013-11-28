package edu.uga.csci4050.group3.admin;

import java.util.ArrayList;
import java.util.List;

import edu.uga.csci4050.group3.core.VehicleTypeEntity;
import edu.uga.csci4050.group3.db.DatabaseAbstraction;
import edu.uga.csci4050.group3.db.RecordNotFoundException;

public class VehicleTypeListControl {

	public List<VehicleTypeEntity> list(){
		// Get list from database
		try {
			List<VehicleTypeEntity> types = DatabaseAbstraction.getVehicleTypes();
			
			return types;
		} catch (RecordNotFoundException e) {
			// This means we got 0 vehicle types
			return new ArrayList<VehicleTypeEntity>();
		}
	}
	
	public int count(){
		// Get list from database
		try {
			List<VehicleTypeEntity> types = DatabaseAbstraction.getVehicleTypes();
			
			return types.size();
		} catch (RecordNotFoundException e) {
			// This means we got 0 vehicle types
			return 0;
		}
	}
}
