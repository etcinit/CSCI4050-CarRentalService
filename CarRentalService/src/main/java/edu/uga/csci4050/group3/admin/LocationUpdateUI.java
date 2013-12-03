package edu.uga.csci4050.group3.admin;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.core.Boundary;
import edu.uga.csci4050.group3.core.CarRentalServlet;
import edu.uga.csci4050.group3.core.InvalidInputException;
import edu.uga.csci4050.group3.core.InvalidUrlException;
import edu.uga.csci4050.group3.core.LocationEntity;
import edu.uga.csci4050.group3.core.RequestType;
import edu.uga.csci4050.group3.core.UserType;
import edu.uga.csci4050.group3.core.VehicleEntity;
import edu.uga.csci4050.group3.db.RecordNotFoundException;
import edu.uga.csci4050.group3.db.SessionManagement;
import edu.uga.csci4050.group3.template.Alert;
import edu.uga.csci4050.group3.template.LayoutRoot;
import edu.uga.csci4050.group3.template.SelectForm;
import edu.uga.csci4050.group3.template.SelectFormType;
import edu.uga.csci4050.group3.template.SimpleTemplate;

public class LocationUpdateUI implements Boundary {

	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response, ServletContext context,
			RequestType type) {
		
		LocationUpdateControl control = new LocationUpdateControl();
		LayoutRoot lr = new LayoutRoot(context, request, response);
		SimpleTemplate updateForm = new SimpleTemplate(context, "LocationUpdateForm.mustache");
		SimpleTemplate countryList = new SimpleTemplate(context, "CountrySelectInput.mustache");
		SimpleTemplate stateList = new SimpleTemplate(context, "StateSelectInput.mustache");
		
		lr.setTitle("Update location");
		
		// Check if the user is authorized
		if(new SessionManagement(request, response).requireRole(UserType.ADMIN, CarRentalServlet.getFullURL(context, "/user/home"))){
			return;
		}
		
		
		
		// Try to load item data
		try{
			LocationEntity location = control.getLocation(request);
			updateForm.setVariables(location.getData());
			
			// Prepare form elements (Country/State)
			SelectForm countryForm = new SelectForm(context, SelectFormType.COUNTRY);
			countryForm.setPreselectedOption(location.getCountry(), location.getCountry());
			countryForm.setName("locationCountry");
			SelectForm stateForm = new SelectForm(context, SelectFormType.STATE);
			stateForm.setPreselectedOption(location.getState(), location.getState());
			stateForm.setName("locationState");
			
			updateForm.setVariable("select_country", countryForm.render());
			updateForm.setVariable("select_state", stateForm.render());
		}
		catch(RecordNotFoundException ex){
			lr.setContent(new Alert(context,"Location with UID not found").render());
			lr.render(response);
			return;
		}
		catch(InvalidUrlException ex){
			lr.setContent(new Alert(context,"UID wasn't specified").render());
			lr.render(response);
			return;
		}

		if(type == RequestType.POST){
			try {
				control.update(request);
				response.sendRedirect(CarRentalServlet.getFullURL(context, "/locations"));
			} catch (InvalidInputException e) {
				// Display error messages
				updateForm.setVariable("alerts", e.getMessagesHtml(context));
				lr.setContent(updateForm.render());
				lr.render(response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e){
				updateForm.setVariable("alerts", new Alert(context,"Something went wrong").render());
				lr.setContent(updateForm.render());
				lr.render(response);
			}
		}else{
			// Display just the form
			lr.setContent(updateForm.render());
			lr.render(response);
		}
	}

}
