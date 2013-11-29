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

public class VehicleTypeEntity {

	@Column(name = "uid")
	@NotNull
	@Size(min = 0)
	String uid;
	
	@Column(name = "name")
	@NotNull
	@Size(min = 0)
	String name;
	
	@Column(name = "hourly_rate")
	@NotNull
	Double hourly_rate;
	
	@Column(name = "daily_rate")
	@NotNull
	Double daily_rate;

	@Column(name = "uid")
	public String getUid() {
		return uid;
	}

	@Column(name = "uid")
	public void setUid(String uid) {
		this.uid = uid;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	@Column(name = "name")
	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "hourly_rate")
	public Double getHourly_rate() {
		return hourly_rate;
	}

	@Column(name = "hourly_rate")
	public void setHourly_rate(Double hourly_rate) {
		this.hourly_rate = hourly_rate;
	}

	@Column(name = "daily_rate")
	public Double getDaily_rate() {
		return daily_rate;
	}

	@Column(name = "daily_rate")
	public void setDaily_rate(Double daily_rate) {
		this.daily_rate = daily_rate;
	}
	
	public VehicleTypeEntity(){
		this.uid = UUID.randomUUID().toString();
	}
	
	public void loadFromForm(Map<String, String[]> formData){
		if(formData.containsKey("typeName")){
			this.name = formData.get("typeName")[0];
		}
		
		if(formData.containsKey("typeDailyRate")){
			this.daily_rate = new Double(formData.get("typeDailyRate")[0]);
		}
		
		if(formData.containsKey("typeHourlyRate")){
			this.hourly_rate = new Double(formData.get("typeHourlyRate")[0]);
		}
	}
	
	public void validate() throws InvalidInputException{
		ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
		Validator validator = vf.getValidator();
		Set<ConstraintViolation<VehicleTypeEntity>> constraintViolations = validator.validate(this);
		
		if(constraintViolations.size() > 0){
			InvalidInputExceptionFactory<VehicleTypeEntity> iief = new InvalidInputExceptionFactory<VehicleTypeEntity>();
			
			throw iief.buildException(constraintViolations);
		}
	}
	
	public Map<String, String> getData(){
		Map<String, String> data = new HashMap<String, String>();
		
		data.put("uid", this.uid);
		data.put("name", this.name);
		data.put("hourly_rate", String.valueOf(this.hourly_rate));
		data.put("daily_rate", String.valueOf(this.daily_rate));
		
		return data;
	}
}
