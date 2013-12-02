package edu.uga.csci4050.group3.customer;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.core.*;
import edu.uga.csci4050.group3.template.Alert;
import edu.uga.csci4050.group3.template.AlertType;
import edu.uga.csci4050.group3.template.LayoutRoot;
import edu.uga.csci4050.group3.template.SimpleTemplate;

public class LocationFilterUI implements Boundary {
	
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response, ServletContext context,
			RequestType type) {
		
		LayoutRoot lr = new LayoutRoot(context, request, response);
		SimpleTemplate list = new SimpleTemplate(context, "LocationFilterList.mustache");
		LocationFilterControl control = new LocationFilterControl();
		lr.setTitle("Locations");
		
		List<LocationEntity> locations = control.getFilteredList(request);
		
		if(locations.size() > 0){
			String locationsHtml = "";
			for(LocationEntity loc : locations){
				SimpleTemplate locRow = new SimpleTemplate(context, "LocationRow.mustache");
				locRow.setVariables(loc.getData());
				locationsHtml += locRow.render();
			}
			list.setVariable("locations", locationsHtml);
		}else{
			Alert info = new Alert(context,"No locations found with the specified criteria");
			info.setType(AlertType.WARNING);
			list.setVariable("message", info.render());
		}
		lr.setContent(list.render());
		lr.render(response);
		
	}
	
}
