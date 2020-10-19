package com.java.tools;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;
import java.util.Locale;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.java.database.DatabaseConnection;
import com.opencsv.CSVWriter;

public  class Utils {

	
	public static boolean validateAgainstXSD(String xml, String xsd)
	{
	    try
	    {
	        SchemaFactory factory = 
	            SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	        Schema schema = factory.newSchema(new StreamSource(xsd));
	        Validator validator = schema.newValidator();
	        validator.validate(new StreamSource(xml));
	        return true;
	    }
	    catch(Exception ex)
	    {
	    	System.out.println(ex);
	        return false;
	    }
	}
	
	public static boolean calculateAge(String dateOfBirth) 
	{
		boolean isAdult = false;
		
		try {			
			System.out.println("dateOfBirth ==> " + dateOfBirth);
			DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy")
		            .withLocale(Locale.UK);

		        LocalDate date = formatter.parseLocalDate(dateOfBirth);
		        LocalDate now = new LocalDate();
		        Years age = Years.yearsBetween(date, now);
		        if(age.getValue(0) >= 18)
		        {
		        	isAdult = true;
		        }
		}catch(Exception E)
		{
			System.out.println(E);
			Utils.logErrorMessage(E.getLocalizedMessage());
		}
		
		
		return isAdult;
	}
	
	public static boolean writeToCSV(ResultSet rs) 
	{
		String fileName = "src/main/resources/customerdetail.csv";
        Path myPath = Paths.get(fileName);
        boolean success = false;
		try {			
			try (var writer = new CSVWriter(Files.newBufferedWriter(myPath,
	                StandardCharsets.UTF_8), CSVWriter.DEFAULT_SEPARATOR,
	                CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER,
	                CSVWriter.DEFAULT_LINE_END)) {

	            writer.writeAll(rs, true);
	            success = true;
	        }
		}catch(Exception E)
		{
			System.out.println(E);
			Utils.logErrorMessage(E.getLocalizedMessage());
		}
		
		
		return success;
	}
	
	public static void logErrorMessage(String error)
	{
		DatabaseConnection BDDConnection = new DatabaseConnection();
		Connection conn;
		conn = BDDConnection.ConnectDatabase();
		PreparedStatement stmt = null;
		try {
			
			String query = "INSERT INTO `error` (`Error_message`) VALUES ('" + error  +"');";
			stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.executeUpdate();
		}catch(Exception E)
		{
			System.out.println(E);
			
		}
	}
	
	public static void storeXMLBDD(String path)
	{
		DatabaseConnection BDDConnection = new DatabaseConnection();
		Connection conn;
		conn = BDDConnection.ConnectDatabase();
		PreparedStatement stmt = null;
		String encodedSrting = "";
		try {
			
			File file = new File(path);
			byte[] fileContent = Files.readAllBytes(file.toPath());
			encodedSrting = Base64.getEncoder().encodeToString(fileContent);
			String query = "INSERT INTO `fileuploaded` (`DateUpload`, `Filename`, `EncodedStringBase64`) VALUES (current_timestamp(), '"+ 
			file.getName() +"', '" + encodedSrting  + "');";
			System.out.println(query);
			stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.executeUpdate();
			
		}catch(Exception E)
		{
			Utils.logErrorMessage(E.getLocalizedMessage());
		}
		
	}
	
}
