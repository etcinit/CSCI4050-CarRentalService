package edu.uga.csci4050.group3.admin;

import edu.uga.csci4050.group3.core.Vehicle;
import edu.uga.csci4050.group3.db.DatabaseAbstraction;

public class VehicleCreateControl {
	public void create(){
		Vehicle vh = new Vehicle();
		DatabaseAbstraction.putVehicle(vh);
	}
}
