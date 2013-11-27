package edu.uga.csci4050.group3.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.uga.csci4050.group3.db.DatabaseAbstraction;
import edu.uga.csci4050.group3.db.RecordNotFoundException;

public class VehicleListControl {
	public List<VehicleEntity> getList(Map<String, String[]> map){
		try {
			return DatabaseAbstraction.getVehicles();
		} catch (RecordNotFoundException e) {
			return new ArrayList<VehicleEntity>();
		}
	}
}
