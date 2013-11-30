package edu.uga.csci4050.group3.admin;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import edu.uga.csci4050.group3.core.InvalidInputException;
import edu.uga.csci4050.group3.db.Settings;

public class ServiceManagementControl {

	public double getFee(ServletContext context){
		Settings settings = Settings.loadFromStorage(context);
		
		return settings.getMembershipFee();
	}
	
	public void setFee(ServletContext context, HttpServletRequest request) throws InvalidInputException{
		Settings settings = Settings.loadFromStorage(context);
		
		if(!request.getParameterMap().containsKey("serviceFee")){
			throw new InvalidInputException();
		}
		
		settings.setMembershipFee(new Double(request.getParameter("serviceFee")));
		
		try {
			Settings.saveToStorage(settings, context);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
