package edu.uga.csci4050.group3.core;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.db.RecordNotFoundException;
import edu.uga.csci4050.group3.template.Alert;
import edu.uga.csci4050.group3.template.LayoutRoot;
import edu.uga.csci4050.group3.template.SimpleTemplate;

public class LocationViewUI implements Boundary {

	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response, ServletContext context,
			RequestType type) {
		LayoutRoot lr = new LayoutRoot(context, request, response);
		SimpleTemplate view = new SimpleTemplate(context, "LocationView.mustache");
		LocationViewControl control = new LocationViewControl();
		
		// Try to find location
		try {
			LocationEntity loc = control.getLocation(request);
			view.setVariables(loc.getData());
			
			// Check if user is admin
			boolean isAdmin = control.isAdmin(request, response);
			if(isAdmin){
				SimpleTemplate menu = new SimpleTemplate(context, "LocationViewAdminMenu.mustache");
				menu.setVariable("uid", loc.getUid());
				view.setVariable("extra_options", menu.render());
			}
			
			// TODO: Load vehicles in the location and display them
		} catch (InvalidUrlException e) {
			lr.setContent(new Alert(context, "Invalid URL. No UID specified").render());
			lr.render(response);
			return;
		} catch (RecordNotFoundException e) {
			lr.setContent(new Alert(context, "Invalid UID. Location not found").render());
			lr.render(response);
			return;
		}
		
		lr.setContent(view.render());
		lr.render(response);
	}

}
