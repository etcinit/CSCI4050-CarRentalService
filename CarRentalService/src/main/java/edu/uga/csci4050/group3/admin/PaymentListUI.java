package edu.uga.csci4050.group3.admin;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.core.Boundary;
import edu.uga.csci4050.group3.core.CarRentalServlet;
import edu.uga.csci4050.group3.core.PaymentTransactionEntity;
import edu.uga.csci4050.group3.core.RequestType;
import edu.uga.csci4050.group3.core.UserType;
import edu.uga.csci4050.group3.db.SessionManagement;
import edu.uga.csci4050.group3.template.Alert;
import edu.uga.csci4050.group3.template.AlertType;
import edu.uga.csci4050.group3.template.LayoutRoot;
import edu.uga.csci4050.group3.template.SimpleTemplate;

public class PaymentListUI implements Boundary {

	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response, ServletContext context,
			RequestType type) {
		
		LayoutRoot lr = new LayoutRoot(context, request, response);
		SimpleTemplate paymentsLayout = new SimpleTemplate(context, "PaymentList.mustache");
		SimpleTemplate paymentsRowLayout = new SimpleTemplate(context, "PaymentRow.mustache");
		PaymentListControl control = new PaymentListControl();
		
		lr.setTitle("Payments");
		
		// Check if the user is authorized
		if(new SessionManagement(request, response).requireRole(UserType.ADMIN, CarRentalServlet.getFullURL(context, "/user/home"))){
			return;
		}
		
		// Try to get list
		List<PaymentTransactionEntity> payments = control.getPayments();
		
		if(payments.size() > 0){
			String paymentsHtml = "";
			
			for(PaymentTransactionEntity payment : payments){
				paymentsRowLayout.setVariables(payment.getData());
				paymentsHtml += paymentsRowLayout.render();
			}
			
			paymentsLayout.setVariable("payments", paymentsHtml);
		}else{
			paymentsLayout.setVariable("alert", new Alert(context, "No payments found in our records", AlertType.WARNING).render());
		}
		
		lr.setContent(paymentsLayout.render());
		lr.render(response);
	}

}
