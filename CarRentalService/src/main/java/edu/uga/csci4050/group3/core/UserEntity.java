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
	
	@Column(name = "date_of_birth")
	@NotNull
	int date_of_birth;
	
	@Column(name = "address")
	@Size(min = 1)
	@NotNull
	String address;
	
	@Column(name = "country")
	@Size(min = 1)
	@NotNull
	String country;
	
	@Column(name = "zip_code")
	@NotNull
	int zip_code;
	
	@Column(name = "city")
	@Size(min = 1)
	@NotNull
	String city;
	
}
