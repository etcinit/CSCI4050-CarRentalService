package edu.uga.csci4050.group3.admin;

import javax.servlet.http.HttpServletRequest;

import edu.uga.csci4050.group3.core.InvalidUrlException;
import edu.uga.csci4050.group3.db.DatabaseAbstraction;
import edu.uga.csci4050.group3.db.RecordNotFoundException;

public class LocationDeleteControl {

	public void delete(HttpServletRequest request) throws RecordNotFoundException, InvalidUrlException {
		if(!request.getParameterMap().containsKey("uid")){
			throw new InvalidUrlException();
		}
		
		String UID = request.getParameter("UID");
		
		DatabaseAbstraction.deleteLocation(UID);
	}
}
