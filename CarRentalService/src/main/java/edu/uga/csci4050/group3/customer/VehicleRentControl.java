package edu.uga.csci4050.group3.customer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import edu.uga.csci4050.group3.core.*;
import edu.uga.csci4050.group3.admin.*;
import edu.uga.csci4050.group3.db.*;
import edu.uga.csci4050.group3.template.SimpleTemplate;

public class VehicleRentControl {

	public VehicleEntity getVehicle(HttpServletRequest request) throws InvalidUrlException, RecordNotFoundException {
		// Check that we have a UID
		if(!request.getParameterMap().containsKey("uid")){
			throw new InvalidUrlException();
		}
		// Attempt to get the vehicle
		VehicleEntity veh = DatabaseAbstraction.getVehicle(request.getParameter("uid"));
		
		return veh;
	}
	
	
	
	public boolean checkReservationDates(HttpServletRequest request) throws RecordNotFoundException, InvalidUrlException, InvalidInputException{
		
		Date start_date = new Date();
		Date end_date = new Date();
		
		// Check that we have a UID
		if(!request.getParameterMap().containsKey("uid")){
			throw new InvalidUrlException();
		}
		
		if(!request.getParameterMap().containsKey("start_date")){
			throw new InvalidUrlException();
		}
		
		if(!request.getParameterMap().containsKey("end_date")){
			throw new InvalidUrlException();
		}
		
		// Parse dates
		InvalidInputException invalidEx = new InvalidInputException();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		
		try {
			start_date = sdf.parse(request.getParameter("start_date"));
		} catch (ParseException e) {
			invalidEx.addMessage("Invalid start date format");
		}
		
		try {
			end_date = sdf.parse(request.getParameter("end_date"));
		} catch (ParseException e) {
			invalidEx.addMessage("Invalid end date format");
		}
		
		if(invalidEx.countMessages() > 0){
			throw invalidEx;
		}
		
		// Attempt to get the vehicle
		VehicleEntity veh = DatabaseAbstraction.getVehicle(request.getParameter("uid"));
		
		List<RentalTransactionEntity> tList = DatabaseAbstraction.getRentalTransactions();
		
		/*
		if(tList.size() > 0){
			for(RentalTransactionEntity rental : tList){
				if(rental.getStart_date() == start){
					return false;
				}else if(rental.getEnd_date() == end){
					return false;	
				}
			}
			return true;
		}*/
		return true;
	}
	

}
