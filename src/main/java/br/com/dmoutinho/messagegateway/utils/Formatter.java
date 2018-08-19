package br.com.dmoutinho.messagegateway.utils;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class Formatter {
	
	public XMLGregorianCalendar dateToXMLGregorianCalendar(Date date) {

		XMLGregorianCalendar xmlGregorianCalendar = null;

		try {
			
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			gregorianCalendar.setTime(date);
			gregorianCalendar.setTimeZone(TimeZone.getDefault());

			DatatypeFactory dataTypeFactory = DatatypeFactory.newInstance();

			xmlGregorianCalendar = dataTypeFactory.newXMLGregorianCalendar(gregorianCalendar);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return xmlGregorianCalendar;

	}

}
