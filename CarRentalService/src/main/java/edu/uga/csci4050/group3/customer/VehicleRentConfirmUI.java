package edu.uga.csci4050.group3.customer;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.core.AuthenticationException;
import edu.uga.csci4050.group3.core.Boundary;
import edu.uga.csci4050.group3.core.CarRentalServlet;
import edu.uga.csci4050.group3.core.InvalidInputException;
import edu.uga.csci4050.group3.core.InvalidUrlException;
import edu.uga.csci4050.group3.core.RequestType;
import edu.uga.csci4050.group3.core.UserType;
import edu.uga.csci4050.group3.db.RecordNotFoundException;
import edu.uga.csci4050.group3.db.SessionManagement;
import edu.uga.csci4050.group3.template.Alert;
import edu.uga.csci4050.group3.template.LayoutRoot;

public class VehicleRentConfirmUI implements Boundary {

	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response, ServletContext context,
			RequestType type) {
		
		VehicleRentConfirmControl control = new VehicleRentConfirmControl();
		LayoutRoot lr = new LayoutRoot(context, request, response);
		
		// Check if the user is authorized
		ArrayList<UserType> authTypes = new ArrayList<UserType>();
		authTypes.add(UserType.ADMIN);
		authTypes.add(UserType.CUSTOMER);
		if(new SessionManagement(request, response).requireRole(authTypes, CarRentalServlet.getFullURL(context, "/user/home"))){
			return;
		}
		
		if(type == RequestType.GET){
			CarRentalServlet.redirect(context, response, "/user/home");
		}else{
			// Process request to rent vehicle
			try {
				control.confirmRental(request, response);
				CarRentalServlet.redirect(context, response, "/reservations");
			} catch (InvalidInputException e) {
				lr.setContent(e.getMessagesHtml(context));
				lr.render(response);
				return;
			} catch (InvalidUrlException e) {
				lr.setContent(new Alert(context,"Unable to process your request. Invalid URL or parameters.").render());
				lr.render(response);
				return;
			} catch (RecordNotFoundException e) {
				lr.setContent(new Alert(context,"Unable to process your request. Unable to find one or more database objects.").render());
				lr.render(response);
				return;
			} catch (AuthenticationException e) {
				lr.setContent(new Alert(context,"Unable to process your request. User session error").render());
				lr.render(response);
				return;
			}
		}

	}

}
