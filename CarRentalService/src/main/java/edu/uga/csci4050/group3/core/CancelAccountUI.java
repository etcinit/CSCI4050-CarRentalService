package edu.uga.csci4050.group3.core;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.template.Alert;
import edu.uga.csci4050.group3.template.LayoutRoot;
import edu.uga.csci4050.group3.template.SimpleTemplate;

public class CancelAccountUI implements Boundary {

	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response, ServletContext context,
			RequestType type) {
		LayoutRoot lr = new LayoutRoot(context,request,response);
		
		
		lr.setTitle("Cancel Account");
		
		CancelAccountControl control = new CancelAccountControl();
		
		if(control.isLoggedIn(request, response)){
			SimpleTemplate cancelLayout = new SimpleTemplate(context, "CancelAccount.mustache");
			
			if(control.isAdmin(request, response)){
				// TODO 
				lr.setContent(cancelLayout.render());
				lr.render(response);
			}else{
		
				lr.setContent(cancelLayout.render());
				lr.render(response);
			}
		}else{
			try {
				response.sendRedirect(CarRentalServlet.getFullURL(context, "/user/login"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
}
