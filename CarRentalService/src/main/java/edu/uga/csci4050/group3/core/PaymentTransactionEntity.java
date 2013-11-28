package edu.uga.csci4050.group3.core;

import java.util.Date;
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

public class PaymentTransactionEntity {

	@NotNull
	@Size(min = 1)
	@Column(name = "uid")
	String uid;
	
	@NotNull
	@Column(name = "date")
	int date;
	
	@NotNull
	@Size(min = 1)
	@Column(name = "method")
	String method;
	
	@NotNull
	@Size(min = 1)
	@Column(name = "description")
	String description;
	
	@NotNull
	@Size(min = 1)
	@Column(name = "user")
	String user;
	
	@NotNull
	@Size(min = 1)
	@Column(name = "reason")
	String reason;
	
	public PaymentTransactionEntity(){
		this.uid = UUID.randomUUID().toString();
		this.date = 0;
		this.method = "Unknown";
		this.description = "Unknown";
		this.user = "Unknown";
		this.reason = "Unknown";
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getDate() {
		return date;
	}
	
	public Date getDateDate(){
		return DatabaseAbstraction.getDateFromTimestamp(this.date);
	}

	public void setDate(int date) {
		this.date = date;
	}
	
	public void setDateDate(Date date){
		this.date = DatabaseAbstraction.getTimestampFromDate(date);
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getReason() {
		return reason;
	}
	
	public PaymentReason getReasonEnum(){
		return PaymentReason.valueOf(reason);
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public void setReasonFromEnum(PaymentReason reason){
		this.reason = reason.name();
	}

	public void validate() throws InvalidInputException{
		ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
		Validator validator = vf.getValidator();
		Set<ConstraintViolation<PaymentTransactionEntity>> constraintViolations = validator.validate(this);
		
		if(constraintViolations.size() > 0){
			InvalidInputExceptionFactory<PaymentTransactionEntity> iief = new InvalidInputExceptionFactory<PaymentTransactionEntity>();
			
			throw iief.buildException(constraintViolations);
		}
	}
}
