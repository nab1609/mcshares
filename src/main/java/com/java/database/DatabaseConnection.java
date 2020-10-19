package com.java.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.java.tools.Utils;

public class DatabaseConnection {
	
	public Connection ConnectDatabase() 
	{
		 Connection con = null;
	    try {
	    	
	    	Class.forName("com.mysql.jdbc.Driver");  
		    con=DriverManager.getConnection(  
		    "jdbc:mysql://localhost:3306/mcshare_test","root","");  
	       if (con != null) {
	          System.out.println("Database connection is successful !!!!");
	       }
	    } catch (Exception E) {
	       
	       Utils.logErrorMessage(E.getLocalizedMessage());
	    }
	    
	    return con;
	}
	
}
