package edu.uga.csci4050.group3.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.jooq.DSLContext;
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
		}
		finally{
			if(conn != null){
				try{
					conn.close();
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	private static String getConnectionURI(){
		return "jdbc:mysql://" + HOSTNAME + ":" + PORT + "/" + DATABASE;
	}
	
	public static void setupDatabase(){
		Connection conn = getConnection();
		
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		
		create.execute("CREATE TABLE VEHICLE " +
						"(id INTEGER not NULL, " +
						"uid VARCHAR(255), " +
						"PRIMARY KEY( id ))");
	}
}
