package edu.uga.csci4050.group3.customer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.core.AuthenticationException;
import edu.uga.csci4050.group3.core.UserEntity;
import edu.uga.csci4050.group3.db.DatabaseAbstraction;
import edu.uga.csci4050.group3.db.RecordNotFoundException;
import edu.uga.csci4050.group3.db.SessionManagement;

public class MembershipControl {
	
	public boolean customerHasMembership(HttpServletRequest request, HttpServletResponse response) {
		// Get customer from database
		SessionManagement sessMan = new SessionManagement(request, response);
		try {
			String currentUser = sessMan.getLoggedinUsername();
			UserEntity customer = DatabaseAbstraction.getUserByUsername(currentUser);
			
			// TODO 
			
			return false; // No membership
		} catch (RecordNotFoundException e) {
			e.printStackTrace();
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}
		return false;
	}
}
