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
		SimpleTemplate cardTemplate = new SimpleTemplate(context,"VehicleCardCustomer.mustache");
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
				return;
			} catch (RecordNotFoundException e) {
				lr.setContent(new Alert(context,"Vehicle with UID not found").render());
				lr.render(response);
				return;
			} 
		}else{
			try {
				if(control.isVehicleAvailable(request)){
					// Vehicle is available
					SimpleTemplate availableLayout = new SimpleTemplate(context, "VehicleRentAvailable.mustache");
					
					// Populate variables
					availableLayout.setVariable("uid", request.getParameter("rentalVehicleUID"));
					availableLayout.setVariable("start_date", request.getParameter("rentalStartDate"));
					availableLayout.setVariable("end_date", request.getParameter("rentalEndDate"));
					VehicleTypeEntity vtype = control.getType(request);
					availableLayout.setVariable("daily_rate", String.valueOf(vtype.getDaily_rate()));
					availableLayout.setVariable("hourly_rate", String.valueOf(vtype.getHourly_rate()));
					availableLayout.setVariable("amount", String.valueOf(control.getTotalAmount(request)));
					
					// Get vehicle that the user would like to rent
					VehicleEntity vehicle = control.getVehicle(request);
					
					// Load variables into the vehicle card
					cardTemplate.setVariables(vehicle.getCustomerData()); 
					
					availableLayout.setVariable("vehicle_card", cardTemplate.render());
					
					// Render
					lr.setContent(availableLayout.render());
					lr.render(response);
				}else{
					// TODO: Vehicle is not available
					SimpleTemplate unavailableLayout = new SimpleTemplate(context, "VehicleRentUnavailable.mustache");
					
					// Get vehicle that the user would like to rent
					VehicleEntity vehicle = control.getVehicle(request);
					
					// Load variables into the vehicle card
					cardTemplate.setVariables(vehicle.getCustomerData()); 
					
					unavailableLayout.setVariable("vehicle_card", cardTemplate.render());
					
					// Render
					lr.setContent(unavailableLayout.render());
					lr.render(response);
				}
			} catch (RecordNotFoundException e) {
				lr.setContent(new Alert(context,"Vehicle with UID not found").render());
				lr.render(response);
				return;
			} catch (InvalidUrlException e) {
				lr.setContent(new Alert(context,"Invalid URL format").render());
				lr.render(response);
				return;
			} catch (InvalidInputException e) {
				lr.setContent(e.getMessagesHtml(context));
				lr.render(response);
				return;
			}
		}
		
	
		
	}
}