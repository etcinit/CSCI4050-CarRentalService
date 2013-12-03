package edu.uga.csci4050.group3.customer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import edu.uga.csci4050.group3.core.*;
import edu.uga.csci4050.group3.admin.*;
import edu.uga.csci4050.group3.db.*;
import edu.uga.csci4050.group3.template.SimpleTemplate;

public class VehicleRentControl {

	public VehicleEntity getVehicle(HttpServletRequest request) throws InvalidUrlException, RecordNotFoundException {
		// Check that we have a UID
		if(!request.getParameterMap().containsKey("uid")){
			if(!request.getParameterMap().containsKey("rentalVehicleUID")){
				throw new InvalidUrlException();
			}else{
				return DatabaseAbstraction.getVehicle(request.getParameter("rentalVehicleUID"));
			}
		}
		// Attempt to get the vehicle
		VehicleEntity veh = DatabaseAbstraction.getVehicle(request.getParameter("uid"));
		
		return veh;
	}
	
	public boolean isVehicleAvailable(HttpServletRequest request) throws RecordNotFoundException, InvalidUrlException, InvalidInputException{
		
		Date start_date = new Date();
		Date end_date = new Date();
		
		// Check that we have a UID
		if(!request.getParameterMap().containsKey("rentalVehicleUID")){
			throw new InvalidUrlException();
		}
		
		if(!request.getParameterMap().containsKey("rentalStartDate")){
			throw new InvalidUrlException();
		}
		
		if(!request.getParameterMap().containsKey("rentalEndDate")){
			throw new InvalidUrlException();
		}
		
		// Parse dates
		InvalidInputException invalidEx = new InvalidInputException();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		
		try {
			start_date = sdf.parse(request.getParameter("rentalStartDate"));
		} catch (ParseException e) {
			invalidEx.addMessage("Invalid start date format");
		}
		
		try {
			end_date = sdf.parse(request.getParameter("rentalEndDate"));
		} catch (ParseException e) {
			invalidEx.addMessage("Invalid end date format");
		}
		
		if(invalidEx.countMessages() > 0){
			throw invalidEx;
		}
		
		// Attempt to get the vehicle
		VehicleEntity veh = DatabaseAbstraction.getVehicle(request.getParameter("rentalVehicleUID"));
		
		try{
			List<RentalTransactionEntity> conflicts = DatabaseAbstraction.getConflictingRentalTransactions(veh.getUid(), start_date, end_date);
			return false;
		}
		catch(RecordNotFoundException ex){
			return true;
		}
	}
	
	public VehicleTypeEntity getType(HttpServletRequest request) throws InvalidUrlException, RecordNotFoundException{
		if(!request.getParameterMap().containsKey("rentalVehicleUID")){
			throw new InvalidUrlException();
		}
		
		// Get vehicle first
		VehicleEntity vehicle = DatabaseAbstraction.getVehicle(request.getParameter("rentalVehicleUID"));
		
		return DatabaseAbstraction.getVehicleType(vehicle.getType());
	}
	
	public double getTotalAmount(HttpServletRequest request) throws RecordNotFoundException, InvalidInputException, InvalidUrlException{
		
		Date start_date = new Date();
		Date end_date = new Date();
		
		// Check that we have a UID
		if(!request.getParameterMap().containsKey("rentalVehicleUID")){
			throw new InvalidUrlException();
		}
		
		if(!request.getParameterMap().containsKey("rentalStartDate")){
			throw new InvalidUrlException();
		}
		
		if(!request.getParameterMap().containsKey("rentalEndDate")){
			throw new InvalidUrlException();
		}
		
		// Parse dates
		InvalidInputException invalidEx = new InvalidInputException();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		
		try {
			start_date = sdf.parse(request.getParameter("rentalStartDate"));
		} catch (ParseException e) {
			invalidEx.addMessage("Invalid start date format");
		}
		
		try {
			end_date = sdf.parse(request.getParameter("rentalEndDate"));
		} catch (ParseException e) {
			invalidEx.addMessage("Invalid end date format");
		}
		
		if(invalidEx.countMessages() > 0){
			throw invalidEx;
		}
		
		// Attempt to get the vehicle
		VehicleEntity veh = DatabaseAbstraction.getVehicle(request.getParameter("rentalVehicleUID"));
		
		VehicleTypeEntity vtype = DatabaseAbstraction.getVehicleType(veh.getType());
		
		// Calculate time diff
		long diff = end_date.getTime() - start_date.getTime();
		
		if(diff <= 0){
			throw new InvalidInputException();
		}
		
		if(TimeUnit.MILLISECONDS.toHours(diff) >= 24){
			// Charge daily
			return vtype.getDaily_rate() * Math.ceil(TimeUnit.MILLISECONDS.toDays(diff));
		}else{
			// Charge hourly
			return vtype.getHourly_rate() * TimeUnit.MILLISECONDS.toHours(diff);
		}
	}
}
