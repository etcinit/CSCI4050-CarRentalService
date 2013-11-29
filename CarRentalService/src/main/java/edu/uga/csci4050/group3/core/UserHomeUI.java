package edu.uga.csci4050.group3.core;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.template.LayoutRoot;

public class UserHomeUI implements Boundary{

	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response, ServletContext context,
			RequestType type) {
		// TEMP: Display a basic page
		LayoutRoot lr = new LayoutRoot(context);
		
		lr.setContent("User home");
		
		lr.render(response);
	}

}
