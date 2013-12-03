package edu.uga.csci4050.group3.customer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.admin.*;
import edu.uga.csci4050.group3.core.*;
import edu.uga.csci4050.group3.template.*;
import edu.uga.csci4050.group3.customer.CreateRentalControl;
import edu.uga.csci4050.group3.db.RecordNotFoundException;

public class CreateRentalUI implements Boundary {
	
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response, ServletContext context,
			RequestType type) {
		
		LayoutRoot lr = new LayoutRoot(context, request, response);
		//SimpleTemplate page = new SimpleTemplate(context, "CreateRental.mustache");
		SimpleTemplate cardTemplate = new SimpleTemplate(context,"VehicleCardCustomer.mustache"); //
		SimpleTemplate datesInput = new SimpleTemplate(context, "RentalDateInput.mustache");
		CreateRentalControl control = new CreateRentalControl();
		lr.setTitle("Rental");
		VehicleEntity vehicle;
		
		if(type == type.GET){
			try {
				vehicle = control.getVehicle(request); //locate the vehicle user wishes to rent.
				cardTemplate.setVariables(vehicle.getCustomerData()); 
				datesInput.setVariable("uid", vehicle.getUid());
				cardTemplate.setVariable("rental_dates", datesInput.render());
				lr.setContent(cardTemplate.render());
				lr.render(response);
				
				// Find vehicle
				// Show form
				
			} catch (InvalidUrlException e) {
				lr.setContent(new Alert(context,"Invalid URL format").render());
				lr.render(response);
			} catch (RecordNotFoundException e) {
				lr.setContent(new Alert(context,"Vehicle with UID not found").render());
				lr.render(response);
			} 
		}else{
			// POST
			try {
				// Check 
				control.checkReservationDates(request);
				cardTemplate.setVariable("rental_dates", cardTemplate.render());
				lr.setContent(cardTemplate.render());
				
				
				
			} catch (RecordNotFoundException e) {
				lr.setContent(new Alert(context, "Vehicle with UID not found").render());
				lr.render(response);
			} catch (InvalidUrlException e) {
				lr.setContent(new Alert(context, "Invalid URL format").render());
				lr.render(response);
			} catch (InvalidInputException e) {
				lr.setContent(e.getMessagesHtml(context));
				lr.render(response);
			}
		}
		
	
		
	}
}