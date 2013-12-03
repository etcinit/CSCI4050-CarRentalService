package edu.uga.csci4050.group3.customer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.core.AuthenticationException;
import edu.uga.csci4050.group3.core.RentalTransactionEntity;
import edu.uga.csci4050.group3.db.DatabaseAbstraction;
import edu.uga.csci4050.group3.db.RecordNotFoundException;
import edu.uga.csci4050.group3.db.SessionManagement;

public class ReservationListControl {

	public List<RentalTransactionEntity> getList(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, RecordNotFoundException{
		
		// Check if we have a username
		String username = new SessionManagement(request,response).getLoggedinUsername();
		
		// Resolve user UID
		String uid = DatabaseAbstraction.getUserByUsername(username).getUid();
		
		try {
			List<RentalTransactionEntity> list = DatabaseAbstraction.getRentalTransactionsForUser(uid);
			return list;
		} catch (RecordNotFoundException e) {
			return new ArrayList<RentalTransactionEntity>();
		}
	}
}
