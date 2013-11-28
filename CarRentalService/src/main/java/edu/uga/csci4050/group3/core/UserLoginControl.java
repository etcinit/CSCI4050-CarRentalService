package edu.uga.csci4050.group3.core;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import edu.uga.csci4050.group3.db.DatabaseAbstraction;
import edu.uga.csci4050.group3.db.RecordNotFoundException;

public class UserLoginControl {

	public boolean login(HttpServletRequest request) throws InvalidInputException, AuthenticationException{
		// Get credentials from form
		Map<String,String[]> params = request.getParameterMap();
		
		// Check that we got all the fields
		if(!params.containsKey("userUsername")){
			throw new InvalidInputException();
		}
		
		if(!params.containsKey("userPassword")){
			throw new InvalidInputException();
		}
		
		String username = params.get("userUsername")[0];
		String password = params.get("userPassword")[0];
		
		// Try to find username on db
		try {
			UserEntity user = DatabaseAbstraction.getUserByUsername(username);
			
			// Compare password
			if(!user.getPassword().equals(password)){
				throw new AuthenticationException();
			}
			
			// Create session
		} catch (RecordNotFoundException e) {
			// User not found throw auth error
			throw new AuthenticationException();
		}
		
		return true;
	}
}
