package edu.uga.csci4050.group3.admin;

import javax.servlet.http.HttpServletRequest;

import edu.uga.csci4050.group3.core.InvalidUrlException;
import edu.uga.csci4050.group3.core.UserEntity;
import edu.uga.csci4050.group3.core.UserType;
import edu.uga.csci4050.group3.db.DatabaseAbstraction;
import edu.uga.csci4050.group3.db.RecordNotFoundException;

public class UserUpdateRoleControl {

	public void updateRole(HttpServletRequest request) throws InvalidUrlException, RecordNotFoundException{
		
		// Check that we have a UID
		if(!request.getParameterMap().containsKey("uid")){
			throw new InvalidUrlException();
		}
		
		// Find user on DB (if it exists)
		UserEntity user = DatabaseAbstraction.getUser(request.getParameter("uid"));
		
		// Update role
		if(user.getRoleEnum() == UserType.ADMIN){
			user.setRoleFromEnum(UserType.CUSTOMER);
		}else{
			user.setRoleFromEnum(UserType.ADMIN);
		}
		
		DatabaseAbstraction.updateUser(user);
	}
}
