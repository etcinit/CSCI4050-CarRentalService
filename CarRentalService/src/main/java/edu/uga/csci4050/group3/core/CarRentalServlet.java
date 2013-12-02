package edu.uga.csci4050.group3.core;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.core.CancelAccountUI;
import edu.uga.csci4050.group3.admin.LocationCreateUI;
import edu.uga.csci4050.group3.admin.LocationDeleteUI;
import edu.uga.csci4050.group3.admin.LocationUpdateUI;
import edu.uga.csci4050.group3.admin.RentalCancelUI;
import edu.uga.csci4050.group3.admin.RentalListUI;
import edu.uga.csci4050.group3.admin.ServiceManagementUI;
import edu.uga.csci4050.group3.admin.UserListUI;
import edu.uga.csci4050.group3.admin.UserUpdateRoleUI;
import edu.uga.csci4050.group3.admin.VehicleCreateUI;
import edu.uga.csci4050.group3.admin.VehicleDeleteUI;
import edu.uga.csci4050.group3.admin.VehicleTypeCreateUI;
import edu.uga.csci4050.group3.admin.VehicleTypeDeleteUI;
import edu.uga.csci4050.group3.admin.VehicleTypeListUI;
import edu.uga.csci4050.group3.admin.VehicleTypeUpdateUI;
import edu.uga.csci4050.group3.admin.VehicleUpdateUI;
import edu.uga.csci4050.group3.db.DatabaseAbstraction;
import edu.uga.csci4050.group3.template.LayoutRoot;
import edu.uga.csci4050.group3.template.SimpleTemplate;
import edu.uga.csci4050.group3.customer.LocationFilterUI;
import edu.uga.csci4050.group3.customer.MembershipUI;

/**
 * Servlet implementation class CarRentalServlet
 */
public class CarRentalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CarRentalServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response, RequestType.GET);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response, RequestType.POST);
	}
	
	private void doRequest(HttpServletRequest request, HttpServletResponse response, RequestType type){
		// Get servlet context for the boundary classes
		ServletContext context = getServletContext();
		
		// This where routing happens. An instance of the corresponding boundary class is created
		// depending on the URL
		if(uriMatches(request, "/login")){
			new UserLoginUI().handleRequest(request, response, context, type);
		}else if(uriMatches(request, "/") || uriMatches(request, "/home")){
			new HomeUI().handleRequest(request, response, context, type);
		}else if(uriMatches(request, "/vehicle/create")){
			new VehicleCreateUI().handleRequest(request, response, context, type);
		}else if(uriMatches(request, "/vehicle/delete")){
			new VehicleDeleteUI().handleRequest(request, response, context, type);
		}else if(uriMatches(request, "/vehicle/update")){
			new VehicleUpdateUI().handleRequest(request, response, context, type);
		}else if(uriMatches(request, "/vehicles")){
			new VehicleListUI().handleRequest(request, response, context, type);
		}else if(uriMatches(request, "/vehicletypes")){
			new VehicleTypeListUI().handleRequest(request, response, context, type);
		}else if(uriMatches(request, "/vehicletype/create")){
			new VehicleTypeCreateUI().handleRequest(request, response, context, type);
		}else if(uriMatches(request, "/vehicletype/update")){
			new VehicleTypeUpdateUI().handleRequest(request, response, context, type);
		}else if(uriMatches(request, "/vehicletype/delete")){
			new VehicleTypeDeleteUI().handleRequest(request, response, context, type);
		}else if(uriMatches(request, "/locations")){
			new LocationListUI().handleRequest(request, response, context, type);
		}else if(uriMatches(request, "/locations/filter")){
			new LocationFilterUI().handleRequest(request, response, context, type);
		}else if(uriMatches(request, "/location")){
			new LocationViewUI().handleRequest(request, response, context, type);
		}else if(uriMatches(request, "/location/create")){
			new LocationCreateUI().handleRequest(request, response, context, type);
		}else if(uriMatches(request, "/location/update")){
			new LocationUpdateUI().handleRequest(request, response, context, type);
		}else if(uriMatches(request, "/location/delete")){
			new LocationDeleteUI().handleRequest(request, response, context, type);
		}else if(uriMatches(request, "/rentals")){
			new RentalListUI().handleRequest(request, response, context, type);
		}else if(uriMatches(request, "/rental/cancel")){
			new RentalCancelUI().handleRequest(request, response, context, type);
		}else if(uriMatches(request, "/users")){
			new UserListUI().handleRequest(request, response, context, type);
		}else if(uriMatches(request, "/user/register")){
			new UserRegisterUI().handleRequest(request, response, context, type);
		}else if(uriMatches(request, "/user/home")){
			new UserHomeUI().handleRequest(request, response, context, type);
		}else if(uriMatches(request, "/user/delete")){
			new CancelAccountUI().handleRequest(request, response, context, type);
		}else if(uriMatches(request, "/user/login")){
			new UserLoginUI().handleRequest(request, response, context, type);
		}else if(uriMatches(request, "/user/logout")){
			new UserLogoutUI().handleRequest(request, response, context, type);
		}else if(uriMatches(request, "/user/updaterole")){
			new UserUpdateRoleUI().handleRequest(request, response, context, type);
		}else if(uriMatches(request, "/service")){
			new ServiceManagementUI().handleRequest(request, response, context, type);
		}else if(uriMatches(request, "/database/setup")){
			DatabaseAbstraction.setupDatabase();
		}else if(uriMatches(request, "/database/destroy")){
			DatabaseAbstraction.destroyDatabase();
		}else if(uriMatches(request, "/membership")){
			new MembershipUI().handleRequest(request, response, context, type);
		}else{
			// Basic page for debugging URLs
			LayoutRoot lr = new LayoutRoot(getServletContext(),request,response);
			lr.setContent("Page:" + request.getRequestURI());
			lr.render(response);
		}
	}
	
	public static String getFullURL(ServletContext context, String URL){
		return context.getContextPath() + URL;
	}
	
	public static void redirect(ServletContext context, HttpServletResponse response, String URL){
		try {
			response.sendRedirect(getFullURL(context, URL));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean uriMatches(HttpServletRequest request, String uri){
		return request.getRequestURI().equals(request.getContextPath() + uri);
	}

}
