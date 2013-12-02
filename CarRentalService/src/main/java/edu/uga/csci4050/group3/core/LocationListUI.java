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
		SimpleTemplate countryList = new SimpleTemplate(context, "CountrySelectInput.mustache");
		SimpleTemplate stateList = new SimpleTemplate(context, "StateSelectInput.mustache");
		SimpleTemplate filterForm = new SimpleTemplate(context, "CustomerLocationEntry.mustache");
		LocationListControl control = new LocationListControl();
		lr.setTitle("Locations");
		
		// Check if user is admin
		boolean isAdmin = control.isAdmin(request, response);
		if(isAdmin){
			SimpleTemplate menu = new SimpleTemplate(context, "LocationListAdminMenu.mustache");
			
			String extraOptions = "";
			
			// Add admin menu
			extraOptions += menu.render();
			
			// Prepare form elements (Country/State)
			countryList.setVariable("name", "country");
			filterForm.setVariable("select_country", countryList.render());
			stateList.setVariable("name", "state");
			filterForm.setVariable("select_state", stateList.render());
			extraOptions += filterForm.render();
			
			list.setVariable("extra_options", extraOptions);
		}else{//if customer, display entry form to filter by state
			
			// Prepare form elements (Country/State)
			countryList.setVariable("name", "country");
			filterForm.setVariable("select_country", countryList.render());
			stateList.setVariable("name", "state");
			filterForm.setVariable("select_state", stateList.render());
			list.setVariable("extra_options", filterForm.render());
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
