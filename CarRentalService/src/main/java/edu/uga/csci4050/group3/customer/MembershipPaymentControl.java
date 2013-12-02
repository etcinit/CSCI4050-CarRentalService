package edu.uga.csci4050.group3.customer;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.core.AuthenticationException;
import edu.uga.csci4050.group3.core.InvalidInputException;
import edu.uga.csci4050.group3.core.UserEntity;
import edu.uga.csci4050.group3.db.DatabaseAbstraction;
import edu.uga.csci4050.group3.db.RecordNotFoundException;
import edu.uga.csci4050.group3.db.SessionException;
import edu.uga.csci4050.group3.db.SessionManagement;
import edu.uga.csci4050.group3.db.Settings;

public class MembershipPaymentControl {
	public void authenticate(HttpServletRequest request, HttpServletResponse response) throws InvalidInputException {
		// Get payment info from form
		Map<String,String[]> params = request.getParameterMap();
		
		String creditCard = params.get("creditCard")[0];
		String securityKey = params.get("securityKey")[0];
		
		if (!(creditCard.length() == 16 && securityKey.length() == 3)) {
			throw new InvalidInputException();
		} 
	}
	
	public double getMembershipFee(ServletContext context) {
		// Get membership price from database
		Settings settings = Settings.loadFromStorage(context);
		double value = settings.getMembershipFee();
		
		return value;
	}
}
