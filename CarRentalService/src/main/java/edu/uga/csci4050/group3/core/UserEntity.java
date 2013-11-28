package edu.uga.csci4050.group3.core;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

	public void setRole(String role) {
		this.role = role;
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

	public void setDateofbirth(int dateofbirth) {
		this.dateofbirth = dateofbirth;
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
	
	
}
