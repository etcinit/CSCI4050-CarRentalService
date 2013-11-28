package edu.uga.csci4050.group3.admin;

import edu.uga.csci4050.group3.db.DatabaseAbstraction;
import edu.uga.csci4050.group3.db.RecordNotFoundException;

public class VehicleDeleteControl {

	public void deleteVehicle(String UID) throws RecordNotFoundException{
		DatabaseAbstraction.deleteVehicle(UID);
	}
}
