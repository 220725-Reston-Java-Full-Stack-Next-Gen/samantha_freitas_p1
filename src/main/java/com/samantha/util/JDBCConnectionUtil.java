package com.samantha.util;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnectionUtil {
	 
	private static Logger LOGGER = Logger.getLogger(JDBCConnectionUtil.class);
	    public static Connection getConnection() {
	        Connection conn = null;

	        try {
	        	LOGGER.info("JDBCConnectionUtil - using DB creds for connection: URL="+ System.getenv("dbproject1_url") + 
						", Username=" + System.getenv("dbproject1_username") + 
						", Password=" + System.getenv("dbproject1_password"));
	            conn = DriverManager.getConnection(
	                    System.getenv("dbproject1_url"),
	                    System.getenv("dbproject1_username"),
	                    System.getenv("dbproject1_password")
	            );
	        } catch (SQLException e) {
	            LOGGER.info(e.getMessage());
	        }
	        return conn;
	    }
}
