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
		UserHomeControl uhc = new UserHomeControl();
		LayoutRoot lr = new LayoutRoot(context,request,response);
		
		if(uhc.isLoggedIn(request, response)){
			SimpleTemplate centCol = new SimpleTemplate(context, "CenteredColumn.mustache");
			SimpleTemplate custMenu = new SimpleTemplate(context, "UserHomeCustomer.mustache");
			centCol.setVariable("content", custMenu.render());
			lr.setContent(centCol.render());
			//lr.setContent("Role:" + uhc.getRoleString(request, response));
			lr.render(response);
		}else{
			try {
				response.sendRedirect(CarRentalServlet.getFullURL(context, "/user/login"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//response.setHeader("Location", CarRentalServlet.getFullURL(context, "/user/login"));
		
		}
		
		
	}

}
