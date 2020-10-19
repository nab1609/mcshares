package com.java.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Shares_Details")
public class SharesDetails {

	private String numShares;
    private String sharePrice;
    private String balance;
    
    public String getNumShares() { return numShares; }
    
    @XmlElement(name = "Num_Shares")
    public void setNumShares(String value) { this.numShares = value; }

    
    public String getSharePrice() { return sharePrice; }
    
    @XmlElement(name = "Share_Price")
    public void setSharePrice(String value) { this.sharePrice = value; }
    
    
    public void setBalance(String value) { this.balance = value; }
    
}
