package edu.uga.csci4050.group3.customer;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.core.AuthenticationException;
import edu.uga.csci4050.group3.core.Boundary;
import edu.uga.csci4050.group3.core.CarRentalServlet;
import edu.uga.csci4050.group3.core.InvalidInputException;
import edu.uga.csci4050.group3.core.RequestType;
import edu.uga.csci4050.group3.core.UserLoginControl;
import edu.uga.csci4050.group3.db.Settings;
import edu.uga.csci4050.group3.template.Alert;
import edu.uga.csci4050.group3.template.LayoutRoot;
import edu.uga.csci4050.group3.template.SimpleTemplate;

public class MembershipPurchaseForm implements Boundary {
	
	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response, ServletContext context,
			RequestType type) {
		LayoutRoot lr = new LayoutRoot(context,request,response);
		SimpleTemplate paymentForm = new SimpleTemplate(context, "MembershipPaymentForm.mustache");
		SimpleTemplate centCol = new SimpleTemplate(context, "CenteredColumn.mustache");
		
		lr.setTitle("Membership");
		
		MembershipPaymentControl mpc = new MembershipPaymentControl();
		
		if(type == RequestType.GET) {
			// Show the payment form
			centCol.setVariable("content", paymentForm.render());
			
			double value = mpc.getMembershipFee(context);
			paymentForm.setVariable("price", String.valueOf(value));
			
			lr.setContent(centCol.render());
			lr.render(response);
		} else {
			try {
				mpc.authenticate(request, response);
				
				response.sendRedirect(CarRentalServlet.getFullURL(context, "/user/home"));
			} catch (InvalidInputException e) {
				// Generate alert
				Alert alert = new Alert(context);
				alert.setContent("Invalid input. Check credit card info and security key.");
				paymentForm.setVariable("alert", alert.render());
				
				// Show the payment form
				centCol.setVariable("content", paymentForm.render());
				lr.setContent(centCol.render());
				lr.render(response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
