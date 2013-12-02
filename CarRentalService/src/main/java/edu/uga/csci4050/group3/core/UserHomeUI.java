package edu.uga.csci4050.group3.core;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.template.LayoutRoot;
import edu.uga.csci4050.group3.template.SimpleTemplate;

public class UserHomeUI implements Boundary{

	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response, ServletContext context,
			RequestType type) {
		// TEMP: Display a basic page
		UserHomeControl control = new UserHomeControl();
		LayoutRoot lr = new LayoutRoot(context,request,response);
		
		if(control.isLoggedIn(request, response)){
			SimpleTemplate custLayout = new SimpleTemplate(context, "UserHomeLayout.mustache");
			SimpleTemplate adminLayout = new SimpleTemplate(context, "UserHomeLayoutAdmin.mustache");
			SimpleTemplate custMenu = new SimpleTemplate(context, "UserHomeCustomer.mustache");
			SimpleTemplate adminMenu = new SimpleTemplate(context, "UserHomeAdmin.mustache");
			
			if(control.isAdmin(request, response)){
				adminLayout.setVariable("user_menu", custMenu.render());
				adminLayout.setVariable("admin_menu", adminMenu.render());
				lr.setContent(adminLayout.render());
				lr.render(response);
			}else{
				custLayout.setVariable("user_menu", custMenu.render());
				lr.setContent(custLayout.render());
				lr.render(response);
			}
		}else{
			try {
				response.sendRedirect(CarRentalServlet.getFullURL(context, "/user/login"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
