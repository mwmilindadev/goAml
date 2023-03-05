package com.sasianet.aml.serviseImpl;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import org.springframework.stereotype.Service;

import com.sasianet.aml.pojo.ProcedureParameter;
import com.sasianet.aml.pojo.ResponceMessage;
import com.sasianet.aml.servise.ReportServise;
import com.sasianet.aml.util.DBManager;


@Service
public class ReportServiseImpl implements ReportServise{
	Connection connection = null;
	PreparedStatement statement = null;
	PreparedStatement subStatement = null;
	

	@Override
	public ResponceMessage save(ProcedureParameter procedureParameters) throws Exception {
		// TODO Auto-generated method stub
		Connection connection = DBManager.getInstance().getConnection();
		ResponceMessage responceMessage = new ResponceMessage();
		System.out.println(procedureParameters.getStartDate());
		System.out.println(procedureParameters.getEndDate());
		try {
			CallableStatement callstmt = connection.prepareCall( "BEGIN "+
					" GO_AML ( "+
					"    :v0, "+
					"    :v1 "+
					");"+
					" END; ");
			
			callstmt.setString(1, procedureParameters.getStartDate());
			callstmt.setString(2, procedureParameters.getEndDate());
			callstmt.execute();
			connection.commit();
			responceMessage.setResponceStatus("OK");
			DBManager.getInstance().closeConnection(connection);
			System.out.println(":::::Procedure Execution is OK!::::");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			responceMessage.setResponceStatus("FAILED");
		}
		return responceMessage;
	}

//	@Override
//	public ProcedureParameter save(String procedureParameters) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	
	
	

}
