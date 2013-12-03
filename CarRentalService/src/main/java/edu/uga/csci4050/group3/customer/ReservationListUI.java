package edu.uga.csci4050.group3.customer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.core.AuthenticationException;
import edu.uga.csci4050.group3.core.Boundary;
import edu.uga.csci4050.group3.core.CarRentalServlet;
import edu.uga.csci4050.group3.core.LocationEntity;
import edu.uga.csci4050.group3.core.LocationListControl;
import edu.uga.csci4050.group3.core.RentalStatus;
import edu.uga.csci4050.group3.core.RentalTransactionEntity;
import edu.uga.csci4050.group3.core.RequestType;
import edu.uga.csci4050.group3.core.UserType;
import edu.uga.csci4050.group3.db.DatabaseAbstraction;
import edu.uga.csci4050.group3.db.RecordNotFoundException;
import edu.uga.csci4050.group3.db.SessionManagement;
import edu.uga.csci4050.group3.template.Alert;
import edu.uga.csci4050.group3.template.LayoutRoot;
import edu.uga.csci4050.group3.template.SimpleTemplate;

public class ReservationListUI implements Boundary {

	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response, ServletContext context,
			RequestType type) {
		
		LayoutRoot lr = new LayoutRoot(context, request, response);
		SimpleTemplate list = new SimpleTemplate(context, "ReservationList.mustache");
		ReservationListControl control = new ReservationListControl();
		Calendar cal = Calendar.getInstance();
		lr.setTitle("Reservations");
		
		// Check if the user is authorized
		ArrayList<UserType> authTypes = new ArrayList<UserType>();
		authTypes.add(UserType.ADMIN);
		authTypes.add(UserType.CUSTOMER);
		if(new SessionManagement(request, response).requireRole(authTypes, CarRentalServlet.getFullURL(context, "/user/home"))){
			return;
		}
		
		// Show username in title
		try {
			list.setVariable("username", new SessionManagement(request, response).getLoggedinUsername());
		} catch (AuthenticationException e1) {
			// Do nothing
		}

		List<RentalTransactionEntity> tlist;
		try {
			tlist = control.getList(request,response);
			
			if(tlist.size() > 0){
				String locationsHtml = "";
				for(RentalTransactionEntity rental : tlist){
					// Move time back an hour for checking
					cal.setTime(rental.getStart_dateDate());
					cal.add(Calendar.HOUR, -1);
					// Load the right template depending on whether the user can cancel or return
					if(DatabaseAbstraction.getTimestampFromDate(cal.getTime()) > DatabaseAbstraction.getTimestampFromDate(new Date())){
						// Also check that it hasn't been already cancelled
						if(rental.getStatusEnum() == RentalStatus.CANCELLED){
							SimpleTemplate rentalRow = new SimpleTemplate(context, "ReservationRowStatic.mustache");
							rentalRow.setVariables(rental.getData(true));
							locationsHtml += rentalRow.render();
						}else{
							SimpleTemplate rentalRow = new SimpleTemplate(context, "ReservationRowCancel.mustache");
							rentalRow.setVariables(rental.getData(true));
							locationsHtml += rentalRow.render();
						}
					}else if(rental.getEnd_date() < DatabaseAbstraction.getTimestampFromDate(new Date())){
						if(rental.getStatusEnum() == RentalStatus.ACTIVE){
							SimpleTemplate rentalRow = new SimpleTemplate(context, "ReservationRowReturn.mustache");
							rentalRow.setVariables(rental.getData(true));
							locationsHtml += rentalRow.render();
						}else{
							SimpleTemplate rentalRow = new SimpleTemplate(context, "ReservationRowStatic.mustache");
							rentalRow.setVariables(rental.getData(true));
							locationsHtml += rentalRow.render();
						}
					}else{
						// Also check that is hasn't been returned
						if(rental.getStatusEnum() == RentalStatus.RETURNED){
							SimpleTemplate rentalRow = new SimpleTemplate(context, "ReservationRowStatic.mustache");
							rentalRow.setVariables(rental.getData(true));
							locationsHtml += rentalRow.render();
						}else{
							SimpleTemplate rentalRow = new SimpleTemplate(context, "ReservationRowReturn.mustache");
							rentalRow.setVariables(rental.getData(true));
							locationsHtml += rentalRow.render();
						}
					}
					
				}
				list.setVariable("rentals", locationsHtml);
			}else{
				list.setVariable("message", new Alert(context,"We don't have ny rentals in our records").render());
			}
		} catch (AuthenticationException e) {
			// Problem with user logged in
			lr.setContent(new Alert(context, "Invalid user session").render());
			lr.render(response);
		} catch (RecordNotFoundException e) {
			// Problem with user db info
			lr.setContent(new Alert(context, "Invalid user session").render());
			lr.render(response);
		}
		
		lr.setContent(list.render());
		lr.render(response);
	}

}
