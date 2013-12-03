package edu.uga.csci4050.group3.core;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.db.RecordNotFoundException;
import edu.uga.csci4050.group3.db.SessionManagement;
import edu.uga.csci4050.group3.template.Alert;
import edu.uga.csci4050.group3.template.ConfirmationDialog;
import edu.uga.csci4050.group3.template.LayoutRoot;

public class MembershipCancelUI implements Boundary{
	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response, ServletContext context,
			RequestType type) {
		
		LayoutRoot lr = new LayoutRoot(context,request,response);
		MembershipCancelControl control = new MembershipCancelControl();
		
		lr.setTitle("Terminate Membership");
		
		// Check if the user is authorized
		ArrayList<UserType> authTypes = new ArrayList<UserType>();
		authTypes.add(UserType.ADMIN);
		authTypes.add(UserType.CUSTOMER);
		if(new SessionManagement(request, response).requireRole(authTypes, CarRentalServlet.getFullURL(context, "/user/home"))){
			return;
		}
		
		// Process termination
		if(control.processCancellation(request, response)){
			CarRentalServlet.redirect(context, response, "/membership");
			return;
		}
		
		// Show dialog or confirm cancellation
		ConfirmationDialog dialog = new ConfirmationDialog(context, 
				"Terminate your membership.", 
				CarRentalServlet.getFullURL(context, "/membership/cancel?confirm=yes"), 
				CarRentalServlet.getFullURL(context, "/user/home"));
		
		lr.setContent(dialog.render());
		lr.render(response);
	}
}
