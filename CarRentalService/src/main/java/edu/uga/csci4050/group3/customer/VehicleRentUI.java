package edu.uga.csci4050.group3.customer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.core.Boundary;
import edu.uga.csci4050.group3.core.RequestType;
import edu.uga.csci4050.group3.core.UserType;

public class VehicleRentUI implements Boundary {

	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response, ServletContext context,
			RequestType type) {
		// TODO Auto-generated method stub

		if(type == RequestType.POST){
			// Check that the vehicle is available at the specified dates
			
			// If available, show estimated cost
			
			// If not available, show form for entering dates
		}else{
			// Show a form for entering the rental dates
		}
	}

}
