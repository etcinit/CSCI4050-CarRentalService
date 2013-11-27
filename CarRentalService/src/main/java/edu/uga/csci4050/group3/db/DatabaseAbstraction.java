package edu.uga.csci4050.group3.db;

import java.util.Date;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import edu.uga.csci4050.group3.core.VehicleEntity;
import static org.jooq.impl.DSL.*;
import edu.uga.csci4050.group3.jooq.rentalservice.tables.*;

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
	
	/** VEHICLES **/
	
	public static VehicleEntity getVehicle(String UID) throws RecordNotFoundException{
		DSLContext create = DSL.using(getConnection(), SQLDialect.MYSQL);
		List<VehicleEntity> result = create.select()
				.from(Vehicle.VEHICLE)
				.where(Vehicle.VEHICLE.UID.equal(UID))
				.fetch().into(VehicleEntity.class);
		if(result.size() > 0){
			return result.get(0);
		}else{
			throw new RecordNotFoundException();
		}
	}
	
	public static List<VehicleEntity> getVehicles() throws RecordNotFoundException{
		DSLContext create = DSL.using(getConnection(), SQLDialect.MYSQL);
		List<VehicleEntity> result = create.select()
				.from(Vehicle.VEHICLE)
				.fetch().into(VehicleEntity.class);
		if(result.size() > 0){
			return result;
		}else{
			throw new RecordNotFoundException();
		}
	}
	
	public static void putVehicle(VehicleEntity vehicle){
		DSLContext create = DSL.using(getConnection(), SQLDialect.MYSQL);
		Record vehicleRec = create.newRecord(Vehicle.VEHICLE,vehicle);
		create.insertInto(Vehicle.VEHICLE).set(vehicleRec).execute();
	}
	
	public static boolean vehicleExists(String UID){
		try {
			getVehicle(UID);
			return true;
		}catch(RecordNotFoundException ex){
			return false;
		}
	}
	
	/** DATABASE MANAGEMENT **/
	
	public static void setupDatabase(){
		Connection conn = getConnection();
		
		try{
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		
		create.execute("CREATE TABLE IF NOT EXISTS USER " +
						"(id INTEGER NOT NULL AUTO_INCREMENT, " +
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
						"(id INTEGER NOT NULL AUTO_INCREMENT, " +
						"uid VARCHAR(255), " +
						"date SMALLINT, " +
						"method VARCHAR(255), " +
						"description VARCHAR(255), " +
						"user VARCHAR(255), " +
						"reason VARCHAR(255), " +
						"PRIMARY KEY( id ))");

		create.execute("CREATE TABLE IF NOT EXISTS RENTAL_TRANSACTION " +
						"(id INTEGER NOT NULL AUTO_INCREMENT, " +
						"uid VARCHAR(255), " +
						"start_date SMALLINT, " +
						"end_date SMALLINT, " +
						"user VARCHAR(255), " +
						"vehicle VARCHAR(255), " +
						"PRIMARY KEY( id ))");
		
		create.execute("CREATE TABLE IF NOT EXISTS VEHICLE " +
						"(id INTEGER NOT NULL AUTO_INCREMENT, " +
						"uid VARCHAR(255), " +
						"type VARCHAR(255), " +
						"make VARCHAR(255), " +
						"model VARCHAR(255), " +
						"year SMALLINT, " +
						"mileage SMALLINT, " +
						"tag VARCHAR(255), " +
						"lastservice SMALLINT, " +
						"location VARCHAR(255), " +
						"PRIMARY KEY( id ))");
		
		create.execute("CREATE TABLE IF NOT EXISTS VEHICLE_TYPE " +
						"(id INTEGER NOT NULL AUTO_INCREMENT, " +
						"uid VARCHAR(255), " +
						"name VARCHAR(255), " +
						"hourly_rate FLOAT, " +
						"daily_rate FLOAT, " +
						"PRIMARY KEY( id ))");
		
		create.execute("CREATE TABLE IF NOT EXISTS RENTAL_LOCATION " +
						"(id INTEGER NOT NULL AUTO_INCREMENT, " +
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
	
	public static void destroyDatabase(){
		DSLContext create = DSL.using(getConnection(), SQLDialect.MYSQL);
		create.execute("DROP TABLE USER");
		create.execute("DROP TABLE PAYMENT_TRANSACTION");
		create.execute("DROP TABLE RENTAL_TRANSACTION");
		create.execute("DROP TABLE VEHICLE");
		create.execute("DROP TABLE VEHICLE_TYPE");
		create.execute("DROP TABLE RENTAL_LOCATION");
	}
	
	/** TOOLS **/
	
	public static Date getDateFromTimestamp(int timestamp){
		return new Date((long)timestamp * 1000L);
	}
	
	public static int getTimestampFromDate(Date date){
		return new Long(date.getTime()/1000L).intValue();
	}
}
