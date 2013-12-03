package edu.uga.csci4050.group3.customer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.admin.*;
import edu.uga.csci4050.group3.core.*;
import edu.uga.csci4050.group3.template.*;
import edu.uga.csci4050.group3.customer.VehicleRentControl;
import edu.uga.csci4050.group3.db.RecordNotFoundException;

public class VehicleRentUI implements Boundary {
	
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response, ServletContext context,
			RequestType type) {
		
		LayoutRoot lr = new LayoutRoot(context, request, response);
		//SimpleTemplate page = new SimpleTemplate(context, "CreateRental.mustache");
		SimpleTemplate cardTemplate = new SimpleTemplate(context,"VehicleCardCustomer.mustache"); //
		SimpleTemplate vehicleRentLayout = new SimpleTemplate(context, "VehicleRent.mustache");
		VehicleRentControl control = new VehicleRentControl();
		
		lr.setTitle("Rental");
		
		if(type == RequestType.GET){
			try {
				// Get vehicle that the user would like to rent
				VehicleEntity vehicle = control.getVehicle(request);
				
				// Load variables into the vehicle card
				cardTemplate.setVariables(vehicle.getCustomerData()); 
				
				// Load variables into the form
				vehicleRentLayout.setVariable("uid", vehicle.getUid());
				
				// Include card in the layout
				vehicleRentLayout.setVariable("vehicle_card", cardTemplate.render());
				
				// Show form to user
				lr.setContent(vehicleRentLayout.render());
				lr.render(response);
				return;
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