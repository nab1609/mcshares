package com.java.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.java.database.CustomerDAO;
import com.java.model.DataItemCustomer;
import com.java.tools.Utils;

@RestController
public class CustomerController {

	
	@RequestMapping(method = RequestMethod.GET ,value =  "/GetBalance"  , produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> GetCustomerDetails() {
		
		List<DataItemCustomer> customerDetails;
		ResponseEntity entity = null;
		
		try {
			
			customerDetails = CustomerDAO.GetCustomerDetails();
			
			if(customerDetails.isEmpty())
			{
				entity =  new ResponseEntity<>("NO CUSTOMER FOUND",HttpStatus.NOT_FOUND);
			}else
			{
				String json = new Gson().toJson(customerDetails);
				entity =  new ResponseEntity<>(json , HttpStatus.OK);
			}
		}catch(Exception E)
		{
			System.out.println(E);
			Utils.logErrorMessage(E.getLocalizedMessage());
		}
		
		return entity;
	}
	
	@RequestMapping(method = RequestMethod.GET ,value =  "/GetRecords/view"  , produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> GetAllRecords(@RequestParam String view) {
		
		List<DataItemCustomer> customerDetails;
		ResponseEntity entity = null;
		
		try {
			
			customerDetails = CustomerDAO.GetAllRecords(view);
			
			if(customerDetails.isEmpty())
			{
				entity =  new ResponseEntity<>("NO RECORDS WERE FOUND",HttpStatus.NOT_FOUND);
				
			}else
			{
				String json = new Gson().toJson(customerDetails);
				entity =  new ResponseEntity<>(json , HttpStatus.OK);
			}
		}catch(Exception E)
		{
			entity =  new ResponseEntity<>("NO RECORDS WERE FOUND",HttpStatus.NOT_FOUND);
			Utils.logErrorMessage(E.getLocalizedMessage());
		}
		
		return entity;
	}
	
	@RequestMapping(method = RequestMethod.GET ,value =  "/GetRecordByName/name"  , produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> GetRecordByName(@RequestParam String name) {
		
		List<DataItemCustomer> customerDetails;
		ResponseEntity entity = null;
		
		try {
			customerDetails = CustomerDAO.GetByRecordName(name);
			
			if(customerDetails.isEmpty())
			{
				entity =  new ResponseEntity<>("NO RECORDS WERE FOUND",HttpStatus.NOT_FOUND);
				
			}else
			{
				String json = new Gson().toJson(customerDetails);
				entity =  new ResponseEntity<>(json , HttpStatus.OK);
			}
		}catch(Exception E)
		{
			entity =  new ResponseEntity<>("NO RECORDS WERE FOUND",HttpStatus.NOT_FOUND);
			Utils.logErrorMessage(E.getLocalizedMessage());
		}
		
		return entity;
	}
	
	@RequestMapping(method = RequestMethod.GET ,value =  "/GetCSV/name"  , produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> GetCSV(@RequestParam String name) {
	
		ResponseEntity entity = null;
		
		try {
			if(CustomerDAO.writeToCSV(name))
			{
				File file = new File("src/main/resources/customerdetail.csv");
				if(file.exists()){
			        InputStream inputStream = new FileInputStream("src/main/resources/customerdetail.csv");
			        String type=file.toURL().openConnection().guessContentTypeFromName("src/main/resources/customerdetail.csv");

			        byte[]out=inputStream.readAllBytes();

			        HttpHeaders responseHeaders = new HttpHeaders();
			        responseHeaders.add("content-disposition", "attachment; filename=customerdetail.csv");
			        responseHeaders.add("Content-Type",type);

			        entity = new ResponseEntity(out, responseHeaders,HttpStatus.OK);
			    }else{
			    	entity = new ResponseEntity ("FILE NOT FOUND", HttpStatus.NOT_FOUND);
			    	
			    }
			}else
			{
				entity =  new ResponseEntity<>("NO RECORDS WERE FOUND",HttpStatus.NOT_FOUND);
				
			}
			
		}catch(Exception E)
		{
			entity =  new ResponseEntity<>("NO RECORDS WERE FOUND",HttpStatus.NOT_FOUND);	
			Utils.logErrorMessage(E.getLocalizedMessage());
		}
		return entity;
	}
	
	
	@RequestMapping(method = RequestMethod.POST ,value =  "/UpdateCustomerDetails"  , consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> UpdateCustomerDetails(InputStream  incomingData) {
		
		ResponseEntity entity = null;
		StringBuilder crunchifyBuilder = new StringBuilder();
		Gson gson = new Gson();
		try {
			
			 BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
             String line = null;
             while ((line = in.readLine()) != null) {
                 crunchifyBuilder.append(line);
             }
             
             DataItemCustomer[] data = gson.fromJson(crunchifyBuilder.toString(), DataItemCustomer[].class);
             
             if(CustomerDAO.updateCustomerDetails(data))
             {
            	 entity =  new ResponseEntity<>("RECORD UPDATED",HttpStatus.OK);	
             }else
             {
            	 entity =  new ResponseEntity<>("RECORD NOT UPDATED",HttpStatus.NOT_FOUND);	
            	 
             }
             
		}catch(Exception E)
		{
			System.out.println(E);
			Utils.logErrorMessage(E.getLocalizedMessage());
		}
		
		return entity;
	}
	
	
	
}
