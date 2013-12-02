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
		CreateRentalControl control = new CreateRentalControl();
		lr.setTitle("Rental");
		VehicleEntity vehicle;
		try {
			vehicle = control.getVehicle(request);
			cardTemplate.setVariables(vehicle.getCustomerData());
			
			lr.setContent(cardTemplate.render());
			lr.render(response);
			
			
		} catch (InvalidUrlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RecordNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //locate the vehicle object user wishes to rent
		
	
		
	}
}