package com.java.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RequestDoc")
public class RequestDoc {
	
	private String docDate;
    private String docRef;
    private DocData docData;
    
	
    public String getDocDate() {
		return docDate;
	}
    
    @XmlElement(name = "Doc_Date")
	public void setDocDate(String docDate) {
		this.docDate = docDate;
	}
	
	public String getDocRef() {
		return docRef;
	}
	
	@XmlElement(name = "Doc_Ref")
	public void setDocRef(String docRef) {
		this.docRef = docRef;
	}
	
	public DocData getDocData() {
		return docData;
	}
	
	@XmlElement(name = "Doc_Data")
	public void setDocData(DocData docData) {
		this.docData = docData;
	}
	
}
