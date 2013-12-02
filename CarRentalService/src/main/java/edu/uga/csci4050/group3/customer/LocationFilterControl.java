package edu.uga.csci4050.group3.customer;

import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import edu.uga.csci4050.group3.core.*;
import edu.uga.csci4050.group3.db.DatabaseAbstraction;
import edu.uga.csci4050.group3.db.RecordNotFoundException;

public class LocationFilterControl {
	 
	 public List<LocationEntity> getFilteredList(HttpServletRequest request){
		 
		 try{
			 List<LocationEntity> list = DatabaseAbstraction.getLocations();
			 List<LocationEntity> filteredList = new ArrayList<LocationEntity>();
			 
			 for(LocationEntity loc : list){
				 boolean matches = true;
				 
				// Check if country matches (if provided)
				 if(request.getParameterMap().containsKey("country") && request.getParameter("country") != ""){
					 if(!loc.getCountry().equals(request.getParameter("country"))){
						 matches = false;
					 }
				 }
				 
				// Check if city matches (if provided)
				 if(request.getParameterMap().containsKey("city") && request.getParameter("city") != ""){
					 if(!loc.getCity().equals(request.getParameter("city"))){
						 matches = false;
					 }
				 }
				 
				 // Check if state matches (if provided)
				 if(request.getParameterMap().containsKey("state") && request.getParameter("state") != ""){
					 if(!loc.getState().equals(request.getParameter("state"))){
						 matches = false;
					 }
				 }
				 
				 // Check if zipcode matches (if provided)
				 if(request.getParameterMap().containsKey("zipcode") && request.getParameter("zipcode") != ""){
					 if(loc.getZipcode() != new Integer(request.getParameter("zipcode"))){
						 matches = false;
					 }
				 }
				 
				 // If all conditions pass, the add to the filtered list
				 if(matches == true){
					 filteredList.add(loc);
				 }
			 }
			 
			 return filteredList;
		 } catch( RecordNotFoundException e ){
			 return new ArrayList<LocationEntity>();
		 }
	 }
	 
}
