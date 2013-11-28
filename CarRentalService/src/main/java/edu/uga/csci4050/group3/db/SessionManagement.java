package edu.uga.csci4050.group3.db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.core.AuthenticationException;
import edu.uga.csci4050.group3.core.UserEntity;
import edu.uga.csci4050.group3.core.UserType;

public class SessionManagement {

	public static final String COOKIE_NAME = "rentalAuth";
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public SessionManagement(HttpServletRequest request, HttpServletResponse response){
		this.request = request;
		this.response = response;
	}
	
	public String getLoggedinUsername() throws AuthenticationException{
		// Check for the auth cookie
		Cookie[] cookies = request.getCookies();
		
		// Check if we got any cookies
		if(cookies != null){
			String username = null;
			
			// Check if the auth cookie is within them
			for(Cookie cookie : cookies){
				if(cookie.getName().equals(COOKIE_NAME)){
					username = cookie.getValue();
				}
			}
			
			if(username != null){
				return username;
			}else{
				throw new AuthenticationException();
			}
		}else{
			throw new AuthenticationException();
		}
	}
	
	public boolean isUserLoggedIn(){
		try {
			getLoggedinUsername();
			return true;
		} catch (AuthenticationException e) {
			return false;
		}
	}
	
	/**
	 * Requires that the currently logged in user has a certain role
	 * @param roles
	 * @param redirectUrl URL to redirect the user to
	 * @return True if a redirect is required
	 */
	public boolean requireRole(List<UserType> roles, String redirectUrl){
		// First, check that the user is logged in
		if(!isUserLoggedIn()){
			// User is unregistered
			try {
				response.sendRedirect(redirectUrl);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		
		// Now get the role of the user
		try {
			String username = getLoggedinUsername();
			
			UserEntity user = DatabaseAbstraction.getUserByUsername(username);
			
			// Check if the role matches
			boolean roleMatch = false;
			UserType role = user.getRoleEnum();
			
			for(UserType type : roles){
				if(type.equals(role)){
					roleMatch = true;
				}
			}
			
			// If role matches, no redirect is required
			if(roleMatch){
				return false;
			}else{
				try {
					response.sendRedirect(redirectUrl);
				} catch (IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		} catch (AuthenticationException e) {
			// This exception should not happen
			e.printStackTrace();
			try {
				response.sendRedirect(redirectUrl);
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			return true;
		} catch (RecordNotFoundException e) {
			// Weird state. User logged in but not in database
			try {
				response.sendRedirect(redirectUrl);
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			return true;
		}
		
		
		return false;
	}
	
	/**
	 * Requires that the currently logged in user has a certain role
	 * @param role
	 * @param redirectUrl URL to redirect the user to
	 * @return True if a redirect is required
	 */
	public boolean requireRole(UserType role, String redirectUrl){
		List<UserType> roles = new ArrayList<UserType>();
		roles.add(role);
		return requireRole(roles, redirectUrl);
	}
}
