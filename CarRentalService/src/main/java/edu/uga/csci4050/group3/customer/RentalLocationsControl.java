package edu.uga.csci4050.group3.customer;

import java.util.List;
import java.util.ArrayList;

import edu.uga.csci4050.group3.core.*;

import edu.uga.csci4050.group3.db.DatabaseAbstraction;
import edu.uga.csci4050.group3.db.RecordNotFoundException;

public class RentalLocationsControl {

	// holds instance of Rental Location object returned by findRentalLocation(String UID).
	 private LocationEntity currRentalLocation;
	 
	 public LocationEntity findRentalLocation(String UID){
		 
		 return currRentalLocation;
	 }
	 
	 public List<LocationEntity> getRentalLocations(){
		 
		 try{
			 List<LocationEntity> list = DatabaseAbstraction.getLocations();
			 return list;
		 } catch( RecordNotFoundException e ){
			 return new ArrayList<LocationEntity>();
		 }
	 }
	 
}
