package edu.uga.csci4050.group3.core;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.db.DatabaseAbstraction;
import edu.uga.csci4050.group3.db.RecordNotFoundException;
import edu.uga.csci4050.group3.db.SessionException;
import edu.uga.csci4050.group3.db.SessionManagement;

public class LocationListControl {
	
	public List<LocationEntity> getList(){
		try {
			List<LocationEntity> list = DatabaseAbstraction.getLocations();
			return list;
		} catch (RecordNotFoundException e) {
			return new ArrayList<LocationEntity>();
		}
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
	
	public boolean isCustomer(HttpServletRequest request, HttpServletResponse response){
		SessionManagement sessMan = new SessionManagement(request, response);
		
		if(sessMan.isUserLoggedIn()){
			try {
				if(sessMan.getUserRole() == UserType.CUSTOMER){
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
}
