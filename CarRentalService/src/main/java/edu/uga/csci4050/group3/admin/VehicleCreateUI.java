package edu.uga.csci4050.group3.admin;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.core.Boundary;
import edu.uga.csci4050.group3.core.InvalidInputException;
import edu.uga.csci4050.group3.core.RequestType;
import edu.uga.csci4050.group3.template.LayoutRoot;
import edu.uga.csci4050.group3.template.SimpleTemplate;

public class VehicleCreateUI implements Boundary {

	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response, ServletContext context,
			RequestType type) {
		VehicleCreateControl control = new VehicleCreateControl();
		// TODO Check that there is at least one location
		// TODO Check that there is at least one vehicle type
		
		// TEMP: Show the new vehicle form
				LayoutRoot lr = new LayoutRoot(context);
				SimpleTemplate createForm = new SimpleTemplate(context, "VehicleCreateForm.mustache");
		
		// Process form data if we got any
		if(type == RequestType.POST){
			try {
				control.create(request.getParameterMap());
			} catch (InvalidInputException e) {
				createForm.setVariable("alerts", e.getMessagesHtml(context));
			}
		}
		
		// Finish building template and display page
		lr.setContent(createForm.render());
		lr.render(response);
		
	}

}
