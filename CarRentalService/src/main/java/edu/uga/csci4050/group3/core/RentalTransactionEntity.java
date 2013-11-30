package edu.uga.csci4050.group3.core;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import edu.uga.csci4050.group3.db.DatabaseAbstraction;
import edu.uga.csci4050.group3.db.RecordNotFoundException;

public class RentalTransactionEntity {

	@NotNull
	@Size(min = 1)
	@Column(name = "name")
	String uid;
	
	@NotNull
	@Column(name = "start_date")
	int start_date;
	
	@NotNull
	@Column(name = "end_date")
	int end_date;
	
	@NotNull
	@Size(min = 1)
	@Column(name = "user")
	String user;
	
	@NotNull
	@Size(min = 1)
	@Column(name = "vehicle")
	String vehicle;
	
	public RentalTransactionEntity(){
		this.uid = UUID.randomUUID().toString();
		this.start_date = 0;
		this.end_date = 0;
		this.user = "Unknown";
		this.vehicle = "Unknown";
	}

	@Column(name = "uid")
	public String getUid() {
		return uid;
	}

	@Column(name = "uid")
	public void setUid(String uid) {
		this.uid = uid;
	}

	@Column(name = "start_date")
	public int getStart_date() {
		return start_date;
	}

	public Date getStart_dateDate(){
		return DatabaseAbstraction.getDateFromTimestamp(this.start_date);
	}

	@Column(name = "start_date")
	public void setStart_date(int start_date) {
		this.start_date = start_date;
	}
	
	public void setStart_dateDate(Date start_date){
		this.start_date = DatabaseAbstraction.getTimestampFromDate(start_date);
	}

	@Column(name = "end_date")
	public int getEnd_date() {
		return end_date;
	}
	
	public Date getEnd_dateDate(){
		return DatabaseAbstraction.getDateFromTimestamp(this.end_date);
	}

	@Column(name = "end_date")
	public void setEnd_date(int end_date) {
		this.end_date = end_date;
	}
	
	public void setEnd_dateDate(Date end_date){
		this.end_date = DatabaseAbstraction.getTimestampFromDate(end_date);
	}

	@Column(name = "user")
	public String getUser() {
		return user;
	}

	@Column(name = "user")
	public void setUser(String user) {
		this.user = user;
	}

	@Column(name = "vehicle")
	public String getVehicle() {
		return vehicle;
	}

	@Column(name = "vehicle")
	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}
	
	public void validate() throws InvalidInputException{
		ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
		Validator validator = vf.getValidator();
		Set<ConstraintViolation<RentalTransactionEntity>> constraintViolations = validator.validate(this);
		
		if(constraintViolations.size() > 0){
			InvalidInputExceptionFactory<RentalTransactionEntity> iief = new InvalidInputExceptionFactory<RentalTransactionEntity>();
			
			throw iief.buildException(constraintViolations);
		}
	}
	
	public Map<String, String> getData(boolean humanDates){
		Map<String, String> data = new HashMap<String, String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		if(humanDates == true){
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		}
		
		data.put("uid", this.uid);
		data.put("user", this.user);
		
		try {
			data.put("username", DatabaseAbstraction.getUser(this.user).getUsername());
		} catch (RecordNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		data.put("start_date", sdf.format(getStart_dateDate()));
		data.put("end_date", sdf.format(getEnd_dateDate()));
		data.put("vehicle", this.vehicle);
		
		return data;
	}
}
