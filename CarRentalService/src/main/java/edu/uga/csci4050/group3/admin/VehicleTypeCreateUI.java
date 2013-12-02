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
import edu.uga.csci4050.group3.db.SessionManagement;
import edu.uga.csci4050.group3.template.Alert;
import edu.uga.csci4050.group3.template.LayoutRoot;
import edu.uga.csci4050.group3.template.SimpleTemplate;

public class VehicleTypeCreateUI implements Boundary {

	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response, ServletContext context,
			RequestType type) {
		VehicleTypeCreateControl control = new VehicleTypeCreateControl();
		LayoutRoot lr = new LayoutRoot(context, request, response);
		SimpleTemplate createForm = new SimpleTemplate(context, "VehicleTypeCreateForm.mustache");
		lr.setTitle("Define new vehicle type");
		
		// Check if the user is authorized
		if(new SessionManagement(request, response).requireRole(UserType.ADMIN, CarRentalServlet.getFullURL(context, "/user/home"))){
			return;
		}

		if(type == RequestType.POST){
			try {
				control.create(request);
				CarRentalServlet.redirect(context, response, "/vehicletypes");
			} catch (InvalidInputException e) {
				// Display error messages
				createForm.setVariable("alerts", e.getMessagesHtml(context));
				lr.setContent(createForm.render());
				lr.render(response);
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
