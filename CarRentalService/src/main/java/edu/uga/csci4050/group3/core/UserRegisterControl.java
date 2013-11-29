package edu.uga.csci4050.group3.core;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import edu.uga.csci4050.group3.db.DatabaseAbstraction;
import edu.uga.csci4050.group3.db.RecordNotFoundException;

public class UserRegisterControl {

	public void register(HttpServletRequest request) throws InvalidInputException{
		// Get user data from form
		UserEntity user = new UserEntity();
		InvalidInputException iie = new InvalidInputException();
		user.loadFromForm(request.getParameterMap());
		
		// Set role as customer
		user.setRoleFromEnum(UserType.CUSTOMER);
		
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
