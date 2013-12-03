package edu.uga.csci4050.group3.customer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.core.AuthenticationException;
import edu.uga.csci4050.group3.core.InvalidInputException;
import edu.uga.csci4050.group3.core.InvalidUrlException;
import edu.uga.csci4050.group3.core.PaymentReason;
import edu.uga.csci4050.group3.core.PaymentTransactionEntity;
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
	
	public void extendMembership(HttpServletRequest request, HttpServletResponse response) throws InvalidUrlException, InvalidInputException, RecordNotFoundException, AuthenticationException {
		
		// -- Check that payment information was provided --
		
		// Check that we have the credit card number
		if(!request.getParameterMap().containsKey("membershipCardNumber")){
			throw new InvalidUrlException();
		}
		
		// Check that we have the CSV
		if(!request.getParameterMap().containsKey("membershipCSV")){
			throw new InvalidUrlException();
		}
		
		String cardNumber = request.getParameter("membershipCardNumber");
		String cardCSV = request.getParameter("membershipCSV");
		
		// Validate input
		InvalidInputException invalidEx = new InvalidInputException();
		String cardPattern = "[0-9]{13,19}|([0-9]{4,8}[-]{1}){3,5}[0-9]{4,8}";
		String csvPattern = "[0-9]{3,4}";
		
		if(!cardNumber.matches(cardPattern)){
			invalidEx.addMessage("Invalid card number format. Expected: XXXX-XXXX-XXXX-XXXX");
		}
		
		if(!cardCSV.matches(csvPattern)){
			invalidEx.addMessage("Invalid CSV code format. Expected: XXX or XXXX");
		}
		
		if(invalidEx.countMessages() > 0){
			throw invalidEx;
		}
		
		// -- Attempt to obtain the user entity --
		
		SessionManagement sessMan = new SessionManagement(request, response);
		UserEntity user = DatabaseAbstraction.getUserByUsername(sessMan.getLoggedinUsername());
		
		// -- Time calculations --
		
		// Check that the user's membership is not longer than 12 months
		// We just need to check that it is over 6 months
		Date currentTime = new Date();
		if(user.getMembershipExpirationDate().getTime() > currentTime.getTime()){
			// 12 months * 30 days 
			if(TimeUnit.MILLISECONDS.toDays(user.getMembershipExpirationDate().getTime() - currentTime.getTime()) > (6*30)){
				invalidEx.addMessage("Your membership is already at the maximum extension. You have to wait until it goes below 6 months for an extension.");
				throw invalidEx;
			}
		}
		
<<<<<<< HEAD
		Date currentExpiration;
		if (user.getMembershipExpiration() == 0 || 
				(user.getMembershipExpiration() < DatabaseAbstraction.getTimestampFromDate(new Date()))) { // Expired or non-existent
			currentExpiration = new Date();
		} else { // Not expired
			currentExpiration = user.getMembershipExpirationDate();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(currentExpiration);
		c.add(Calendar.MONTH, 6);
		Date newDate = c.getTime();
		user.setMembershipExpirationDate(newDate);
=======
		// Calculate new time
		long newTime = 0;
		if(user.getMembershipExpirationDate().getTime() < currentTime.getTime()){
			// Outside of extension period
			newTime = currentTime.getTime() + TimeUnit.DAYS.toMillis(6*30);
		}else{
			// Inside extension period
			newTime = user.getMembershipExpirationDate().getTime() + TimeUnit.DAYS.toMillis(6*30);
		}
		
		// -- Process payment --
		PaymentTransactionEntity payment = new PaymentTransactionEntity();
		
		payment.setDateDate(new Date());
		payment.setReasonFromEnum(PaymentReason.MEMBERSHIP);
		payment.setDescription("CSV: " + cardCSV);
		payment.setUser(user.getUid());
		payment.setMethod("Card");
		
		// Send to database
		DatabaseAbstraction.putPaymentTransaction(payment);
		
		// -- Update membership on database --
		
		// Last steps
		user.setMembershipExpirationDate(new Date(newTime));
		
		// Update user on the database
		DatabaseAbstraction.updateUser(user);
>>>>>>> f6e1faa07c06703983ef728d294f282db006aab6
	}
	
	public double getMembershipFee(ServletContext context) {
		// Get membership price from database
		Settings settings = Settings.loadFromStorage(context);
		double value = settings.getMembershipFee();
		
		return value;
	}
	
	public Date getExpirationDate(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, RecordNotFoundException{
		
		// Get user
		String username = new SessionManagement(request, response).getLoggedinUsername();
		
		UserEntity user = DatabaseAbstraction.getUserByUsername(username);
		
		return user.getMembershipExpirationDate();
	}
	
	public String getExpirationDateString(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, RecordNotFoundException{
		SimpleDateFormat sdftime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		
		return sdftime.format(getExpirationDate(request, response));
	}
}
