package edu.uga.csci4050.group3.customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.core.AuthenticationException;
import edu.uga.csci4050.group3.core.InvalidUrlException;
import edu.uga.csci4050.group3.core.RentalStatus;
import edu.uga.csci4050.group3.core.RentalTransactionEntity;
import edu.uga.csci4050.group3.db.DatabaseAbstraction;
import edu.uga.csci4050.group3.db.RecordNotFoundException;
import edu.uga.csci4050.group3.db.SessionManagement;

public class ReservationCancelControl {

	public void cancel(HttpServletRequest request, HttpServletResponse response) throws InvalidUrlException, RecordNotFoundException, AuthenticationException{
		
		// Check that we have a UID
		if(!request.getParameterMap().containsKey("uid")){
			throw new InvalidUrlException();
		}
		
		// Cancel rental
		RentalTransactionEntity rental = DatabaseAbstraction.getRentalTransaction(request.getParameter("uid"));
		
		// Check that the user owns the rental
		String username = new SessionManagement(request, response).getLoggedinUsername();
		
		if(!DatabaseAbstraction.getUserByUsername(username).getUid().equals(rental.getUser())){
			throw new AuthenticationException();
		}
		
		rental.setStatusEnum(RentalStatus.CANCELLED);
		
		DatabaseAbstraction.updateRentalTransaction(rental);
	}
}
