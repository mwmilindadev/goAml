package com.sasianet.aml.endpoint;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sasianet.aml.gen.Report;
import com.sasianet.aml.gen.ReportIndicatorType;
import com.sasianet.aml.gen.Report.ReportIndicators;
import com.sasianet.aml.gen.Report.Transaction;
import com.sasianet.aml.pojo.ProcedureParameter;
import com.sasianet.aml.pojo.ResponceMessage;
import com.sasianet.aml.servise.GoAmlStoreServise;
import com.sasianet.aml.servise.ReportEntiryServise;
import com.sasianet.aml.servise.ReportServise;
import com.sasianet.aml.servise.TransactionServise;

@RestController
@RequestMapping("/aml")
public class ReportEndpoint {
	@Autowired
	ReportServise reportServise;
	@Autowired
	ReportEntiryServise reportEntityServise;
	@Autowired
	TransactionServise transactionServise;
	
	@Autowired
	GoAmlStoreServise goAmlStoreService;
	
	

	@PostMapping("/report")
	public ResponseEntity<ResponceMessage> generateReport(@RequestBody ProcedureParameter procedureParameters) {
		ResponceMessage responceMessage = new ResponceMessage();
		try {
			
			System.out.println("procedureParameters::::>>"+procedureParameters);
			System.out.println("Procedure Execution Start::::");
			responceMessage=reportServise.save(procedureParameters); 
			System.out.println("Procedure Execution End::::");
			//responceMessage.setResponceStatus("OK");
			//responceMessage.setResponceMessage("Success");
			if(responceMessage.getResponceStatus().equals("OK")) {
				//System.out.println("Done");
				Report report = new Report();
				System.out.println("Entity SetData Start::::");
				reportEntityServise.setDate(report);
				System.out.println("Entity SetData End::::");
				
			    List<Report.Transaction>listTransactions= new ArrayList<Report.Transaction>();
				//transactionServise.setTransactionData();
				System.out.println("Transactions SetData Start::::");
			    listTransactions=transactionServise.setTransactionData();
				report.setTransaction(listTransactions);
				System.out.println("Transactions SetData End::::");
				List<Report.ReportIndicators> INDICATOR = new ArrayList<Report.ReportIndicators>();
				ReportIndicators ind= new ReportIndicators();
				ind.getIndicator().add(ReportIndicatorType.CTR);
				report.setReportIndicators(ind);
				//[Generate XML File]
				goAmlStoreService.storeDrive( report);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responceMessage.setResponceStatus("FAILED");
		}
		
		return ResponseEntity.ok(responceMessage);

	}

}
