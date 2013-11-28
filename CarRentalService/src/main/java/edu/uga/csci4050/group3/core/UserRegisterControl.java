package edu.uga.csci4050.group3.core;

import javax.servlet.http.HttpServletRequest;

import edu.uga.csci4050.group3.db.DatabaseAbstraction;

public class UserRegisterControl {

	public void register(HttpServletRequest request) throws InvalidInputException{
		// Get user data from form
		UserEntity user = new UserEntity();
		
		// Set role as customer
		user.setRoleFromEnum(UserType.CUSTOMER);
		
		// Validate
		user.validate();
		
		// Send to database
		DatabaseAbstraction.putUser(user);
	}
}
