package edu.uga.csci4050.group3.admin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import edu.uga.csci4050.group3.core.InvalidUrlException;
import edu.uga.csci4050.group3.db.DatabaseAbstraction;
import edu.uga.csci4050.group3.db.RecordNotFoundException;

public class VehicleTypeDeleteControl {

	public void delete(HttpServletRequest request) throws InvalidUrlException, RecordNotFoundException{
		// Get UID from URL
		Map<String, String[]> params = request.getParameterMap();
		
		if(!params.containsKey("uid")){
			throw new InvalidUrlException();
		}
		
		String uid = params.get("uid")[0];
		
		// Delete UID from database (if it exists)
		DatabaseAbstraction.deleteVehicleType(uid);
	}
}
