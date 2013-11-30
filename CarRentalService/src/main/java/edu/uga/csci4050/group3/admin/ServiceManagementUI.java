package edu.uga.csci4050.group3.admin;

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

public class ServiceManagementUI implements Boundary {

	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response, ServletContext context,
			RequestType type) {
		
		LayoutRoot lr = new LayoutRoot(context, request, response);
		SimpleTemplate serviceLayout = new SimpleTemplate(context, "ServiceManagement.mustache");
		ServiceManagementControl control = new ServiceManagementControl();
		
		lr.setTitle("Manage service");
		
		// Check if the user is authorized
		if(new SessionManagement(request, response).requireRole(UserType.ADMIN, CarRentalServlet.getFullURL(context, "/user/home"))){
			return;
		}
		
		// Get service fee
		serviceLayout.setVariable("fee", String.valueOf(control.getFee(context)));
		
		if(type == RequestType.POST){
			try {
				control.setFee(context, request);
				serviceLayout.setVariable("fee", String.valueOf(control.getFee(context)));
				lr.setContent(serviceLayout.render());
				lr.render(response);
			} catch (InvalidInputException e) {
				serviceLayout.setVariable("alerts", new Alert(context,"Invalid input").render());
				lr.setContent(serviceLayout.render());
				lr.render(response);
			}
		}else{
			lr.setContent(serviceLayout.render());
			lr.render(response);
		}
	}

}
