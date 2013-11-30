package edu.uga.csci4050.group3.admin;

import javax.servlet.http.HttpServletRequest;

import edu.uga.csci4050.group3.core.InvalidInputException;
import edu.uga.csci4050.group3.core.LocationEntity;
import edu.uga.csci4050.group3.db.DatabaseAbstraction;

public class LocationCreateControl {

	public void create(HttpServletRequest request) throws InvalidInputException{
		LocationEntity location = new LocationEntity();
		
		location.loadFromForm(request.getParameterMap());
		
		location.validate();
		
		DatabaseAbstraction.putLocation(location);
	}
}
