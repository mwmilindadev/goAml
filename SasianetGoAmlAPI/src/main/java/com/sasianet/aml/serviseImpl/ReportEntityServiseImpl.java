package com.sasianet.aml.serviseImpl;



import org.springframework.stereotype.Service;

import com.sasianet.aml.gen.CurrencyType;
import com.sasianet.aml.gen.Report;
import com.sasianet.aml.gen.ReportType;
import com.sasianet.aml.servise.ReportEntiryServise;
import com.sasianet.aml.util.XMLCalendarToDate;



@Service
public class ReportEntityServiseImpl implements ReportEntiryServise{

	

	@Override
	public void setDate(Report report) {
		// TODO Auto-generated method stub
		report.setRentityId(103);
		report.setRentityBranch("Head Office");
		report.setSubmissionCode("E");
		report.setReportCode(ReportType.CTR);
		//report.setEntityReference(null);
		//report.setFiuRefNumber(null);
		report.setSubmissionDate(new XMLCalendarToDate().getXmlDate());
		report.setCurrencyCodeLocal(CurrencyType.LKR);
		//report.setReportingPerson(null);
		//report.setLocation(null);
		//report.setAction(null);
		System.out.println("report::::"+report);
		
	}

	
	
	

}
