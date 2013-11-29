package edu.uga.csci4050.group3.admin;

import java.util.ArrayList;
import java.util.List;

import edu.uga.csci4050.group3.core.UserEntity;
import edu.uga.csci4050.group3.db.DatabaseAbstraction;
import edu.uga.csci4050.group3.db.RecordNotFoundException;

public class UserListControl {

	public List<UserEntity> list(){
		// Get list from database
		try {
			List<UserEntity> types = DatabaseAbstraction.getUsers();
			
			return types;
		} catch (RecordNotFoundException e) {
			// This means we got 0 vehicle types
			return new ArrayList<UserEntity>();
		}
	}
	
	public int count(){
		// Get list from database
		try {
			List<UserEntity> types = DatabaseAbstraction.getUsers();
			
			return types.size();
		} catch (RecordNotFoundException e) {
			// This means we got 0 vehicle types
			return 0;
		}
	}
}
