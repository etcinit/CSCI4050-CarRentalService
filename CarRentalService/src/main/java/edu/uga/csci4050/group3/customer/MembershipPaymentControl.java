package edu.uga.csci4050.group3.customer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	
	public boolean isLoggedIn(HttpServletRequest request, HttpServletResponse response){
		SessionManagement sessMan = new SessionManagement(request, response);
		
		return sessMan.isUserLoggedIn();
	}
	public void authenticate(HttpServletRequest request, HttpServletResponse response) throws InvalidInputException {
		// Get payment info from form
		Map<String,String[]> params = request.getParameterMap();
		
		String creditCard = params.get("creditCard")[0];
		String securityKey = params.get("securityKey")[0];
		
		if (!(creditCard.length() == 16 && securityKey.length() == 3)) {
			throw new InvalidInputException();
		} 
	}
	public void extendMembership(HttpServletRequest request, HttpServletResponse response) {
		SessionManagement sessMan = new SessionManagement(request, response);
		UserEntity user = null;
		try {
			user = DatabaseAbstraction.getUserByUsername(sessMan.getLoggedinUsername());
		} catch (AuthenticationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RecordNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (user.getMembershipExpiration() == 0 || (user.getMembershipExpiration() < DatabaseAbstraction.getTimestampFromDate(new Date()))) { // Expired or non-existent
			Date newDate = new Date();
			newDate.setMonth((newDate.getMonth() + 6));
			user.setMembershipExpirationDate(newDate);
		} else { // Not expired
			Date newDate = user.getMembershipExpirationDate();
			newDate.setMonth((newDate.getMonth() + 6));
			user.setMembershipExpirationDate(newDate);
		}
	}
	
	public double getMembershipFee(ServletContext context) {
		// Get membership price from database
		Settings settings = Settings.loadFromStorage(context);
		double value = settings.getMembershipFee();
		
		return value;
	}
}
