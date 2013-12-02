package edu.uga.csci4050.group3.customer;

import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.core.*;
import edu.uga.csci4050.group3.template.LayoutRoot;
import edu.uga.csci4050.group3.template.SimpleTemplate;

public class RentalLocationsUI implements Boundary {
	
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response, ServletContext context,
			RequestType type) {
		
		LayoutRoot lr = new LayoutRoot(context, request, response);
		SimpleTemplate list = new SimpleTemplate(context, "RentalLocationsList.mustache");
		LocationListControl control = new LocationListControl();
		lr.setTitle("Locations");
		
		List<LocationEntity> locations = control.getList();
		String userLocation = request.getParameter("state");
		
		if(locations.size() > 0){
			String locationsHtml = "";
			for(LocationEntity loc : locations){
				SimpleTemplate locRow = new SimpleTemplate(context, "LocationRow.mustache");
				if(loc.getState().compareTo(userLocation) == 0){
				locRow.setVariables(loc.getData());
				locationsHtml += locRow.render();
				}
			}
			list.setVariable("locations", locationsHtml);
		}else{
			list.setVariable("message", "<h4>No locations found</h4>");
		}
		lr.setContent(list.render());
		lr.render(response);
		
	}

	
	
	
	//uses getters and setters from LocationEntity to obtain location info and display to user,
	//use findRentalLocation() operation.
	public void getLocationData(LocationEntity location ){
		
		
		
		
	}
	
}
