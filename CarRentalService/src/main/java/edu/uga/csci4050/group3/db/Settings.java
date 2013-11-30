package edu.uga.csci4050.group3.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;

import javax.servlet.ServletContext;

public class Settings implements Serializable{
	
	private static final String FILENAME = "CSCI4050-Group3-Settings.obj";
	private static final String PATH = "/tmp/";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Settings(){
		
	}
	
	// Variables
	private ServletContext context;
	
	// Static variables
	private static Settings singleton;
	
	// Settings
	private double membershipFee;
	
	public void loadDefaults(){
		this.membershipFee = 10.00;
	}
	
	// Mutators
	public void setMembershipFee(double fee){
		this.membershipFee = fee;
	}
	
	public double getMembershipFee(){
		return this.membershipFee;
	}
	
	// Static methods
	public static Settings loadFromStorage(ServletContext context){
		try {
			// Check settings file exists
			if(new File(PATH + FILENAME).isFile()){
				try {
					FileInputStream istream = new FileInputStream(new File(PATH + FILENAME));
					ObjectInputStream oistream = new ObjectInputStream(istream);
					singleton = (Settings)oistream.readObject();
					oistream.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return singleton;
			}else{
				return create(context);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private static Settings create(ServletContext context){
		singleton = new Settings();
		singleton.loadDefaults();
		FileOutputStream fostream;
		try {
			fostream = new FileOutputStream(new File(PATH + FILENAME));
			ObjectOutputStream ostream = new ObjectOutputStream(fostream);
			ostream.writeObject(singleton);
			ostream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return singleton;
	}
	
	public static void saveToStorage(Settings settings, ServletContext context) throws Exception{
		if(settings == null){
			throw new Exception("Invalid settings object");
		}
		FileOutputStream fostream;
		try {
			fostream = new FileOutputStream(new File(PATH + FILENAME));
			ObjectOutputStream ostream = new ObjectOutputStream(fostream);
			ostream.writeObject(settings);
			ostream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Settings getSingleton(){
		return singleton;
	}

}
