package edu.uga.csci4050.group3.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.db.DatabaseAbstraction;
import edu.uga.csci4050.group3.db.RecordNotFoundException;
import edu.uga.csci4050.group3.db.SessionException;
import edu.uga.csci4050.group3.db.SessionManagement;

public class LocationViewControl {

	public LocationEntity getLocation(HttpServletRequest request) throws InvalidUrlException, RecordNotFoundException{
		// Check that we have a UID
		if(!request.getParameterMap().containsKey("uid")){
			throw new InvalidUrlException();
		}
		
		// Attempt to get the location
		LocationEntity loc = DatabaseAbstraction.getLocation(request.getParameter("uid"));
		
		return loc;
	}
	
	public boolean isAdmin(HttpServletRequest request, HttpServletResponse response){
		SessionManagement sessMan = new SessionManagement(request, response);
		
		if(sessMan.isUserLoggedIn()){
			try {
				if(sessMan.getUserRole() == UserType.ADMIN){
					return true;
				}else{
					return false;
				}
			} catch (SessionException e) {
				return false;
			}
		}else{
			return false;
		}
	}
}
