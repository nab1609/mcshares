package com.java.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DataItem_Customer")
public class DataItemCustomer {
	
	 	private String customerID;
	    private String customerType;
	    private String dateOfBirth;
	    private String dateIncorp;
	    private String registrationNo;
	    private MailingAddress mailingAddress;
	    private ContactDetails contactDetails;
	    private SharesDetails sharesDetails;
	   

	    
	    public String getCustomerID() { return customerID; }
	    
	    @XmlElement(name = "customer_id")
	    public void setCustomerID(String value) { this.customerID = value; }

	    
	    public String getCustomerType() { return customerType; }
	    
	    @XmlElement(name = "Customer_Type")
	    public void setCustomerType(String value)
	    { 
	    	this.customerType = value; 
    	}

	    
	    public String getDateOfBirth() { return dateOfBirth; }
	    
	    @XmlElement(name = "Date_Of_Birth")
	    public void setDateOfBirth(String value) { this.dateOfBirth = value; }

	    public String getDateIncorp() { return dateIncorp; }
	    
	    @XmlElement(name = "Date_Incorp")
	    public void setDateIncorp(String value) { this.dateIncorp = value; }

	    
	    
	    public String getRegistrationNo() { return registrationNo; }
	    
	    @XmlElements({
            @XmlElement(name = "REGISTRATION_NO"),
            @XmlElement(name = "Registration_No")
	    })
	    public void setRegistrationNo(String value) { this.registrationNo = value; }

	    
	    public MailingAddress getMailingAddress() { return mailingAddress; }
	    
	    @XmlElement(name = "Mailing_Address")
	    public void setMailingAddress(MailingAddress value) { this.mailingAddress = value; }


	    public ContactDetails getContactDetails() { return contactDetails; }
	    
	    @XmlElement(name = "Contact_Details")
	    public void setContactDetails(ContactDetails value) { this.contactDetails = value; }


	    public SharesDetails getSharesDetails() { return sharesDetails; }
	    
	    @XmlElement(name = "Shares_Details")
	    public void setSharesDetails(SharesDetails value) { this.sharesDetails = value; }

}
