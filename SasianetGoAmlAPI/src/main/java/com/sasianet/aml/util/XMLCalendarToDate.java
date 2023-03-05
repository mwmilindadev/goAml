package com.sasianet.aml.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class XMLCalendarToDate {

	public XMLGregorianCalendar getXmlDate() {
		Date today = new Date();

		// Converting date to XMLGregorianCalendar in Java
		XMLGregorianCalendar xml = toXMLGregorianCalendar(today);
		//System.out.println("XMLGregorianCalendar from Date in Java      : " + xml);

		// Converting XMLGregorianCalendar to java.util.Date in Java
		Date date = toDate(xml);
		//System.out.println("java.util.Date from XMLGregorianCalendar in Java : " + date);
		return xml;

	}
	
	
	public XMLGregorianCalendar getConvertXmlDate(Date today) {
		//Date today = new Date();
		System.out.println("XMLGregorianCalendar from today     : " + today);
		// Converting date to XMLGregorianCalendar in Java
		XMLGregorianCalendar xmlDate = null;
		XMLGregorianCalendar xml = toXMLGregorianCalendar(today);
		System.out.println("XMLGregorianCalendar from Date in Java      : " + xml);
		
		// Converting XMLGregorianCalendar to java.util.Date in Java
		
		Date date = toDate(xml);
		System.out.println("java.util.Date from XMLGregorianCalendar in Java : " + date);
		return xml;

	}

	/*
	 * Converts java.util.Date to javax.xml.datatype.XMLGregorianCalendar
	 */
	public static XMLGregorianCalendar toXMLGregorianCalendar(Date date) {
		GregorianCalendar gCalendar = new GregorianCalendar();
		String formatedData = null;
		gCalendar.setTime(date);
		XMLGregorianCalendar xmlCalendar = null;
		SimpleDateFormat in_data = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat out_data = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	    
        formatedData = out_data.format(date);
        String df = formatedData;
		try {
			xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(df);
			xmlCalendar.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
		} catch (DatatypeConfigurationException ex) {
			ex.printStackTrace();
		}
		return xmlCalendar;
	}
	
	public static XMLGregorianCalendar toXMLGregorianCalendarold(Date date) {
		GregorianCalendar gCalendar = new GregorianCalendar();
		gCalendar.setTime(date);
		XMLGregorianCalendar xmlCalendar = null;
		
		try {
			xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
			xmlCalendar.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
		} catch (DatatypeConfigurationException ex) {
			ex.printStackTrace();
		}
		return xmlCalendar;
	}

	/*
	 * Converts XMLGregorianCalendar to java.util.Date in Java
	 */
	public static Date toDate(XMLGregorianCalendar calendar) {
		if (calendar == null) {
			return null;
		}
		return calendar.toGregorianCalendar().getTime();
	}

}
