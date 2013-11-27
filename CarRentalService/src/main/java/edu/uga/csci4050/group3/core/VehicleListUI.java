package edu.uga.csci4050.group3.core;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.template.LayoutRoot;
import edu.uga.csci4050.group3.template.SimpleTemplate;

public class VehicleListUI implements Boundary {

	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response, ServletContext context,
			RequestType type) {
		VehicleListControl control = new VehicleListControl();
		
		LayoutRoot lr = new LayoutRoot(context);
		lr.setTitle("List vehicles");
		
		// Tell the control to load the list of vehicles based on the parameters given by the user
		List<VehicleEntity> vehicles = control.getList(request.getParameterMap());
		
		// Load template for vehicle cards
		SimpleTemplate cardTemplate = new SimpleTemplate(context, "VehicleCard.mustache");
		String vehiclesHtml = "";
		
		// Render the template for each vehicle
		if(vehicles.size() > 0){
			for(VehicleEntity vehicle : vehicles){
				cardTemplate.setVariables(vehicle.getCustomerData());
				vehiclesHtml += cardTemplate.render();
			}
		}else{
			// Show a message for when there aren't any vehicles 
			vehiclesHtml = "There aren't vehicles here";
		}
		
		// Finish the template and display the page
		
		SimpleTemplate listTemplate = new SimpleTemplate(context, "VehicleList.mustache");
		listTemplate.setVariable("list", vehiclesHtml);
		
		lr.setContent(listTemplate.render());
		lr.render(response);
	}

}
