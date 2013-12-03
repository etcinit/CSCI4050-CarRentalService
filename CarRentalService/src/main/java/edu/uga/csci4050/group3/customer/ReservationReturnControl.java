package edu.uga.csci4050.group3.customer;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import edu.uga.csci4050.group3.core.InvalidInputException;
import edu.uga.csci4050.group3.core.InvalidUrlException;
import edu.uga.csci4050.group3.core.PaymentReason;
import edu.uga.csci4050.group3.core.PaymentTransactionEntity;
import edu.uga.csci4050.group3.core.RentalStatus;
import edu.uga.csci4050.group3.core.RentalTransactionEntity;
import edu.uga.csci4050.group3.core.VehicleEntity;
import edu.uga.csci4050.group3.core.VehicleTypeEntity;
import edu.uga.csci4050.group3.db.DatabaseAbstraction;
import edu.uga.csci4050.group3.db.RecordNotFoundException;

public class ReservationReturnControl {

	public boolean isReturningPossible(HttpServletRequest request) throws InvalidUrlException, RecordNotFoundException{
		
		// Check that we have an UID
		if(!request.getParameterMap().containsKey("uid")){
			throw new InvalidUrlException();
		}
		
		// Get UID
		String uid = request.getParameter("uid");
		
		// Attempt to find rental
		RentalTransactionEntity rental = DatabaseAbstraction.getRentalTransaction(uid);
		
		// Check if we can return
		if(rental.getStatusEnum() == RentalStatus.ACTIVE){
			return true;
		}else{
			return false;
		}
	}
	
	public double getAdditionalCharges(HttpServletRequest request) throws InvalidUrlException, RecordNotFoundException{
		
		// Check that we have an UID
		if(!request.getParameterMap().containsKey("uid")){
			throw new InvalidUrlException();
		}
		
		// Get UID
		String uid = request.getParameter("uid");
		
		// Attempt to find rental
		RentalTransactionEntity rental = DatabaseAbstraction.getRentalTransaction(uid);
		
		// Calculate additional charges
		
		// If we are still in the rental period, there shouldn't be any
		if(rental.getEnd_date() >= DatabaseAbstraction.getTimestampFromDate(new Date()) && 
				rental.getStart_date() <= DatabaseAbstraction.getTimestampFromDate(new Date())){
			return 0.0;
		}
		
		// We need the vehicle type, so we need the vehicle first
		VehicleEntity vehicle = DatabaseAbstraction.getVehicle(rental.getVehicle());
		VehicleTypeEntity vtype = DatabaseAbstraction.getVehicleType(vehicle.getType());
		
		// Calculate how much time the customer exceeded the end date
		Date current_time = new Date();
		long difference = current_time.getTime() - rental.getEnd_dateDate().getTime();
		
		if(rental.getStart_date() > DatabaseAbstraction.getTimestampFromDate(new Date())) { // Returning before start date, but in the 1 hour grace period
			return vtype.getHourly_rate();
		}
		
		if(difference > 0) {
			// User is returning past return end date
			int hoursToCharge = (int)Math.ceil(TimeUnit.MILLISECONDS.toHours(difference));
			return (vtype.getHourly_rate() * (double)hoursToCharge) + 50.0;
		}
		
		return 0.0;
	}
	
	public void processReturn(HttpServletRequest request) throws InvalidUrlException, InvalidInputException, RecordNotFoundException{
		
		// Check that we have an UID
		if(!request.getParameterMap().containsKey("rentalUID")){
			throw new InvalidUrlException();
		}
		
		// Get values
		String uid = request.getParameter("rentalUID");
		
		// Get transaction
		RentalTransactionEntity rental = DatabaseAbstraction.getRentalTransaction(uid);
		
		// Create payment (if needed)
		double extraPayment = getAdditionalCharges(request);
		
		if(extraPayment > 0){
			// Check that payment information was provided
			
			// Check that we have the credit card number
			if(!request.getParameterMap().containsKey("rentalCardNumber")){
				throw new InvalidUrlException();
			}
			
			// Check that we have the CSV
			if(!request.getParameterMap().containsKey("rentalCSV")){
				throw new InvalidUrlException();
			}
			
			String cardNumber = request.getParameter("rentalCardNumber");
			String cardCSV = request.getParameter("rentalCSV");
			
			// Validate input
			InvalidInputException invalidEx = new InvalidInputException();
			String cardPattern = "[0-9]{13,19}|([0-9]{4,8}[-]{1}){3,5}[0-9]{4,8}";
			String csvPattern = "[0-9]{3,4}";
			
			PaymentTransactionEntity payment = new PaymentTransactionEntity();
			
			if(!cardNumber.matches(cardPattern)){
				invalidEx.addMessage("Invalid card number format. Expected: XXXX-XXXX-XXXX-XXXX");
			}
			
			if(!cardCSV.matches(csvPattern)){
				invalidEx.addMessage("Invalid CSV code format. Expected: XXX or XXXX");
			}
			
			if(invalidEx.countMessages() > 0){
				throw invalidEx;
			}
			
			// Set details
			payment.setDateDate(new Date());
			payment.setReasonFromEnum(PaymentReason.EXTRA);
			payment.setDescription("CSV: " + cardCSV);
			payment.setUser(rental.getUser());
			payment.setMethod("Card");
			
			// Send to database
			DatabaseAbstraction.putPaymentTransaction(payment);
		}
		
		// Update transaction to mark as returned
		rental.setStatusEnum(RentalStatus.RETURNED);
		
		// Add comments
		if(!request.getParameterMap().containsKey("rentalComments")){
			rental.setComments("No comments");
		}else{
			rental.setComments(request.getParameter("rentalComments"));
		}
		
		DatabaseAbstraction.updateRentalTransaction(rental);
	}
}
