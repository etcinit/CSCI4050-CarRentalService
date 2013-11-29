package edu.uga.csci4050.group3.core;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.admin.VehicleCreateUI;
import edu.uga.csci4050.group3.admin.VehicleDeleteUI;
import edu.uga.csci4050.group3.admin.VehicleUpdateUI;
import edu.uga.csci4050.group3.db.DatabaseAbstraction;
import edu.uga.csci4050.group3.template.LayoutRoot;
import edu.uga.csci4050.group3.template.SimpleTemplate;

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
		}else if(uriMatches(request, "/user/register")){
			new UserRegisterUI().handleRequest(request, response, context, type);
		}else if(uriMatches(request, "/user/home")){
			new UserHomeUI().handleRequest(request, response, context, type);
		}else if(uriMatches(request, "/user/login")){
			new UserLoginUI().handleRequest(request, response, context, type);
		}else if(uriMatches(request, "/user/logout")){
			new UserLogoutUI().handleRequest(request, response, context, type);
		}else if(uriMatches(request, "/database/setup")){
			DatabaseAbstraction.setupDatabase();
		}else if(uriMatches(request, "/database/destroy")){
			DatabaseAbstraction.destroyDatabase();
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
