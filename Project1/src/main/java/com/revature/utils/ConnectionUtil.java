package com.revature.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionUtil {
	private static ConnectionUtil cu = null;
	private static Properties properties;
	
	private ConnectionUtil() {
		properties = new Properties();
		
		try {

			InputStream dbProperties = ConnectionUtil.class.getClassLoader().
					getResourceAsStream("database.properties");
			properties.load(dbProperties);
		}catch(IOException e) {
			e.printStackTrace();
		}//end trycatch
		
	}//end constructor
	
	

	
	public static synchronized ConnectionUtil getConnectionUtil() {
		if(cu == null) {
			cu = new ConnectionUtil();
		}
		return cu;
	}
	
	public Connection getConnection() {
		Connection conn = null;
		
		try {
			
			//properties are used to mkae a connection to
			//the database, so we need the same information
			//that is needed for DBeaver
		Class.forName(properties.getProperty("drv"));
		conn = DriverManager.getConnection(
				properties.getProperty("aws"),
				properties.getProperty("usr"),
				properties.getProperty("psw")
				);
				
				
		}catch(Exception e) {
			e.printStackTrace();
		}
		return conn;
	}//end getConnection
	
}//end class
