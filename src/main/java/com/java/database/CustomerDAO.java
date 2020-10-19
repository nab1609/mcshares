package com.java.database;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.java.model.ContactDetails;
import com.java.model.DataItemCustomer;
import com.java.model.MailingAddress;
import com.java.model.RequestDoc;
import com.java.model.SharesDetails;
import com.java.tools.Utils;
import com.opencsv.CSVWriter;

public class CustomerDAO {
	
	DatabaseConnection conn = new DatabaseConnection();
	
	public static boolean CreateCustomerRecord(RequestDoc RequestDoc) {
		
		DatabaseConnection BDDConnection = new DatabaseConnection();
		Connection conn;
		conn = BDDConnection.ConnectDatabase();
		PreparedStatement stmt = null;
		ResultSet tableKeys = null;
		int idGenerated = 0;
		int docReferenceId = 0;
		boolean success = false;
		
		try {
			int number_of_customer = RequestDoc.getDocData().getDataItemCustomer().length;
			
			for(int index=0;index <number_of_customer ; index++ )
			{
				if(RequestDoc.getDocData().getDataItemCustomer()[index].getCustomerType().toLowerCase().equals("individual") && !RequestDoc.getDocData().getDataItemCustomer()[index].getDateOfBirth().equals( ""))
				{	
					//verify if > 18
					if(Utils.calculateAge(RequestDoc.getDocData().getDataItemCustomer()[index].getDateOfBirth()))
					{
						
						
						String insert_request_doc = "INSERT INTO `requestdoc` (`Doc_Date`, `Doc_Ref`) VALUES ('" + 
						(RequestDoc.getDocDate().equals("") || RequestDoc.getDocDate().equals(null)? null : RequestDoc.getDocDate() ) +"', '" + 
						(RequestDoc.getDocRef().equals("") || RequestDoc.getDocRef().equals(null) ? null : RequestDoc.getDocRef() ) +"');";
						
						stmt = conn.prepareStatement(insert_request_doc, Statement.RETURN_GENERATED_KEYS);
						stmt.executeUpdate();
						
						tableKeys = stmt.getGeneratedKeys();
						tableKeys.next();
						idGenerated = tableKeys.getInt(1);
						docReferenceId = idGenerated;
						System.out.println(insert_request_doc);
						String insert_customerDetails = "INSERT INTO `customer` (`Customer_id`, `Customer_Type`, `Date_Of_Birth`, `Date_Incorp`, `Registration_No`, `RequestDoc_Doc_Data`)VALUES"
								+ "('" + (RequestDoc.getDocData().getDataItemCustomer()[index].getCustomerID().equals("") || RequestDoc.getDocData().getDataItemCustomer()[index].getCustomerID().equals(null) ? null : RequestDoc.getDocData().getDataItemCustomer()[index].getCustomerID())  +
								"', '" + (RequestDoc.getDocData().getDataItemCustomer()[index].getCustomerType().equals("") || RequestDoc.getDocData().getDataItemCustomer()[index].getCustomerType().equals(null) ? null : RequestDoc.getDocData().getDataItemCustomer()[index].getCustomerType()) +
								"', '" + (RequestDoc.getDocData().getDataItemCustomer()[index].getDateOfBirth().equals("") || RequestDoc.getDocData().getDataItemCustomer()[index].getDateOfBirth().equals(null)  ? null : RequestDoc.getDocData().getDataItemCustomer()[index].getDateOfBirth()) +
								"','" +  (RequestDoc.getDocData().getDataItemCustomer()[index].getDateIncorp().equals("") || RequestDoc.getDocData().getDataItemCustomer()[index].getDateIncorp().equals(null) ? null : RequestDoc.getDocData().getDataItemCustomer()[index].getDateIncorp())  +
								"','" + (RequestDoc.getDocData().getDataItemCustomer()[index].getRegistrationNo().equals("") || RequestDoc.getDocData().getDataItemCustomer()[index].getRegistrationNo().equals(null)  ? null : RequestDoc.getDocData().getDataItemCustomer()[index].getRegistrationNo()) + 
								"'," + idGenerated + ");";
						
						System.out.println(insert_customerDetails);
						stmt = conn.prepareStatement(insert_customerDetails, Statement.RETURN_GENERATED_KEYS);
						stmt.executeUpdate();
						
						tableKeys = stmt.getGeneratedKeys();
						tableKeys.next();
						idGenerated = tableKeys.getInt(1);
						
						
						String insert_mailing_Address = "INSERT INTO `mailing_address` (`Address_Line1`, `Address_Line2`, `Town_City`, `Country`, `Customer_ID`)VALUES"
								+ "('" + (RequestDoc.getDocData().getDataItemCustomer()[index].getMailingAddress().getAddressLine1().equals("") || RequestDoc.getDocData().getDataItemCustomer()[index].getMailingAddress().getAddressLine1().equals(null) ? null : RequestDoc.getDocData().getDataItemCustomer()[index].getMailingAddress().getAddressLine1()) + "',' " +
								(RequestDoc.getDocData().getDataItemCustomer()[index].getMailingAddress().getAddressLine2().equals("")|| RequestDoc.getDocData().getDataItemCustomer()[index].getMailingAddress().getAddressLine2().equals(null)  ? null : RequestDoc.getDocData().getDataItemCustomer()[index].getMailingAddress().getAddressLine2()) +"', '" + 
								(RequestDoc.getDocData().getDataItemCustomer()[index].getMailingAddress().getTownCity().equals("") || RequestDoc.getDocData().getDataItemCustomer()[index].getMailingAddress().getTownCity().equals(null) ? null : RequestDoc.getDocData().getDataItemCustomer()[index].getMailingAddress().getTownCity()) + "','" +
								(RequestDoc.getDocData().getDataItemCustomer()[index].getMailingAddress().getCountry().equals("") || RequestDoc.getDocData().getDataItemCustomer()[index].getMailingAddress().getCountry().equals(null) ? null : RequestDoc.getDocData().getDataItemCustomer()[index].getMailingAddress().getCountry())+  "'," + 
								idGenerated +");";
						
						
						System.out.println(insert_mailing_Address);
						stmt = conn.prepareStatement(insert_mailing_Address, Statement.RETURN_GENERATED_KEYS);
						stmt.executeUpdate();
						
						tableKeys = stmt.getGeneratedKeys();
						tableKeys.next();
						
						
						String insert_contact_details = "INSERT INTO `contact_details` (`Contact_Name`, `Contact_Number`, `Customer_ID`) VALUES"
								+ "('" +(RequestDoc.getDocData().getDataItemCustomer()[index].getContactDetails().getContactName().equals("") ||  RequestDoc.getDocData().getDataItemCustomer()[index].getContactDetails().getContactName().equals(null) ? null : RequestDoc.getDocData().getDataItemCustomer()[index].getContactDetails().getContactName())+"','" +
								(RequestDoc.getDocData().getDataItemCustomer()[index].getContactDetails().getContactNumber().equals("") || RequestDoc.getDocData().getDataItemCustomer()[index].getContactDetails().getContactNumber().equals(null) ? null : RequestDoc.getDocData().getDataItemCustomer()[index].getContactDetails().getContactNumber()) +"'," + 
								idGenerated+");";
						
						System.out.println(insert_contact_details);
						stmt = conn.prepareStatement(insert_contact_details, Statement.RETURN_GENERATED_KEYS);
						stmt.executeUpdate();
						
						tableKeys = stmt.getGeneratedKeys();
						tableKeys.next();
						
						String insert_share_detail = "INSERT INTO `shares_details` (`Num_Shares`, `Share_Price`, `Customer_ID`) VALUES"
								+ "(' " + (RequestDoc.getDocData().getDataItemCustomer()[index].getSharesDetails().getNumShares().equals("") || RequestDoc.getDocData().getDataItemCustomer()[index].getSharesDetails().getNumShares().equals(null)  ? null : RequestDoc.getDocData().getDataItemCustomer()[index].getSharesDetails().getNumShares())  + "', ' " +
								(RequestDoc.getDocData().getDataItemCustomer()[index].getSharesDetails().getSharePrice().equals("") || RequestDoc.getDocData().getDataItemCustomer()[index].getSharesDetails().getSharePrice().equals(null) ? null : RequestDoc.getDocData().getDataItemCustomer()[index].getSharesDetails().getSharePrice()) + "'," +
								idGenerated + ");";
								
						System.out.println(insert_share_detail);
						stmt = conn.prepareStatement(insert_share_detail, Statement.RETURN_GENERATED_KEYS);
						stmt.executeUpdate();						
						tableKeys = stmt.getGeneratedKeys();
						
						success = true;
						
					}
				}
				else
				{
						System.out.print("================================================================");
						String insert_customerDetails = "INSERT INTO `customer` (`Customer_id`, `Customer_Type`, `Date_Of_Birth`, `Date_Incorp`, `Registration_No`, `RequestDoc_Doc_Data`)VALUES"
								+ "('" + (RequestDoc.getDocData().getDataItemCustomer()[index].getCustomerID().equals("") || RequestDoc.getDocData().getDataItemCustomer()[index].getCustomerID().equals(null) ? null : RequestDoc.getDocData().getDataItemCustomer()[index].getCustomerID())  +
								"', '" + (RequestDoc.getDocData().getDataItemCustomer()[index].getCustomerType().equals("") || RequestDoc.getDocData().getDataItemCustomer()[index].getCustomerType().equals(null) ? null : RequestDoc.getDocData().getDataItemCustomer()[index].getCustomerType()) +
								"', '" + (RequestDoc.getDocData().getDataItemCustomer()[index].getDateOfBirth().equals("") || RequestDoc.getDocData().getDataItemCustomer()[index].getDateOfBirth().equals(null)  ? null : RequestDoc.getDocData().getDataItemCustomer()[index].getDateOfBirth()) +
								"','" +  (RequestDoc.getDocData().getDataItemCustomer()[index].getDateIncorp().equals("") || RequestDoc.getDocData().getDataItemCustomer()[index].getDateIncorp().equals(null) ? null : RequestDoc.getDocData().getDataItemCustomer()[index].getDateIncorp())  +
								"','" + (RequestDoc.getDocData().getDataItemCustomer()[index].getRegistrationNo().equals("") || RequestDoc.getDocData().getDataItemCustomer()[index].getRegistrationNo().equals(null)  ? null : RequestDoc.getDocData().getDataItemCustomer()[index].getRegistrationNo()) + 
								"'," + docReferenceId + ");";
						
						System.out.println(insert_customerDetails);
						stmt = conn.prepareStatement(insert_customerDetails, Statement.RETURN_GENERATED_KEYS);
						stmt.executeUpdate();
						
						tableKeys = stmt.getGeneratedKeys();
						tableKeys.next();
						idGenerated = tableKeys.getInt(1);
						
						String insert_mailing_Address = "INSERT INTO `mailing_address` (`Address_Line1`, `Address_Line2`, `Town_City`, `Country`, `Customer_ID`)VALUES"
								+ "('" + (RequestDoc.getDocData().getDataItemCustomer()[index].getMailingAddress().getAddressLine1().equals("") || RequestDoc.getDocData().getDataItemCustomer()[index].getMailingAddress().getAddressLine1().equals(null) ? null : RequestDoc.getDocData().getDataItemCustomer()[index].getMailingAddress().getAddressLine1()) + "',' " +
								(RequestDoc.getDocData().getDataItemCustomer()[index].getMailingAddress().getAddressLine2().equals("")|| RequestDoc.getDocData().getDataItemCustomer()[index].getMailingAddress().getAddressLine2().equals(null)  ? null : RequestDoc.getDocData().getDataItemCustomer()[index].getMailingAddress().getAddressLine2()) +"', '" + 
								(RequestDoc.getDocData().getDataItemCustomer()[index].getMailingAddress().getTownCity().equals("") || RequestDoc.getDocData().getDataItemCustomer()[index].getMailingAddress().getTownCity().equals(null) ? null : RequestDoc.getDocData().getDataItemCustomer()[index].getMailingAddress().getTownCity()) + "','" +
								(RequestDoc.getDocData().getDataItemCustomer()[index].getMailingAddress().getCountry().equals("") || RequestDoc.getDocData().getDataItemCustomer()[index].getMailingAddress().getCountry().equals(null) ? null : RequestDoc.getDocData().getDataItemCustomer()[index].getMailingAddress().getCountry())+  "'," + 
								idGenerated +");";
						
						
						System.out.println(insert_mailing_Address);
						stmt = conn.prepareStatement(insert_mailing_Address, Statement.RETURN_GENERATED_KEYS);
						stmt.executeUpdate();
						
						tableKeys = stmt.getGeneratedKeys();
						tableKeys.next();
						
						
						String insert_contact_details = "INSERT INTO `contact_details` (`Contact_Name`, `Contact_Number`, `Customer_ID`) VALUES"
								+ "('" +(RequestDoc.getDocData().getDataItemCustomer()[index].getContactDetails().getContactName().equals("") ||  RequestDoc.getDocData().getDataItemCustomer()[index].getContactDetails().getContactName().equals(null) ? null : RequestDoc.getDocData().getDataItemCustomer()[index].getContactDetails().getContactName())+"','" +
								(RequestDoc.getDocData().getDataItemCustomer()[index].getContactDetails().getContactNumber().equals("") || RequestDoc.getDocData().getDataItemCustomer()[index].getContactDetails().getContactNumber().equals(null) ? null : RequestDoc.getDocData().getDataItemCustomer()[index].getContactDetails().getContactNumber()) +"'," + 
								idGenerated+");";
						
						System.out.println(insert_contact_details);
						stmt = conn.prepareStatement(insert_contact_details, Statement.RETURN_GENERATED_KEYS);
						stmt.executeUpdate();
						
						tableKeys = stmt.getGeneratedKeys();
						tableKeys.next();
						

						String insert_share_detail = "INSERT INTO `shares_details` (`Num_Shares`, `Share_Price`, `Customer_ID`) VALUES"
								+ "(' " + (RequestDoc.getDocData().getDataItemCustomer()[index].getSharesDetails().getNumShares().equals("") || RequestDoc.getDocData().getDataItemCustomer()[index].getSharesDetails().getNumShares().equals(null)  ? null : RequestDoc.getDocData().getDataItemCustomer()[index].getSharesDetails().getNumShares())  + "', ' " +
								(RequestDoc.getDocData().getDataItemCustomer()[index].getSharesDetails().getSharePrice().equals("") || RequestDoc.getDocData().getDataItemCustomer()[index].getSharesDetails().getSharePrice().equals(null) ? null : RequestDoc.getDocData().getDataItemCustomer()[index].getSharesDetails().getSharePrice()) + "'," +
								idGenerated + ");";
								
						System.out.println(insert_share_detail);
						stmt = conn.prepareStatement(insert_share_detail, Statement.RETURN_GENERATED_KEYS);
						stmt.executeUpdate();						
						tableKeys = stmt.getGeneratedKeys();
						
						success = true;
				}
		}
		
		}catch(Exception E) {
			System.out.println(E);
			Utils.logErrorMessage(E.getLocalizedMessage());
		}
		
		return success;
		
	}

	
	public static List<DataItemCustomer> GetCustomerDetails() {
		
		List<DataItemCustomer> listDataItem = new ArrayList<DataItemCustomer>();
		
		DatabaseConnection BDDConnection = new DatabaseConnection();
		Connection conn;
		conn = BDDConnection.ConnectDatabase();
		Statement stmnt;
		try 
		{
			
			stmnt = conn.createStatement() ;
			String query = "select customer.Customer_id , contact_details.Contact_Name, customer.Date_Of_Birth,customer.Date_Incorp,customer.Customer_Type,shares_details.Num_Shares,shares_details.Share_Price, shares_details.Num_Shares * shares_details.Share_Price AS Balance from customer "
					+ " inner join contact_details on "
					+ " customer.ID = contact_details.Customer_ID "
					+ " inner join shares_details on "
					+ " customer.ID = shares_details.Customer_ID;" ;
			
			System.out.println("join query ====>" + query);
			
			ResultSet rs = stmnt.executeQuery(query);
			
			while ( rs.next() ) {
				DataItemCustomer dataItem = new DataItemCustomer();
				ContactDetails contact = new ContactDetails();
				SharesDetails sharesDetails = new SharesDetails();
				dataItem.setCustomerID( (rs.getObject(1).equals("") || rs.getObject(1) == null || rs.getObject(1).equals("null")) ? null : rs.getObject(1).toString() );
				contact.setContactName((rs.getObject(2).equals("") || rs.getObject(2) == null || rs.getObject(2).equals("null")) ? null : rs.getObject(2).toString());
				dataItem.setDateOfBirth((rs.getObject(3).equals("") || rs.getObject(3) == null || rs.getObject(3).equals("null")) ? null : rs.getObject(3).toString());
				dataItem.setDateIncorp((rs.getObject(4).equals("") || rs.getObject(4) == null || rs.getObject(4).equals("null")) ? null : rs.getObject(4).toString());
				dataItem.setCustomerType((rs.getObject(5).equals("") || rs.getObject(5) == null || rs.getObject(5).equals("null")) ? null : rs.getObject(5).toString());
				sharesDetails.setNumShares((rs.getObject(6).equals("") || rs.getObject(6) == null || rs.getObject(6).equals("null")) ? null : rs.getObject(6).toString());
				sharesDetails.setSharePrice((rs.getObject(7).equals("") || rs.getObject(7) == null || rs.getObject(7).equals("null")) ? null : rs.getObject(7).toString());
				sharesDetails.setBalance((rs.getObject(8).equals("") || rs.getObject(8) == null || rs.getObject(8).equals("null")) ? null : rs.getObject(8).toString());

			    dataItem.setContactDetails(contact);
			    dataItem.setSharesDetails(sharesDetails);
			    
			    listDataItem.add(dataItem);
	        }
			
		}catch(Exception E) 
		{
			System.out.println(E);
			Utils.logErrorMessage(E.getLocalizedMessage());
		}
		
		return listDataItem;
	}
	
	public static List<DataItemCustomer> GetAllRecords(String name) {
			
			List<DataItemCustomer> listDataItem = new ArrayList<DataItemCustomer>();
			
			DatabaseConnection BDDConnection = new DatabaseConnection();
			Connection conn;
			conn = BDDConnection.ConnectDatabase();
			Statement stmnt;
			String query = null;
			try 
			{
				
				stmnt = conn.createStatement() ;
				
				if(name.toLowerCase().equals("all"))
				{
					query = "select customer.Customer_id , customer.Customer_Type , customer.Date_Of_Birth , customer.Date_Incorp , customer.Registration_No , contact_details.Contact_Name , contact_details.Contact_Number , mailing_address.Address_Line1, mailing_address.Address_Line2,mailing_address.Town_City,mailing_address.Country,shares_details.Num_Shares , shares_details.Share_Price , shares_details.Num_Shares * shares_details.Share_Price AS Balance from customer\r\n"
							+ "inner join contact_details on\r\n"
							+ "customer.ID = contact_details.Customer_ID\r\n"
							+ "inner join shares_details on\r\n"
							+ "customer.ID = shares_details.Customer_ID\r\n"
							+ "inner join mailing_address on \r\n"
							+ "customer.ID = mailing_address.Customer_ID\r\n";
				}else
				{
					query = "select customer.Customer_id , customer.Customer_Type , customer.Date_Of_Birth , customer.Date_Incorp , customer.Registration_No , contact_details.Contact_Name , contact_details.Contact_Number , mailing_address.Address_Line1, mailing_address.Address_Line2,mailing_address.Town_City,mailing_address.Country,shares_details.Num_Shares , shares_details.Share_Price , shares_details.Num_Shares * shares_details.Share_Price AS Balance from customer\r\n"
							+ "inner join contact_details on\r\n"
							+ "customer.ID = contact_details.Customer_ID\r\n"
							+ "inner join shares_details on\r\n"
							+ "customer.ID = shares_details.Customer_ID\r\n"
							+ "inner join mailing_address on \r\n"
							+ "customer.ID = mailing_address.Customer_ID\r\n"
							+ "where customer.Customer_ID = '" + name + "';";
				}
				
			
				
			
			System.out.println("join query ====>" + query);
			
			ResultSet rs = stmnt.executeQuery(query);
			
			while ( rs.next() ) {
				DataItemCustomer dataItem = new DataItemCustomer();
				ContactDetails contact = new ContactDetails();
				SharesDetails sharesDetails = new SharesDetails();
				MailingAddress mailingAddress = new MailingAddress();
				
				dataItem.setCustomerID( (rs.getObject(1).equals("") || rs.getObject(1) == null || rs.getObject(1).equals("null")) ? null : rs.getObject(1).toString() );
				dataItem.setCustomerType((rs.getObject(2).equals("") || rs.getObject(2) == null || rs.getObject(2).equals("null")) ? null : rs.getObject(2).toString());
				dataItem.setDateOfBirth((rs.getObject(3).equals("") || rs.getObject(3) == null || rs.getObject(3).equals("null")) ? null : rs.getObject(3).toString());
				dataItem.setDateIncorp((rs.getObject(4).equals("") || rs.getObject(4) == null || rs.getObject(4).equals("null")) ? null : rs.getObject(4).toString());
				dataItem.setRegistrationNo((rs.getObject(5).equals("") || rs.getObject(5) == null || rs.getObject(5).equals("null")) ? null : rs.getObject(5).toString());
				contact.setContactName((rs.getObject(6).equals("") || rs.getObject(6) == null || rs.getObject(6).equals("null")) ? null : rs.getObject(6).toString());
				contact.setContactNumber((rs.getObject(7).equals("") || rs.getObject(7) == null || rs.getObject(7).equals("null")) ? null : rs.getObject(7).toString());
				mailingAddress.setAddressLine1((rs.getObject(8).equals("") || rs.getObject(8) == null || rs.getObject(8).equals("null")) ? null : rs.getObject(8).toString());
				mailingAddress.setAddressLine2((rs.getObject(9).equals("") || rs.getObject(9) == null || rs.getObject(9).equals("null")) ? null : rs.getObject(9).toString());
				mailingAddress.setTownCity((rs.getObject(10).equals("") || rs.getObject(10) == null || rs.getObject(10).equals("null")) ? null : rs.getObject(10).toString());
				mailingAddress.setCountry((rs.getObject(11).equals("") || rs.getObject(11) == null || rs.getObject(11).equals("null")) ? null : rs.getObject(11).toString());
				sharesDetails.setNumShares((rs.getObject(12).equals("") || rs.getObject(12) == null || rs.getObject(12).equals("null")) ? null : rs.getObject(12).toString());
				sharesDetails.setSharePrice((rs.getObject(13).equals("") || rs.getObject(13) == null || rs.getObject(13).equals("null")) ? null : rs.getObject(13).toString());
				sharesDetails.setBalance((rs.getObject(14).equals("") || rs.getObject(14) == null || rs.getObject(14).equals("null")) ? null : rs.getObject(14).toString());
	
			    dataItem.setContactDetails(contact);
			    dataItem.setSharesDetails(sharesDetails);
			    dataItem.setMailingAddress(mailingAddress);
			    listDataItem.add(dataItem);
	        }
			
		}catch(Exception E) 
		{
			System.out.println(E);
			Utils.logErrorMessage(E.getLocalizedMessage());
		}
		
		return listDataItem;
	}
	
	public static boolean writeToCSV(String name) {
		
		List<DataItemCustomer> listDataItem = new ArrayList<DataItemCustomer>();
		
		DatabaseConnection BDDConnection = new DatabaseConnection();
		Connection conn;
		conn = BDDConnection.ConnectDatabase();
		Statement stmnt;
		String query = null;
		boolean success = false;
		try 
		{
			
			stmnt = conn.createStatement() ;
			query = "select customer.Customer_id , customer.Customer_Type , customer.Date_Of_Birth , customer.Date_Incorp , customer.Registration_No , contact_details.Contact_Name , contact_details.Contact_Number , mailing_address.Address_Line1, mailing_address.Address_Line2,mailing_address.Town_City,mailing_address.Country,shares_details.Num_Shares , shares_details.Share_Price , shares_details.Num_Shares * shares_details.Share_Price AS Balance from customer\r\n"
					+ "inner join contact_details on\r\n"
					+ "customer.ID = contact_details.Customer_ID\r\n"
					+ "inner join shares_details on\r\n"
					+ "customer.ID = shares_details.Customer_ID\r\n"
					+ "inner join mailing_address on \r\n"
					+ "customer.ID = mailing_address.Customer_ID\r\n"
					+ "where contact_details.Contact_Name like '%" + name + "%';";
				
			System.out.println("join query ====>" + query);
			
			
			ResultSet rs = stmnt.executeQuery(query);
			
	
			if(Utils.writeToCSV(rs))
			{
				success = true;
			}else
			{
				success = false;
			}
		
		
		
		
	}catch(Exception E) 
	{
		System.out.println(E);
		Utils.logErrorMessage(E.getLocalizedMessage());
	}
	
	return success;
}
	
	public static List<DataItemCustomer> GetByRecordName(String name) {
		
		List<DataItemCustomer> listDataItem = new ArrayList<DataItemCustomer>();
		
		DatabaseConnection BDDConnection = new DatabaseConnection();
		Connection conn;
		conn = BDDConnection.ConnectDatabase();
		Statement stmnt;
		String query = null;
		try 
		{
			
			stmnt = conn.createStatement() ;
			
			
			query = "select customer.Customer_id , customer.Customer_Type , customer.Date_Of_Birth , customer.Date_Incorp , customer.Registration_No , contact_details.Contact_Name , contact_details.Contact_Number , mailing_address.Address_Line1, mailing_address.Address_Line2,mailing_address.Town_City,mailing_address.Country,shares_details.Num_Shares , shares_details.Share_Price , shares_details.Num_Shares * shares_details.Share_Price AS Balance from customer\r\n"
					+ "inner join contact_details on\r\n"
					+ "customer.ID = contact_details.Customer_ID\r\n"
					+ "inner join shares_details on\r\n"
					+ "customer.ID = shares_details.Customer_ID\r\n"
					+ "inner join mailing_address on \r\n"
					+ "customer.ID = mailing_address.Customer_ID\r\n"
					+ "where contact_details.Contact_Name like '%" + name +"%'";
		
		System.out.println("join query ====>" + query);
		
		ResultSet rs = stmnt.executeQuery(query);
		
		while ( rs.next() ) {
			DataItemCustomer dataItem = new DataItemCustomer();
			ContactDetails contact = new ContactDetails();
			SharesDetails sharesDetails = new SharesDetails();
			MailingAddress mailingAddress = new MailingAddress();
			
			dataItem.setCustomerID( (rs.getObject(1).equals("") || rs.getObject(1) == null || rs.getObject(1).equals("null")) ? null : rs.getObject(1).toString() );
			dataItem.setCustomerType((rs.getObject(2).equals("") || rs.getObject(2) == null || rs.getObject(5).equals("null")) ? null : rs.getObject(2).toString());
			dataItem.setDateOfBirth((rs.getObject(3).equals("") || rs.getObject(3) == null || rs.getObject(3).equals("null")) ? null : rs.getObject(3).toString());
			dataItem.setDateIncorp((rs.getObject(4).equals("") || rs.getObject(4) == null || rs.getObject(4).equals("null")) ? null : rs.getObject(4).toString());
			dataItem.setRegistrationNo((rs.getObject(5).equals("") || rs.getObject(5) == null || rs.getObject(5).equals("null")) ? null : rs.getObject(5).toString());
			contact.setContactName((rs.getObject(6).equals("") || rs.getObject(6) == null || rs.getObject(6).equals("null")) ? null : rs.getObject(6).toString());
			contact.setContactNumber((rs.getObject(7).equals("") || rs.getObject(7) == null || rs.getObject(7).equals("null")) ? null : rs.getObject(7).toString());
			mailingAddress.setAddressLine1((rs.getObject(8).equals("") || rs.getObject(8) == null || rs.getObject(8).equals("null")) ? null : rs.getObject(8).toString());
			mailingAddress.setAddressLine2((rs.getObject(9).equals("") || rs.getObject(9) == null || rs.getObject(9).equals("null")) ? null : rs.getObject(9).toString());
			mailingAddress.setTownCity((rs.getObject(10).equals("") || rs.getObject(10) == null || rs.getObject(10).equals("null")) ? null : rs.getObject(10).toString());
			mailingAddress.setCountry((rs.getObject(11).equals("") || rs.getObject(11) == null || rs.getObject(11).equals("null")) ? null : rs.getObject(11).toString());
			sharesDetails.setNumShares((rs.getObject(12).equals("") || rs.getObject(12) == null || rs.getObject(12).equals("null")) ? null : rs.getObject(12).toString());
			sharesDetails.setSharePrice((rs.getObject(13).equals("") || rs.getObject(13) == null || rs.getObject(13).equals("null")) ? null : rs.getObject(13).toString());
			sharesDetails.setBalance((rs.getObject(14).equals("") || rs.getObject(14) == null || rs.getObject(14).equals("null")) ? null : rs.getObject(14).toString());

		    dataItem.setContactDetails(contact);
		    dataItem.setSharesDetails(sharesDetails);
		    dataItem.setMailingAddress(mailingAddress);
		    listDataItem.add(dataItem);
        }
		
	}catch(Exception E) 
	{
		System.out.println(E);
		Utils.logErrorMessage(E.getLocalizedMessage());
	}
	
	return listDataItem;
}
	
	public static boolean  updateCustomerDetails(DataItemCustomer[] data) {
		
		DatabaseConnection BDDConnection = new DatabaseConnection();
		Connection conn;
		conn = BDDConnection.ConnectDatabase();
		Statement stmnt;
		String query= "";
		int customerId = 0;
		boolean success = false;
		try {
			stmnt = conn.createStatement() ;
			for(int i=0;i<data.length;i++)
			{
				query = "select id from customer where Customer_id='" + data[i].getCustomerID()+ "';";
				ResultSet rs = stmnt.executeQuery(query);
				while ( rs.next() ) 
				{
					customerId = (int) rs.getObject(1);
				}
				
				if(!data[i].getCustomerType().toLowerCase().equals("corporate"))
				{
					
					//update customer details
					query = "UPDATE `customer` SET `Customer_Type` = '" + data[i].getCustomerType() + "',"
							+ " `Date_Of_Birth` = '" + data[i].getDateOfBirth() +
							"', `Date_Incorp` = '" + data[i].getDateIncorp() + 
							"', `Registration_No` = '" + data[i].getRegistrationNo() + 
							"' WHERE `customer`.`ID` = " +customerId + ";";
					
					System.out.println("query to update customer details ==>" + query);
					stmnt.executeUpdate(query);
					
					//update contact details
					query = "UPDATE `contact_details` SET `Contact_Name` = '" +data[i].getContactDetails().getContactName() +
							"', `Contact_Number` = '" + data[i].getContactDetails().getContactNumber() + 
							"' WHERE `contact_details`.`Customer_ID` = " + customerId + ";";
					
					System.out.println("query to update contact details ==>" + query);
					stmnt.executeUpdate(query);
					
					//update share details
					query = "UPDATE `shares_details` SET `Num_Shares` = '" + data[i].getSharesDetails().getNumShares() + 
							"', `Share_Price` = '" + data[i].getSharesDetails().getSharePrice() + 
							"' WHERE `shares_details`.`Customer_ID` = " + customerId  +";";
					
					System.out.println("query to update share details ==>" + query);
					stmnt.executeUpdate(query);
					
					//update mailing details
					query = "UPDATE `mailing_address` SET `Address_Line1` = '" +data[i].getMailingAddress().getAddressLine1() +
							"', `Address_Line2` = '" +data[i].getMailingAddress().getAddressLine2() + 
							"', `Town_City` = '" +data[i].getMailingAddress().getTownCity()+ 
							"', `Country` = '" + data[i].getMailingAddress().getCountry() +"' WHERE `mailing_address`.`Customer_ID` = " +customerId+ ";";
					
					System.out.println("query to update mailing details ==>" + query);
					stmnt.executeUpdate(query);
					
					success = true;
				}
			}
			
			
		}catch(Exception E)
		{
			System.out.println(E);
			Utils.logErrorMessage(E.getLocalizedMessage());
		}
		
		return success;
		
	}
	
}