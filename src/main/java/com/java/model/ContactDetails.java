package com.java.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Contact_Details")
public class ContactDetails {
	private String contactName;
    private String contactNumber;

    
    public String getContactName() { return contactName; }
    
    @XmlElement(name = "Contact_Name")
    public void setContactName(String value) { this.contactName = value; }
    
    public String getContactNumber() { return contactNumber; }
    
    @XmlElement(name = "Contact_Number")
    public void setContactNumber(String value) { this.contactNumber = value; }
}
