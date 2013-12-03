package edu.uga.csci4050.group3.customer;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.core.AuthenticationException;
import edu.uga.csci4050.group3.core.Boundary;
import edu.uga.csci4050.group3.core.CarRentalServlet;
import edu.uga.csci4050.group3.core.InvalidInputException;
import edu.uga.csci4050.group3.core.InvalidUrlException;
import edu.uga.csci4050.group3.core.RequestType;
import edu.uga.csci4050.group3.core.UserType;
import edu.uga.csci4050.group3.db.RecordNotFoundException;
import edu.uga.csci4050.group3.db.SessionManagement;
import edu.uga.csci4050.group3.template.Alert;
import edu.uga.csci4050.group3.template.LayoutRoot;
import edu.uga.csci4050.group3.template.SimpleTemplate;

public class MembershipPaymentUI implements Boundary {
	
	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response, ServletContext context,
			RequestType type) {
		LayoutRoot lr = new LayoutRoot(context,request,response);
		SimpleTemplate paymentForm = new SimpleTemplate(context, "MembershipPaymentForm.mustache");
		MembershipPaymentControl mpc = new MembershipPaymentControl();
		
		lr.setTitle("Membership extension payment");
		
		// Check if the user is authorized
		ArrayList<UserType> authTypes = new ArrayList<UserType>();
		authTypes.add(UserType.ADMIN);
		authTypes.add(UserType.CUSTOMER);
		if(new SessionManagement(request, response).requireRole(authTypes, CarRentalServlet.getFullURL(context, "/user/home"))){
			return;
		}
		
		if(type == RequestType.GET) {
			// Show the payment form and costs
			try {
				double value = mpc.getMembershipFee(context);
				paymentForm.setVariable("price", String.valueOf(value));
				if(mpc.getExpirationDate(request, response).getTime() == 0){
					paymentForm.setVariable("expiration", "Unavailable");
				}else{
					paymentForm.setVariable("expiration", mpc.getExpirationDateString(request, response));
				}
				lr.setContent(paymentForm.render());
				lr.render(response);
			} catch (AuthenticationException e) {
				lr.setContent(new Alert(context,"Unable to process your request.").render());
				lr.render(response);
				return;
			} catch (RecordNotFoundException e) {
				lr.setContent(new Alert(context,"Unable to process your request.").render());
				lr.render(response);
				return;
			}
		} else {
			// Try to process a membership extension
			try {
				mpc.extendMembership(request, response);
				CarRentalServlet.redirect(context, response, "/membership");
				return;
			} catch (InvalidUrlException e) {
				lr.setContent(new Alert(context,"Invalid UID provided.").render());
				lr.render(response);
				return;
			} catch (InvalidInputException e) {
				lr.setContent(e.getMessagesHtml(context));
				lr.render(response);
				return;
			} catch (RecordNotFoundException e) {
				lr.setContent(new Alert(context,"Invalid find user").render());
				lr.render(response);
				return;
			} catch (AuthenticationException e) {
				lr.setContent(new Alert(context,"Unable to process your request.").render());
				lr.render(response);
				return;
			}
		}
	}
}
