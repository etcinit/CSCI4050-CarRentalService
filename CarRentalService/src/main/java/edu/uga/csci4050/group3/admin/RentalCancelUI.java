package edu.uga.csci4050.group3.admin;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.core.Boundary;
import edu.uga.csci4050.group3.core.CarRentalServlet;
import edu.uga.csci4050.group3.core.InvalidUrlException;
import edu.uga.csci4050.group3.core.RequestType;
import edu.uga.csci4050.group3.db.RecordNotFoundException;
import edu.uga.csci4050.group3.template.Alert;
import edu.uga.csci4050.group3.template.LayoutRoot;

public class RentalCancelUI implements Boundary {

	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response, ServletContext context,
			RequestType type) {
		
		RentalCancelControl control = new RentalCancelControl();
		
		try {
			control.cancel(request);
			CarRentalServlet.redirect(context, response, "/rentals");
		} catch (InvalidUrlException e) {
			LayoutRoot lr = new LayoutRoot(context, request, response);
			lr.setContent(new Alert(context, "Invalid UID").render());
			lr.render(response);
		} catch (RecordNotFoundException e) {
			LayoutRoot lr = new LayoutRoot(context, request, response);
			lr.setContent(new Alert(context, "Rental transaction not found").render());
			lr.render(response);
		}
	}

}
