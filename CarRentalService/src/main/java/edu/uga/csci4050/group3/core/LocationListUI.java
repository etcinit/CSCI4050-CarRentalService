package edu.uga.csci4050.group3.core;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.template.LayoutRoot;
import edu.uga.csci4050.group3.template.SimpleTemplate;

public class LocationListUI implements Boundary {

	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response, ServletContext context,
			RequestType type) {
		
		LayoutRoot lr = new LayoutRoot(context, request, response);
		SimpleTemplate list = new SimpleTemplate(context, "LocationList.mustache");
		LocationListControl control = new LocationListControl();
		lr.setTitle("Locations");
		
		// Check if user is admin
		boolean isAdmin = control.isAdmin(request, response);
		if(isAdmin){
			SimpleTemplate menu = new SimpleTemplate(context, "LocationListAdminMenu.mustache");
			list.setVariable("extra_options", menu.render());
		}else{//if customer, display entry form to filter by state
			SimpleTemplate zipCode = new SimpleTemplate(context, "CustomerLocationEntry.mustache");
			list.setVariable("extra_options", zipCode.render());
			
			String userLocation = request.getParameter("state");
			
			/*if(userLocation.compareTo("") != 0){
				List<LocationEntity> locations = control.getList();
				
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
			}*/
		}
		
		
		// Get list of locations
		List<LocationEntity> locations = control.getList();
		
		if(locations.size() > 0){
			String locationsHtml = "";
			for(LocationEntity loc : locations){
				SimpleTemplate locRow = new SimpleTemplate(context, "LocationRow.mustache");
				locRow.setVariables(loc.getData());
				locationsHtml += locRow.render();
			}
			list.setVariable("locations", locationsHtml);
		}else{
			list.setVariable("message", "<h4>No locations found</h4>");
		}
		
		lr.setContent(list.render());
		lr.render(response);
	}

}
