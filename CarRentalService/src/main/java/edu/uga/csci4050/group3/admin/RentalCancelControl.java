package edu.uga.csci4050.group3.admin;

import javax.servlet.http.HttpServletRequest;

import edu.uga.csci4050.group3.core.InvalidUrlException;
import edu.uga.csci4050.group3.core.RentalStatus;
import edu.uga.csci4050.group3.core.RentalTransactionEntity;
import edu.uga.csci4050.group3.db.DatabaseAbstraction;
import edu.uga.csci4050.group3.db.RecordNotFoundException;

public class RentalCancelControl {

	public void cancel(HttpServletRequest request) throws InvalidUrlException, RecordNotFoundException{
		// Check that we have a UID
		if(!request.getParameterMap().containsKey("uid")){
			throw new InvalidUrlException();
		}
		
		// Cancel rental
		RentalTransactionEntity rental = DatabaseAbstraction.getRentalTransaction(request.getParameter("uid"));
		
		rental.setStatusEnum(RentalStatus.CANCELLED);
		
		DatabaseAbstraction.updateRentalTransaction(rental);
	}
}
