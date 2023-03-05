package com.sasianet.aml.servise;



import com.sasianet.aml.pojo.ProcedureParameter;
import com.sasianet.aml.pojo.ResponceMessage;

public interface ReportServise {
	
	ResponceMessage save(ProcedureParameter procedureParameters) throws Exception; 

	//ProcedureParameter save(String procedureParameters);

}
