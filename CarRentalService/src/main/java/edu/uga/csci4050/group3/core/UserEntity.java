package edu.uga.csci4050.group3.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import edu.uga.csci4050.group3.db.DatabaseAbstraction;

public class UserEntity {

	@Column(name = "uid")
	@Size(min = 1)
	@NotNull
	String uid;
	
	@Column(name = "username")
	@Size(min = 5, message = "Username should be at least 5 characters long")
	@NotNull
	String username;
	
	@Column(name = "password")
	@Size(min = 1)
	@NotNull
	String password;
	
	@Column(name = "email")
	@Size(min = 1)
	@NotNull
	String email;
	
	@Column(name = "first_anme")
	@Size(min = 1)
	@NotNull
	String first_name;
	
	@Column(name = "last_name")
	@Size(min = 1)
	@NotNull
	String last_name;
	
	@Column(name = "role")
	@Size(min = 1)
	@NotNull
	String role;
	
	@Column(name = "license")
	@Size(min = 1)
	@NotNull
	String license;
	
	@Column(name = "dateofbirth")
	@NotNull
	int dateofbirth;
	
	@Column(name = "address")
	@Size(min = 1)
	@NotNull
	String address;
	
	@Column(name = "country")
	@Size(min = 1)
	@NotNull
	String country;
	
	@Column(name = "zipcode")
	@NotNull
	int zipcode;
	
	@Column(name = "city")
	@Size(min = 1)
	@NotNull
	String city;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getRole() {
		return role;
	}
	
	public UserType getRoleEnum(){
		return UserType.valueOf(role);
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public void setRoleFromEnum(UserType role){
		this.role = role.name();
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public int getDateofbirth() {
		return dateofbirth;
	}
	
	public Date getDateofbirthDate(){
		return DatabaseAbstraction.getDateFromTimestamp(dateofbirth);
	}

	public void setDateofbirth(int dateofbirth) {
		this.dateofbirth = dateofbirth;
	}
	
	public void setDateofbirthDate(Date dateofbirth){
		this.dateofbirth = DatabaseAbstraction.getTimestampFromDate(dateofbirth);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
	
	public void loadFromForm(Map<String,String[]> formData) throws InvalidInputException{
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		InvalidInputException exception = new InvalidInputException();
		
		if(formData.containsKey("userUsername")){
			setUsername(formData.get("userUsername")[0]);
		}
		
		if(formData.containsKey("userPassword")){
			setPassword(formData.get("userPassword")[0]);
		}
		
		if(formData.containsKey("userEmail")){
			setEmail(formData.get("userEmail")[0]);
		}
		
		if(formData.containsKey("userFirstName")){
			setFirst_name(formData.get("userFirstName")[0]);
		}
		
		if(formData.containsKey("userLastName")){
			setLast_name(formData.get("userLastName")[0]);
		}
		
		if(formData.containsKey("userRole")){
			setRole(formData.get("userRole")[0]);
		}
		
		if(formData.containsKey("userLicense")){
			setLicense(formData.get("userLicense")[0]);
		}
		
		if(formData.containsKey("userDateOfBirth")){
			try{
				setDateofbirthDate(sdf.parse(formData.get("userDateOfBirth")[0]));
			}
			catch(ParseException e){
				exception.addMessage("Invalid date format for date of birth");
			}
		}
		
		if(formData.containsKey("userAddress")){
			setAddress(formData.get("userAddress")[0]);
		}
		
		if(formData.containsKey("userCountry")){
			setCountry(formData.get("userCountry")[0]);
		}
		
		if(formData.containsKey("userZipcode")){
			setZipcode(new Integer(formData.get("userZipcode")[0]));
		}
		
		if(formData.containsKey("userCity")){
			setCity(formData.get("userCity")[0]);
		}
		
		// Throw exception if one or more conditions fail
		if(exception.countMessages() > 0){
			throw exception;
		}
	}
	
	public void validate() throws InvalidInputException{
		ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
		Validator validator = vf.getValidator();
		Set<ConstraintViolation<UserEntity>> constraintViolations = validator.validate(this);
		
		if(constraintViolations.size() > 0){
			InvalidInputExceptionFactory<UserEntity> iief = new InvalidInputExceptionFactory<UserEntity>();
			
			throw iief.buildException(constraintViolations);
		}
	}
}
