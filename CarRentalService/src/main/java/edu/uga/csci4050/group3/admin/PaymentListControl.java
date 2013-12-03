package edu.uga.csci4050.group3.admin;

import java.util.ArrayList;
import java.util.List;

import edu.uga.csci4050.group3.core.PaymentTransactionEntity;
import edu.uga.csci4050.group3.db.DatabaseAbstraction;
import edu.uga.csci4050.group3.db.RecordNotFoundException;

public class PaymentListControl {

	public List<PaymentTransactionEntity> getPayments(){
		
		try {
			return DatabaseAbstraction.getPaymentTransactions();
		} catch (RecordNotFoundException e) {
			return new ArrayList<PaymentTransactionEntity>();
		}
	}
}
