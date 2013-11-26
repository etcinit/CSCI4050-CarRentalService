package edu.uga.csci4050.group3.core;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.csci4050.group3.admin.VehicleCreateUI;
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
		// TODO Auto-generated method stub
		LayoutRoot lr = new LayoutRoot(getServletContext());
		ServletContext context = getServletContext();
		
		if(uriMatches(request, "/login")){
			new LoginUI().handleRequest(request, response, context, type);
		}else if(uriMatches(request, "/") || uriMatches(request, "/home")){
			new HomeUI().handleRequest(request, response, context, type);
		}else if(uriMatches(request, "/vehicle/create")){
			new VehicleCreateUI().handleRequest(request, response, context, type);
		}else if(uriMatches(request, "/database/setup")){
			DatabaseAbstraction.setupDatabase();
		}else if(uriMatches(request, "/database/destroy")){
			DatabaseAbstraction.destroyDatabase();
		}else{
			lr.setContent("Page:" + request.getRequestURI());
			lr.render(response);
		}
		
		//DatabaseAbstraction.setupDatabase();
	}
	
	private boolean uriMatches(HttpServletRequest request, String uri){
		return request.getRequestURI().equals(request.getContextPath() + uri);
	}

}
