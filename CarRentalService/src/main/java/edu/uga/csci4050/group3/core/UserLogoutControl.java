package edu.uga.csci4050.group3.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.db.SessionManagement;

public class UserLogoutControl {

	public void logout(HttpServletRequest request, HttpServletResponse response){
		SessionManagement sessMan = new SessionManagement(request, response);
		
		sessMan.invalidateSession();
	}
}
