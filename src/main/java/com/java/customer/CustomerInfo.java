package com.java.customer;

import org.springframework.web.bind.annotation.RequestMapping;

import com.java.database.CustomerDAO;



public class CustomerInfo {

	
	@RequestMapping("/GetCustomerDetails")
	public String helloWorld() {
		CustomerDAO.GetCustomerDetails();
		
		return "lol";
	}
	
	
}
