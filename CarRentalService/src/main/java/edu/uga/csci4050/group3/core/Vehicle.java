package edu.uga.csci4050.group3.core;

import java.util.UUID;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Vehicle {

	@Column(name = "type")
	@NotNull
	@Pattern(regexp="[a-zA-Z0-9\\-]+")
	String type;
	
	@Column(name = "make")
	@NotNull
	String make;
	
	@Column(name = "model")
	@NotNull
	String model;
	
	@Column(name = "year")
	@NotNull
	@Min(0)
	int year;
	
	@Column(name = "tag")
	@NotNull
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
	String location;
	
	@Column(name = "uid")
	@Pattern(regexp="[a-zA-Z0-9\\-]+")
	@NotNull
	String uid;
	
	public Vehicle(){
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
	public void setLastservice(int lastservice) {
		this.lastservice = lastservice;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
}
