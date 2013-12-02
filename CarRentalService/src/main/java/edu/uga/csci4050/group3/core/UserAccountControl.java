package edu.uga.csci4050.group3.core;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.db.DatabaseAbstraction;
import edu.uga.csci4050.group3.db.RecordNotFoundException;
import edu.uga.csci4050.group3.db.SessionManagement;

public class UserAccountControl {
	public void update(HttpServletRequest request, HttpServletResponse response) throws InvalidInputException, RecordNotFoundException{
		
		// Get the current user
		SessionManagement sessMan = new SessionManagement(request, response);
		UserEntity user = null;
		try {
			user = DatabaseAbstraction.getUserByUsername(sessMan.getLoggedinUsername());
		} catch (AuthenticationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Get user data from form
		InvalidInputException iie = new InvalidInputException();
		user.loadFromForm(request.getParameterMap());
		
		// Check username is not taken
		try {
			UserEntity check = DatabaseAbstraction.getUserByUsername(user.getUsername());
			iie.addMessage("Username is taken");
		} catch (RecordNotFoundException e) {
			// This is good. It's what is supposed to happen
		}
		
		// Check user is over 21 years old
		int diff = DatabaseAbstraction.getTimestampFromDate(new Date()) - user.getDateofbirth();
		if(diff < 16675200){
			iie.addMessage("In order to register you need to be at least 21 years old");
		}
		
		if(iie.countMessages() > 0){
			throw iie;
		}
		
		// Validate
		user.validate();
		
		// Send to database
		DatabaseAbstraction.putUser(user);
	}
}
