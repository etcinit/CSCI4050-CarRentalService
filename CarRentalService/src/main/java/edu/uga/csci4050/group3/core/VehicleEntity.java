package edu.uga.csci4050.group3.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import edu.uga.csci4050.group3.db.DatabaseAbstraction;

public class VehicleEntity {

	@Column(name = "type")
	@NotNull
	@Size(min=1)
	@Pattern(regexp="[a-zA-Z0-9\\-]+")
	String type;
	
	@Column(name = "make")
	@NotNull
	@Size(min=1)
	String make;
	
	@Column(name = "model")
	@NotNull
	@Size(min=1)
	String model;
	
	@Column(name = "year")
	@NotNull
	@Min(0)
	int year;
	
	@Column(name = "tag")
	@NotNull
	@Size(min=1)
	String tag;
	
	@Column(name = "mileage")
	@NotNull
	@Min(0)
	int mileage;
	
	@Column(name = "lastservice")
	@NotNull
	int lastservice;
	
	@Column(name = "location")
	@Pattern(regexp="[a-zA-Z0-9\\-]+")
	@NotNull
	@Size(min=1)
	String location;
	
	@Column(name = "uid")
	@Pattern(regexp="[a-zA-Z0-9\\-]+")
	@NotNull
	@Size(min=1)
	String uid;
	
	public VehicleEntity(){
		this.uid = UUID.randomUUID().toString();
		year = 2013;
		model = "Unknown";
		type = "Unknown";
		tag = "000000";
		mileage = 0;
		lastservice = 0;
		location = "Unknown";
		make = "Unknown";
	}
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public int getMileage() {
		return mileage;
	}
	public void setMileage(int mileage) {
		this.mileage = mileage;
	}
	public int getLastservice() {
		return lastservice;
	}
	public Date getLastserviceDate(){
		return DatabaseAbstraction.getDateFromTimestamp(lastservice);
	}
	public void setLastservice(int lastservice) {
		this.lastservice = lastservice;
	}
	public void setLastserviceDate(Date lastservice){
		this.lastservice = DatabaseAbstraction.getTimestampFromDate(lastservice);
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public void loadFromForm(Map<String, String[]> map) throws InvalidInputException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		InvalidInputException exception = new InvalidInputException();
		
		// Get form data a put it into a vehicle object
		if(map.containsKey("vehicleModel")){
			this.setModel(map.get("vehicleModel")[0]);
		}
		
		if(map.containsKey("vehicleMake")){
			this.setMake(map.get("vehicleMake")[0]);
		}
		
		if(map.containsKey("vehicleYear")){
			this.setYear(new Integer(map.get("vehicleYear")[0]));
		}
		
		if(map.containsKey("vehicleTag")){
			this.setTag(map.get("vehicleTag")[0]);
		}
		
		if(map.containsKey("vehicleMileage")){
			this.setMileage(new Integer(map.get("vehicleMileage")[0]));
		}
		
		if(map.containsKey("vehicleDate")){
			try {
				this.setLastserviceDate(sdf.parse(map.get("vehicleDate")[0]));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				exception.addMessage("Invalid date format for the service date");
			}
		}
		
		if(map.containsKey("vehicleType")){
			this.setType(map.get("vehicleType")[0]);
		}
		
		if(map.containsKey("vehicleLocation")){
			this.setLocation(map.get("vehicleLocation")[0]);
		}
		
		if(exception.countMessages() > 0){
			throw exception;
		}
	}
	
	public void validate() throws InvalidInputException{
		ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
		Validator validator = vf.getValidator();
		Set<ConstraintViolation<VehicleEntity>> constraintViolations = validator.validate(this);
		
		if(constraintViolations.size() > 0){
			InvalidInputExceptionFactory<VehicleEntity> iief = new InvalidInputExceptionFactory<VehicleEntity>();
			
			throw iief.buildException(constraintViolations);
		}
	}
	
	/**
	 * Get public data to be used on each vehicle card (visible by customers)
	 * @return Map used by the template system
	 */
	public Map<String, String> getCustomerData(){
		Map<String, String> data = new HashMap<String, String>();
		
		data.put("uid", this.uid);
		data.put("model", this.model);
		data.put("make", this.make);
		data.put("year", String.valueOf(this.year));
		// TODO: Get the type's actual name from the db not the UID
		data.put("type", this.type);
		// TODO: Get the locations's actual name from the db not the UID
		data.put("location", this.location);
		data.put("location_uid", this.location);
		
		return data;
	}
	
	public Map<String, String> getAdminData(){
		Map<String, String> data = new HashMap<String, String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		data.put("uid", this.uid);
		data.put("model", this.model);
		data.put("make", this.make);
		data.put("year", String.valueOf(this.year));
		data.put("type", this.type);
		data.put("lastservice", sdf.format(this.lastservice));
		data.put("tag", this.tag);
		data.put("mileage", String.valueOf(this.mileage));
		data.put("location", this.location);
		data.put("location_uid", this.location);
		
		return data;
	}
}
