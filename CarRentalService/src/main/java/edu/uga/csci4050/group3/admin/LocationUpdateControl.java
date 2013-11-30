package edu.uga.csci4050.group3.admin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import edu.uga.csci4050.group3.core.InvalidInputException;
import edu.uga.csci4050.group3.core.InvalidUrlException;
import edu.uga.csci4050.group3.core.LocationEntity;
import edu.uga.csci4050.group3.db.DatabaseAbstraction;
import edu.uga.csci4050.group3.db.RecordNotFoundException;

public class LocationUpdateControl {

	public void update(HttpServletRequest request) throws InvalidUrlException, InvalidInputException{
		LocationEntity location = new LocationEntity();
		
		// Load vehicle data from form
		location.loadFromForm(request.getParameterMap());
		
		// Get UID from URL
		Map<String, String[]> params = request.getParameterMap();
		
		if(!params.containsKey("uid")){
			throw new InvalidUrlException();
		}
		
		location.setUid(params.get("uid")[0]);
		
		// Validate vehicle
		location.validate();
		
		// Send vehicle to database
		DatabaseAbstraction.updateLocation(location);
	}
	
	public LocationEntity getLocation(HttpServletRequest request) throws InvalidUrlException, RecordNotFoundException{
		if(!request.getParameterMap().containsKey("uid")){
			throw new InvalidUrlException();
		}
		
		return DatabaseAbstraction.getLocation(request.getParameter("uid"));
	}
}
