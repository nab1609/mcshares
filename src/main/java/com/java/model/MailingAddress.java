package com.java.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Mailing_Address")
public class MailingAddress {
	
	  	private String addressLine1;
	    private String addressLine2;
	    private String townCity;
	    private String country;

	    
	    public String getAddressLine1() { return addressLine1; }
	    
	    @XmlElement(name = "Address_Line1")
	    public void setAddressLine1(String value) { this.addressLine1 = value; }

	    

	    public String getAddressLine2() { return addressLine2; }
	    
	    @XmlElement(name = "Address_Line2")
	    public void setAddressLine2(String value) { this.addressLine2 = value; }

	    
	    
	    public String getTownCity() { return townCity; }
	    
	    @XmlElement(name = "Town_City")
	    public void setTownCity(String value) { this.townCity = value; }

	    
	    public String getCountry() { return country; }
	    
	    @XmlElement(name = "Country")
	    public void setCountry(String value) { this.country = value; }
}
