package edu.uga.csci4050.group3.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LocationEntity {

	@NotNull
	@Size(min = 1)
	@Column(name = "uid")
	String uid;
	
	@NotNull
	@Size(min = 1)
	@Column(name = "name")
	String name;
	
	@NotNull
	@Size(min = 1)
	@Column(name = "street_address")
	String street_address;
	
	@NotNull
	@Size(min = 1)
	@Column(name = "state")
	String state;
	
	@NotNull
	@Size(min = 1)
	@Column(name = "country")
	String country;
	
	@NotNull
	@Column(name = "zipcode")
	int zipcode;
	
	@NotNull
	@Size(min = 1)
	@Column(name = "city")
	String city;
	
	@NotNull
	@Column(name = "capacity")
	int capacity;
	
	public LocationEntity(){
		this.name = "Unknown";
		this.capacity = 1;
		this.uid = UUID.randomUUID().toString();
		this.country = "Unknown";
		this.state = "Unknown";
		this.city = "Unknown";
		this.zipcode = 10000;
		this.street_address = "Unknown";
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet_address() {
		return street_address;
	}

	public void setStreet_address(String street_address) {
		this.street_address = street_address;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getZipcode() {
		return zipcode;
	}

	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public void loadFromForm(Map<String,String[]> formData){
		//InvalidInputException exception = new InvalidInputException();
		
		if(formData.containsKey("locationName")){
			this.setName(formData.get("locationName")[0]);
		}
		
		if(formData.containsKey("locationAddress")){
			this.setStreet_address(formData.get("locationAddress")[0]);
		}
		
		if(formData.containsKey("locationState")){
			this.setState(formData.get("locationState")[0]);
		}
		
		if(formData.containsKey("locationCountry")){
			this.setCountry(formData.get("locationCountry")[0]);
		}
		
		if(formData.containsKey("locationZipcode")){
			this.setZipcode(new Integer(formData.get("locationZipcode")[0]));
		}
		
		if(formData.containsKey("locationCity")){
			this.setCity(formData.get("locationCity")[0]);
		}
		
		if(formData.containsKey("locationCapacity")){
			this.setCapacity(new Integer(formData.get("locationCapacity")[0]));
		}
	}
	
	public void validate() throws InvalidInputException{
		ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
		Validator validator = vf.getValidator();
		Set<ConstraintViolation<LocationEntity>> constraintViolations = validator.validate(this);
		
		if(constraintViolations.size() > 0){
			InvalidInputExceptionFactory<LocationEntity> iief = new InvalidInputExceptionFactory<LocationEntity>();
			
			throw iief.buildException(constraintViolations);
		}
	}
	
	public Map<String, String> getData(){
		Map<String,String> data = new HashMap<String, String>();
		
		data.put("uid", this.uid);
		data.put("name", this.name);
		data.put("street_address", this.uid);
		data.put("state", this.state);
		data.put("country", this.country);
		data.put("zipcode", String.valueOf(this.zipcode));
		data.put("city", this.city);
		data.put("capacity", String.valueOf(this.capacity));
		
		return data;
	}
}
