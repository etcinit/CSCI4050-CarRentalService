package edu.uga.csci4050.group3.admin;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.core.Boundary;
import edu.uga.csci4050.group3.core.CarRentalServlet;
import edu.uga.csci4050.group3.core.InvalidInputException;
import edu.uga.csci4050.group3.core.RequestType;
import edu.uga.csci4050.group3.core.UserType;
import edu.uga.csci4050.group3.db.DatabaseAbstraction;
import edu.uga.csci4050.group3.db.SessionManagement;
import edu.uga.csci4050.group3.template.Alert;
import edu.uga.csci4050.group3.template.LayoutRoot;
import edu.uga.csci4050.group3.template.SimpleTemplate;

public class VehicleCreateUI implements Boundary {

	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response, ServletContext context,
			RequestType type) {
		VehicleCreateControl control = new VehicleCreateControl();
		LayoutRoot lr = new LayoutRoot(context, request, response);
		SimpleTemplate createForm = new SimpleTemplate(context, "VehicleCreateForm.mustache");
		lr.setTitle("Create a new vehicle");
		
		// Check if the user is authorized
		if(new SessionManagement(request, response).requireRole(UserType.ADMIN, CarRentalServlet.getFullURL(context, "/user/home"))){
			return;
		}
		
		// Check that there is at least one location and vehicle type
		/*if(!control.isDbPopulated()){
			// We can't continue. Show an error
			Alert error = new Alert(context);
			error.setContent("Unable to continue. There aren't enough locations or types defined (at least 1 needed)");
			lr.setContent(error.render());
			lr.render(response);
			return;
		}*/
		
		// Populate form fields
		createForm.setVariable("select_types", DatabaseAbstraction.getVehicleTypesSelect());
		createForm.setVariable("select_locations", DatabaseAbstraction.getLocationsSelect());
		
		if(type == RequestType.POST){
			try {
				control.create(request.getParameterMap());
				response.sendRedirect(CarRentalServlet.getFullURL(context, "/vehicles"));
			} catch (InvalidInputException e) {
				// Display error messages
				createForm.setVariable("alerts", e.getMessagesHtml(context));
				lr.setContent(createForm.render());
				lr.render(response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e){
				createForm.setVariable("alerts", new Alert(context,"Something went wrong").render());
				lr.setContent(createForm.render());
				lr.render(response);
			}
		}else{
			// Display just the form
			lr.setContent(createForm.render());
			lr.render(response);
		}
		
	}

}
