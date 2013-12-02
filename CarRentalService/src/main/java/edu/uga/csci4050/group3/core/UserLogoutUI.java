package edu.uga.csci4050.group3.core;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserLogoutUI implements Boundary {

	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response, ServletContext context,
			RequestType type) {
		// Check if a user is logged in
		UserLogoutControl ulc = new UserLogoutControl();
		
		if(!ulc.isLoggedIn(request, response)){
			response.addHeader("Location", CarRentalServlet.getFullURL(context, "/home"));
		}
		
		ulc.logout(request, response);
		
		response.addHeader("Location", CarRentalServlet.getFullURL(context, "/home"));
		
		try {
			response.sendRedirect(CarRentalServlet.getFullURL(context, "/"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
