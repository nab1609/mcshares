package com.java.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.java.database.CustomerDAO;
import com.java.model.RequestDoc;
import com.java.tools.Utils;

@RestController
public class XmlController {
	
	@RequestMapping(value = "/uploadCustomerDetails", method = RequestMethod.POST , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> uploadCustomer(@RequestPart("file") MultipartFile file) throws IOException {
		
		InputStream input = file.getInputStream();
		byte[] buffer = new byte[input.available()];
		ResponseEntity entity = null;
		
		try {
			
			if (file.getOriginalFilename() == null ) {
				
				entity =  new ResponseEntity<>("NO FILE HAS BEEN UPLOADED",HttpStatus.NOT_FOUND);
				Utils.logErrorMessage("NO FILE HAS BEEN UPLOADED");
				
			}else
			{

				if(!file.getContentType().equals("application/xml"))
				{				
					entity =  new ResponseEntity<>("BAD FILE EXTENSION",HttpStatus.UNSUPPORTED_MEDIA_TYPE);
					Utils.logErrorMessage("BAD FILE EXTENSION");
					
				}else
				{
					input.read(buffer);
		
					File targetFile = new File("src/main/resources/temporaryFile.tmp.xml");
					try (OutputStream outStream = new FileOutputStream(targetFile)) {
					    outStream.write(buffer);
					}
					
					if(Utils.validateAgainstXSD(targetFile.getAbsolutePath(), "XSD/XSD.XML"))
					{
						Utils.storeXMLBDD(targetFile.getAbsolutePath());
						File validatedXMLFile = new File(targetFile.getAbsolutePath());
						JAXBContext jaxbContext = JAXBContext.newInstance(RequestDoc.class);
						Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
						RequestDoc RequestDoc = (RequestDoc) jaxbUnmarshaller.unmarshal(validatedXMLFile);
						
						if(CustomerDAO.CreateCustomerRecord(RequestDoc))
						{
							System.out.println("Customer has been created");
							entity =  new ResponseEntity<>("UPLOADED + CREATED CUSTOMER",HttpStatus.OK);
							Utils.logErrorMessage("UPLOADED + CREATED CUSTOMER");
						}
						
					}else
					{
						entity = new ResponseEntity<>("XML IS NOT VALID",HttpStatus.BAD_REQUEST);
						Utils.logErrorMessage("XML IS NOT VALID");
					}
					
				}
				
			}
		}catch(Exception E)
		{
			
			entity = new ResponseEntity<>("AN ERROR HAS OCCURED",HttpStatus.BAD_REQUEST);
			Utils.logErrorMessage(E.getLocalizedMessage());
		}

		return entity;
		
	}
}
