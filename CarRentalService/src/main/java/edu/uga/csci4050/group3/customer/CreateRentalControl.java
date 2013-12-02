package edu.uga.csci4050.group3.customer;

import javax.servlet.http.HttpServletRequest;

import edu.uga.csci4050.group3.core.*;
import edu.uga.csci4050.group3.admin.*;
import edu.uga.csci4050.group3.db.*;

public class CreateRentalControl {

	public VehicleEntity getVehicle(HttpServletRequest request) throws InvalidUrlException, RecordNotFoundException{
		// Check that we have a UID
		if(!request.getParameterMap().containsKey("uid")){
			throw new InvalidUrlException();
		}
		
		// Attempt to get the location
		VehicleEntity veh = DatabaseAbstraction.getVehicle(request.getParameter("uid"));
		
		return veh;
	}
	
	
}
