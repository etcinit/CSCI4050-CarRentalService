package edu.uga.csci4050.group3.core;

import java.util.Map;
import java.util.Set;

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

	public Double getHourly_rate() {
		return hourly_rate;
	}

	public void setHourly_rate(Double hourly_rate) {
		this.hourly_rate = hourly_rate;
	}

	public Double getDaily_rate() {
		return daily_rate;
	}

	public void setDaily_rate(Double daily_rate) {
		this.daily_rate = daily_rate;
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
}
