package com.sasianet.aml.serviseImpl;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.stereotype.Service;

import com.sasianet.aml.gen.Report;
import com.sasianet.aml.servise.GoAmlStoreServise;
@Service
public class GoAmlStoreServiseImpl implements GoAmlStoreServise {

	@Override
	public void storeDrive(Report report) {
		// TODO Auto-generated method stub
		
		
		      try
		      {
		        //Create JAXB Context
		          JAXBContext jaxbContext = JAXBContext.newInstance(Report.class);		           
		          //Create Marshaller
		          Marshaller jaxbMarshaller = jaxbContext.createMarshaller();		 
		          //Required formatting??
		          jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);		 
		         //Store XML to File
		          File file = new File("D://GoAml//goAML.xml");
		          System.out.println("File Stored to drive");		           
		          //Writes XML file to file-system
		          jaxbMarshaller.marshal(report, file); 
		      } 
		      catch (JAXBException e) 
		      {
		          e.printStackTrace();
		      }
		  
		
	}

	

}
