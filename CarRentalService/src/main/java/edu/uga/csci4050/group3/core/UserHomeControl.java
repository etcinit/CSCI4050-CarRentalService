package edu.uga.csci4050.group3.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.db.SessionException;
import edu.uga.csci4050.group3.db.SessionManagement;

public class UserHomeControl {

	public boolean isLoggedIn(HttpServletRequest request, HttpServletResponse response){
		SessionManagement sessMan = new SessionManagement(request, response);
		
		return sessMan.isUserLoggedIn();
	}
	
	public String getRoleString(HttpServletRequest request, HttpServletResponse response){
		SessionManagement sessMan = new SessionManagement(request, response);
		
		String role;
		try {
			role = sessMan.getUserRole().toString();
		} catch (SessionException e) {
			role = "Unknown";
		}
		
		return role;
	}
}
