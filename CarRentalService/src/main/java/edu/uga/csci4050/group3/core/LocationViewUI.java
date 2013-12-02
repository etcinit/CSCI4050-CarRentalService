package edu.uga.csci4050.group3.core;

import java.util.List;

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
		lr.setTitle("Location details");
		
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
			
			//display location first
			lr.setContent(view.render());
			lr.render(response);
			
			//load card template to display vehicles.
			SimpleTemplate cardTemplate = new SimpleTemplate(context, "VehicleCard.mustache");
			// TODO: Load vehicles in the location and display them DONE!
			VehicleListControl vehicleCtrl = new VehicleListControl();
			List<VehicleEntity> vehicles = vehicleCtrl.getList(request.getParameterMap());
			String vehiclesHtml = "";
			if(vehicles.size() > 0){
				vehiclesHtml += "<div class=\"row pad-top-10\">";
				for(VehicleEntity vehicle : vehicles){
					 //compare the UID of vehicle location to UID of location
					if(vehicle.getLocation().compareTo(loc.getUid()) == 0){
						cardTemplate.setVariables(vehicle.getCustomerData());
						vehiclesHtml += cardTemplate.render();
					}
				}
				vehiclesHtml += "</div>";
			}else{
				// Show a message for when there aren't any vehicles 
				vehiclesHtml += "<h4>There aren't vehicles here</h4>";
			}
			SimpleTemplate listTemplate = new SimpleTemplate(context, "VehicleList.mustache");
			listTemplate.setVariable("list", vehiclesHtml);
			
			lr.setContent(listTemplate.render());
			lr.render(response);
			
			
		} catch (InvalidUrlException e) {
			lr.setContent(new Alert(context, "Invalid URL. No UID specified").render());
			lr.render(response);
			return;
		} catch (RecordNotFoundException e) {
			lr.setContent(new Alert(context, "Invalid UID. Location not found").render());
			lr.render(response);
			return;
		}
		
		
	}

}
