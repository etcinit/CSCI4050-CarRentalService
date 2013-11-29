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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import edu.uga.csci4050.group3.db.DatabaseAbstraction;

public class UserEntity {

	@Column(name = "uid")
	@Size(min = 1)
	@NotNull(message = "UID should not be empty")
	String uid;
	
	@Column(name = "username")
	@Size(min = 5, message = "Username should be at least 5 characters long")
	@NotNull(message = "Username should not be empty")
	String username;
	
	@Column(name = "password")
	@Size(min = 1)
	@NotNull(message = "Password should not be empty")
	String password;
	
	@Column(name = "email")
	@Size(min = 1)
	@NotNull(message = "Email should not be empty")
	String email;
	
	@Column(name = "first_name")
	@Size(min = 1)
	@NotNull(message = "First name should not be empty")
	String first_name;
	
	@Column(name = "last_name")
	@Size(min = 1)
	@NotNull(message = "Last name should not be empty")
	String last_name;
	
	@Column(name = "role")
	@Size(min = 1)
	@NotNull(message = "Role should not be empty")
	String role;
	
	@Column(name = "license")
	@Size(min = 1)
	@NotNull(message = "License should not be empty")
	String license;
	
	@Column(name = "dateofbirth")
	@NotNull(message = "Date of birth should not be empty")
	int dateofbirth;
	
	@Column(name = "street_address")
	@Size(min = 1)
	@NotNull(message = "Address should not be empty")
	String address;
	
	@Column(name = "country")
	@Size(min = 1)
	@NotNull(message = "Country should not be empty")
	String country;
	
	@Column(name = "zipcode")
	@NotNull(message = "Zipcode should not be empty")
	int zipcode;
	
	@Column(name = "city")
	@Size(min = 1)
	@NotNull(message = "City should not be empty")
	String city;
	
	@Column(name = "state")
	@Size(min = 1)
	@NotNull(message = "State should not be empty")
	String state;
	
	public UserEntity(){
		this.uid = UUID.randomUUID().toString();
		setRoleFromEnum(UserType.CUSTOMER);
		this.first_name = "test";
	}

	@Column(name = "uid")
	public String getUid() {
		return uid;
	}

	@Column(name = "uid")
	public void setUid(String uid) {
		this.uid = uid;
	}

	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	@Column(name = "username")
	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	@Column(name = "password")
	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	@Column(name = "email")
	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "first_name")
	public String getFirst_name() {
		return first_name;
	}

	@Column(name = "first_name")
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	@Column(name = "last_name")
	public String getLast_name() {
		return last_name;
	}

	@Column(name = "last_name")
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	@Column(name = "role")
	public String getRole() {
		return role;
	}
	
	public UserType getRoleEnum(){
		return UserType.valueOf(role);
	}

	@Column(name = "role")
	public void setRole(String role) {
		this.role = role;
	}
	
	public void setRoleFromEnum(UserType role){
		this.role = role.name();
	}

	@Column(name = "license")
	public String getLicense() {
		return license;
	}

	@Column(name = "license")
	public void setLicense(String license) {
		this.license = license;
	}

	@Column(name = "dateofbirth")
	public int getDateofbirth() {
		return dateofbirth;
	}
	
	public Date getDateofbirthDate(){
		return DatabaseAbstraction.getDateFromTimestamp(dateofbirth);
	}

	@Column(name = "dateofbirth")
	public void setDateofbirth(int dateofbirth) {
		this.dateofbirth = dateofbirth;
	}
	
	public void setDateofbirthDate(Date dateofbirth){
		this.dateofbirth = DatabaseAbstraction.getTimestampFromDate(dateofbirth);
	}

	@Column(name = "street_address")
	public String getStreet_Address() {
		return address;
	}

	@Column(name = "street_address")
	public void setStreet_Address(String address) {
		this.address = address;
	}

	@Column(name = "country")
	public String getCountry() {
		return country;
	}

	@Column(name = "country")
	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "zipcode")
	public int getZipcode() {
		return zipcode;
	}

	@Column(name = "zipcode")
	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}

	@Column(name = "city")
	public String getCity() {
		return city;
	}

	@Column(name = "city")
	public void setCity(String city) {
		this.city = city;
	}
	
	@Column(name = "state")
	public String getState(){
		return state;
	}
	
	@Column(name = "state")
	public void setState(String state){
		this.state = state;
	}
	
	public void loadFromForm(Map<String,String[]> formData) throws InvalidInputException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
			setStreet_Address(formData.get("userAddress")[0]);
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
		
		if(formData.containsKey("userState")){
			setState(formData.get("userState")[0]);
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
	
	public Map<String, String> getData(){
		Map<String, String> data = new HashMap<String, String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		data.put("uid",this.uid);
		data.put("username",this.username);
		data.put("first_name",this.first_name);
		data.put("last_name",this.last_name);
		data.put("email",this.email);
		data.put("address",this.address);
		data.put("state",this.state);
		data.put("country",this.country);
		data.put("license",this.license);
		data.put("dateofbirth",sdf.format(this.dateofbirth));
		data.put("zipcode", String.valueOf(this.zipcode));
		
		return data;
	}
}
