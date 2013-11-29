package edu.uga.csci4050.group3.admin;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.core.Boundary;
import edu.uga.csci4050.group3.core.CarRentalServlet;
import edu.uga.csci4050.group3.core.InvalidInputException;
import edu.uga.csci4050.group3.core.InvalidUrlException;
import edu.uga.csci4050.group3.core.RequestType;
import edu.uga.csci4050.group3.core.UserType;
import edu.uga.csci4050.group3.core.VehicleEntity;
import edu.uga.csci4050.group3.core.VehicleTypeEntity;
import edu.uga.csci4050.group3.db.RecordNotFoundException;
import edu.uga.csci4050.group3.db.SessionManagement;
import edu.uga.csci4050.group3.template.Alert;
import edu.uga.csci4050.group3.template.LayoutRoot;
import edu.uga.csci4050.group3.template.SimpleTemplate;

public class VehicleTypeUpdateUI implements Boundary {

	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response, ServletContext context,
			RequestType type) {
		VehicleTypeUpdateControl control = new VehicleTypeUpdateControl();
		LayoutRoot lr = new LayoutRoot(context, request, response);
		SimpleTemplate updateForm = new SimpleTemplate(context, "VehicleTypeUpdateForm.mustache");
		
		// Check if the user is authorized
		if(new SessionManagement(request, response).requireRole(UserType.ADMIN, CarRentalServlet.getFullURL(context, "/user/home"))){
			return;
		}

		// Try to load item data
		try{
		VehicleTypeEntity vehicle = control.getVehicleType(request);
			updateForm.setVariables(vehicle.getData());
		}
		catch(RecordNotFoundException ex){
			new Alert(context,"Vehicle with UID not found").render(response);
			return;
		}
		catch(InvalidUrlException ex){
			new Alert(context,"UID wasn't specified").render(response);
			return;
		}
		
		if(type == RequestType.POST){
			try {
				control.update(request);
				response.sendRedirect(CarRentalServlet.getFullURL(context, "/vehicletypes"));
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
