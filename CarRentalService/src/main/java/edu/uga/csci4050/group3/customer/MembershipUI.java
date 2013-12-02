package edu.uga.csci4050.group3.customer;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.admin.UserListControl;
import edu.uga.csci4050.group3.core.Boundary;
import edu.uga.csci4050.group3.core.CarRentalServlet;
import edu.uga.csci4050.group3.core.RequestType;
import edu.uga.csci4050.group3.core.UserEntity;
import edu.uga.csci4050.group3.core.UserType;
import edu.uga.csci4050.group3.db.SessionManagement;
import edu.uga.csci4050.group3.template.LayoutRoot;
import edu.uga.csci4050.group3.template.SimpleTemplate;

public class MembershipUI implements Boundary {

	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response, ServletContext context,
			RequestType type) {
		LayoutRoot lr = new LayoutRoot(context, request, response);
		SimpleTemplate membershipLayout = new SimpleTemplate(context, "Membership.mustache");

		MembershipControl control = new MembershipControl();
		if(!control.customerHasMembership(request, response)){ // Customer doesn't have a membership
			membershipLayout.setVariable("message", "<h4>You don't have a membership with us yet. Would you like to get one?</h4>");
		}else{ // They already have a membership, prompt to extend
			membershipLayout.setVariable("message", "<h4>You already have a membership. Would you like to extend it?</h4>");
		}
		lr.setContent(membershipLayout.render());
		lr.render(response);
	}
}
