package com.java.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Doc_Data")
public class DocData {
	
	private DataItemCustomer[] dataItemCustomer;


    public DataItemCustomer[] getDataItemCustomer() { return dataItemCustomer; }
    
	@XmlElement(name = "DataItem_Customer")
    public void setDataItemCustomer(DataItemCustomer[] value) { this.dataItemCustomer = value; }
}
