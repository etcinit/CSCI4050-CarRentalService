package edu.uga.csci4050.group3.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import static org.jooq.impl.DSL.*;

public class DatabaseAbstraction {
	
	private static final String USERNAME = "group3";
	private static final String PASSWORD = "group3";
	private static final String HOSTNAME = "localhost";
	private static final String DATABASE = "rentalservice";
	private static final int PORT = 3306;

	public static Connection getConnection(){
		Connection conn = null;
		
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(getConnectionURI(), USERNAME, PASSWORD);
			return conn;
		}
		catch(Exception e){
			e.printStackTrace();
			//if(conn != null){
			//	try{
			//		conn.close();
			//	}
			//	catch(SQLException ex){
			//		ex.printStackTrace();
			//	}
			//}
		}
		
		return null;
	}
	
	private static String getConnectionURI(){
		return "jdbc:mysql://" + HOSTNAME + ":" + PORT + "/" + DATABASE;
	}
	
	public static void setupDatabase(){
		Connection conn = getConnection();
		
		try{
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		
		create.execute("CREATE TABLE IF NOT EXISTS USER " +
						"(id INTEGER not NULL, " +
						"uid VARCHAR(255), " +
						"username VARCHAR(255), " +
						"password VARCHAR(255), " +
						"email VARCHAR(255), " +
						"first_name VARCHAR(255), " +
						"last_name VARCHAR(255), " +
						"role VARCHAR(255), " +
						"license VARCHAR(255), " +
						"dateofbirth SMALLINT, " +
						"street_address VARCHAR(255), " +
						"state VARCHAR(255), " +
						"country VARCHAR(255), " +
						"zipcode SMALLINT, " +
						"city VARCHAR(255), " +
						"PRIMARY KEY( id ))");
		
		create.execute("CREATE TABLE IF NOT EXISTS PAYMENT_TRANSACTION " +
						"(id INTEGER not NULL, " +
						"uid VARCHAR(255), " +
						"date SMALLINT, " +
						"method VARCHAR(255), " +
						"description VARCHAR(255), " +
						"user VARCHAR(255), " +
						"reason VARCHAR(255), " +
						"PRIMARY KEY( id ))");

		create.execute("CREATE TABLE IF NOT EXISTS RENTAL_TRANSACTION " +
						"(id INTEGER not NULL, " +
						"uid VARCHAR(255), " +
						"start_date SMALLINT, " +
						"end_date SMALLINT, " +
						"user VARCHAR(255), " +
						"vehicle VARCHAR(255), " +
						"PRIMARY KEY( id ))");
		
		create.execute("CREATE TABLE IF NOT EXISTS VEHICLE " +
						"(id INTEGER not NULL, " +
						"uid VARCHAR(255), " +
						"type VARCHAR(255), " +
						"make VARCHAR(255), " +
						"model VARCHAR(255), " +
						"year SMALLINT, " +
						"tag VARCHAR(255), " +
						"lastservice VARCHAR(255), " +
						"location VARCHAR(255), " +
						"PRIMARY KEY( id ))");
		
		create.execute("CREATE TABLE IF NOT EXISTS VEHICLE_TYPE " +
						"(id INTEGER not NULL, " +
						"uid VARCHAR(255), " +
						"name VARCHAR(255), " +
						"hourly_rate FLOAT, " +
						"daily_rate FLOAT, " +
						"PRIMARY KEY( id ))");
		
		create.execute("CREATE TABLE IF NOT EXISTS RENTAL_LOCATION " +
						"(id INTEGER not NULL, " +
						"uid VARCHAR(255), " +
						"name VARCHAR(255), " +
						"street_address VARCHAR(255), " +
						"state VARCHAR(255), " +
						"country VARCHAR(255), " +
						"zipcode SMALLINT, " +
						"city VARCHAR(255), " +
						"capacity SMALLINT, " +
						"PRIMARY KEY( id ))");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
