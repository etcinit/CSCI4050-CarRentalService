package edu.uga.csci4050.group3.core;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Boundary {
	public void handleRequest(HttpServletRequest request, HttpServletResponse response, ServletContext context, RequestType type);
}
