package edu.uga.csci4050.group3.core;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.db.SessionManagement;
import edu.uga.csci4050.group3.template.Alert;
import edu.uga.csci4050.group3.template.LayoutRoot;
import edu.uga.csci4050.group3.template.SimpleTemplate;

public class UserRegisterUI implements Boundary{

	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response, ServletContext context,
			RequestType type) {
		LayoutRoot lr = new LayoutRoot(context, request, response);
		SimpleTemplate registerForm = new SimpleTemplate(context, "UserRegisterForm.mustache");
		lr.setTitle("Register");
		
		SessionManagement sessMan = new SessionManagement(request, response);
		if(sessMan.isUserLoggedIn()){
			try {
				response.sendRedirect(CarRentalServlet.getFullURL(context, "/user/home"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		
		if(type == RequestType.GET){
			lr.setContent(registerForm.render());
			lr.render(response);
		}else{
			UserRegisterControl urc = new UserRegisterControl();
			try {
				urc.register(request);
				response.sendRedirect(CarRentalServlet.getFullURL(context, "/user/login"));
			} catch (InvalidInputException e) {
				// Invalid input
				registerForm.setVariable("alerts", e.getMessagesHtml(context));;
				lr.setContent(registerForm.render());
				lr.render(response);
			} catch (Exception e){
				Alert error = new Alert(context);
				error.setContent("Something went wrong!");
				registerForm.setVariable("alerts", error.render());
				lr.setContent(registerForm.render());
				lr.render(response);
			}
			
		}
	}

}
