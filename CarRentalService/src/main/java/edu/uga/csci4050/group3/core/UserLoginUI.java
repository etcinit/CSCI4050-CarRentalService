package edu.uga.csci4050.group3.core;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.template.LayoutRoot;
import edu.uga.csci4050.group3.template.SimpleTemplate;

public class UserLoginUI implements Boundary {

	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response, ServletContext context,
			RequestType type) {
		LayoutRoot lr = new LayoutRoot(context);
		SimpleTemplate hometp = new SimpleTemplate(context, "LoginForm.mustache");
		
		lr.setContent(hometp.render());
		lr.render(response);
	}

}
