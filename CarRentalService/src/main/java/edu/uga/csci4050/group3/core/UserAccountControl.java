package edu.uga.csci4050.group3.core;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.db.DatabaseAbstraction;
import edu.uga.csci4050.group3.db.RecordNotFoundException;
import edu.uga.csci4050.group3.db.SessionManagement;

public class UserAccountControl {
	public void update(HttpServletRequest request, HttpServletResponse response) throws InvalidInputException, RecordNotFoundException, AuthenticationException{
		
		// Get the current user
		SessionManagement sessMan = new SessionManagement(request, response);
		InvalidInputException iie = new InvalidInputException();
		UserEntity currentInfo = null, newInfo = null;
		newInfo = new UserEntity();
		
		
		try {
			currentInfo = DatabaseAbstraction.getUserByUsername(sessMan.getLoggedinUsername());
		} catch (AuthenticationException e1) {
			e1.printStackTrace();
		}
		
		// Get user data from form
		newInfo.loadFromForm(request.getParameterMap());
				
		
		// Check username is not taken if it's different
		if (!newInfo.getUsername().equals(currentInfo.getUsername())) {
			try {
				UserEntity check = DatabaseAbstraction.getUserByUsername(newInfo.getUsername());
				iie.addMessage("Username is taken");
			} catch (RecordNotFoundException e) {
				// This is good. It's what is supposed to happen
			}
		}
				
		// Check user is over 21 years old
		int diff = DatabaseAbstraction.getTimestampFromDate(new Date()) - newInfo.getDateofbirth();
		if(diff < 16675200){
			iie.addMessage("In order to register you need to be at least 21 years old");
		}
				
		if(iie.countMessages() > 0){
			throw iie;
		}
		
		currentInfo.loadFromForm(request.getParameterMap());
		
		// Send to database
		DatabaseAbstraction.updateUser(currentInfo);
		
		// Update session
		sessMan.updateLoggedinUsername(currentInfo.getUsername());
	}
}
