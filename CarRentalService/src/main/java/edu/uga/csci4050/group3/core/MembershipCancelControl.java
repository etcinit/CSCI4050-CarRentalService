package edu.uga.csci4050.group3.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.db.DatabaseAbstraction;
import edu.uga.csci4050.group3.db.RecordNotFoundException;
import edu.uga.csci4050.group3.db.SessionException;
import edu.uga.csci4050.group3.db.SessionManagement;

public class MembershipCancelControl {
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
	
	public boolean processCancellation(HttpServletRequest request, HttpServletResponse response) throws InvalidInputException {
		
		// Try to get username
		String username = null;
		try {
			username = new SessionManagement(request, response).getLoggedinUsername();
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}
		
		// Check that we got a confirm GET parameter
		if(!request.getParameterMap().containsKey("confirm") || !request.getParameter("confirm").equals("yes")){
			return false;
		}
		
		// Process cancellation
		UserEntity user = null;
		try {
			user = DatabaseAbstraction.getUserByUsername(username);
			if(user.getMembershipExpiration() == 0){
				InvalidInputException invalidEx = new InvalidInputException();
				invalidEx.addMessage("Your membership is already terminated");
				throw invalidEx;
			}
		} catch (RecordNotFoundException e) {
			e.printStackTrace();
		}
		
		user.setMembershipExpiration(0);
		DatabaseAbstraction.updateUser(user);
		
		return true;
	}
}
