package com.sasianet.aml.serviseImpl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.stereotype.Service;

import com.sasianet.aml.gen.Report;
import com.sasianet.aml.gen.Report.ReportIndicators;
import com.sasianet.aml.gen.Report.Transaction;
import com.sasianet.aml.gen.Report.Transaction.TFromMyClient;
import com.sasianet.aml.gen.Report.Transaction.TToMyClient;
import com.sasianet.aml.gen.ReportIndicatorType;
import com.sasianet.aml.gen.TAccount;
import com.sasianet.aml.gen.TAccountMyClient;
import com.sasianet.aml.gen.TAccountMyClient.Signatory;
import com.sasianet.aml.gen.TAddress;
import com.sasianet.aml.gen.TPersonMyClient;
import com.sasianet.aml.gen.TPersonMyClient.Addresses;
import com.sasianet.aml.servise.TransactionServise;
import com.sasianet.aml.util.DBManager;
import com.sasianet.aml.util.XMLCalendarToDate;





@Service
public class TransactionServiseImpl implements TransactionServise {



	

	
	@Override
	public  List<Report.Transaction> setTransactionData() {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		String sql = null;
		
		//Report report= new Report();
		List<Report.Transaction> transactionList = new ArrayList<Report.Transaction>(); 
		try {
			connection=DBManager.getInstance().getConnection();
			
			 sql = "SELECT"+
					"    transactionnumber,"+
					"    internalrefnumber,"+
					"    transactionlocation,"+
					"    transactiondescription,"+
					"    datetransaction,"+
					"    teller,"+
					"    authorized,"+
					"    latedeposit,"+
					"    dateposting,"+
					"    valuedate,"+
					"    transmodecode,"+
					"    transmodecomment,"+
					"    amountlocal,"+
					"    involvedparties,"+
					"    fromparty,"+
					"    toparty,"+
					"    from_source,"+
					"    to_destination,"+
					"    goodsservices,"+
					"    comments,"+
					"    from_funds_code,"+
					"    from_country,"+
					"    client_code, "+
					"    IS_FROM_TO_STATUS "+
					"FROM"+
					"    go_aml_transactions ";
			statement=connection.prepareStatement(sql);
			result=statement.executeQuery();
			
			while(result.next()) {
				Transaction reportTransaction= new Report.Transaction();
				System.out.println("result.getString(\"transactionnumber\")"+result.getString("transactionnumber"));
				reportTransaction.setTransactionnumber(result.getString("transactionnumber"));
				reportTransaction.setInternalRefNumber(result.getString("internalrefnumber"));
				reportTransaction.setTransactionLocation(result.getString("transactionlocation"));
				reportTransaction.setTransactionDescription(result.getString("transactiondescription"));
				reportTransaction.setDateTransaction(new XMLCalendarToDate().getConvertXmlDate(result.getDate("datetransaction")));
				//reportTransaction.setLateDeposit(null);
				//reportTransaction.setDatePosting(null);
				//reportTransaction.setValueDate(null);
				reportTransaction.setTransmodeCode(result.getString("transmodecode"));
				//reportTransaction.setTransmodeComment(null);
				reportTransaction.setAmountLocal(result.getBigDecimal("amountlocal"));
				//reportTransaction.setGoodsServices(null);
				//reportTransaction.setComments(sql);
				
				
				reportTransaction.setTFromMyClient(setTFromMyClient(reportTransaction,result));
				
				reportTransaction.setTToMyClient(setTToMyClient(reportTransaction,result));
				
				/*if(result.getString("IS_FROM_TO_STATUS").equals("t_from_my_client")) {
					//reportTransaction.setFrom_country(result.getString("from_country"));
					//reportTransaction.setFrom_funds_code(result.getString("from_funds_code"));
					System.out.println("*****************1");
					
					
					reportTransaction.setTFromMyClient(setTFromMyClient(reportTransaction,result));
				}else {
					//reportTransaction.setToFundsCode(result.getString("from_country"));
					//reportTransaction.setFrom_funds_code(result.getString("from_funds_code"));
					System.out.println("*****************2");
					
					reportTransaction.setTToMyClient(setTToMyClient(reportTransaction,result));
				}*/
				
				
				transactionList.add(reportTransaction);
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//report.setTransaction(transactionList);
		return transactionList;
		
	}
	


public TToMyClient setTToMyClient(Transaction reportTransaction,ResultSet result) throws SQLException {
		// TODO Auto-generated method stub
	TToMyClient tToMyClient = new TToMyClient();
	//tToMyClient.setToFundsCode(reportTransaction.getFrom_funds_code());
	//tToMyClient.setToCountry(reportTransaction.getFrom_country());

	tToMyClient.setToFundsCode(result.getString("from_funds_code"));
	tToMyClient.setToCountry(result.getString("from_country"));
	tToMyClient.setToAccount(setTAccountMyClient(reportTransaction.getTransactionnumber()));
	
		return tToMyClient;
	}



private TAccountMyClient setTAccountMyClient(String transactionnumber) {
	// TODO Auto-generated method stub
	Connection connection = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	String sql = null;
	TAccountMyClient tAccountMyClient = new TAccountMyClient();
	
	 
	try {
		connection=DBManager.getInstance().getConnection();
		
		 sql = "SELECT"+
				 "    institutionname,"+
				 "    swift,"+
				 "    nonbankinstitution,"+
				 "    branch,"+
				 "    account,"+
				 "    currencycode,"+
				 "    personalaccounttype,"+
				 "    statuscode,"+
				 "    transactionnumber,"+
				 "    role "+
				 " FROM "+
				 "    GO_AML_ACCOUNT where transactionnumber=?  ";
		 
		 statement=connection.prepareStatement(sql);
		 statement.setString(1, transactionnumber);
		 result=statement.executeQuery();
		 
		 while(result.next()) {
			 
			   tAccountMyClient.setInstitutionName(result.getString("institutionname"));
			   tAccountMyClient.setSwift(result.getString("swift")); //7010 LANKA CLEAR 0036 LANKA FIN
			   tAccountMyClient.setNonBankInstitution(true);
			   tAccountMyClient.setBranch(result.getString("branch"));
			   tAccountMyClient.setAccount(result.getString("account"));
			   if(result.getString("currencycode").equals("LKR")) {
				   tAccountMyClient.setCurrencyCode(com.sasianet.aml.gen.CurrencyType.LKR);   
			   }
			   
			   tAccountMyClient.setPersonalAccountType(result.getString("personalaccounttype"));
			   
			   //tAccountMyClient.getSignatory(setSignatory(transactionnumber));
			   System.out.println("666666");
               //For Individual Customars
			   tAccountMyClient.getSignatory().addAll(setSignatory(transactionnumber,result.getString("role")));
			   //For Coporate -- need to devlop
			   
			   
			   tAccountMyClient.setStatusCode(result.getString("statuscode"));
			 
		 }
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	
	return tAccountMyClient;
}


public List<Signatory> setSignatory(String transactionnumber,String role) {
	List<TAccountMyClient.Signatory> signatory = new ArrayList<TAccountMyClient.Signatory>();
	TAccountMyClient.Signatory sig= new TAccountMyClient.Signatory();
	sig.setIsPrimary(true);
	sig.setTPerson(setTPersonMyClient(transactionnumber));
	sig.setRole(role);
	signatory.add(sig);
	
	return signatory;
	
}




public TFromMyClient setTFromMyClient(Transaction reportTransaction,ResultSet result) throws SQLException {	
		
		TFromMyClient tFromMyClient = new TFromMyClient();		
	
		tFromMyClient.setFromFundsCode(result.getString("from_funds_code"));
		tFromMyClient.setFromCountry(result.getString("from_country"));
		//[Set values for t_person_my_client]
		tFromMyClient.setFromPerson(setTPersonMyClient(reportTransaction.getTransactionnumber()));
		
		return tFromMyClient;
			
		
	}

public TPersonMyClient setTPersonMyClient(String transactionNumber) {
	System.out.println("test");
	TPersonMyClient tPersonMyClient = new TPersonMyClient();
	
	Connection connection = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	String sql = null;
	
	try {
		connection=DBManager.getInstance().getConnection();
		
		 sql    = " SELECT"+
				"    transactionnumber,"+
				"    gender,"+
				"    title,"+
				"    firstname,"+
				"    lastname,"+
				"    birthdate,"+
				"    ssn,"+
				"    passportnumber,"+
				"    passportcountry,"+
				"    idnumber,"+
				"    nationality1,"+
				"    nationality2,"+
				"    nationality3,"+
				"    residence,"+
				"    email,"+
				"    occupation,"+
				"    employername,"+
				"    homeaddress, "+
				"    homecity, "+
				"    othaddress, "+
				"    othcity, "+
				"    country "+
				"FROM"+
				"    GO_AML_CLIENT where transactionnumber=? ";
		 
		 statement=connection.prepareStatement(sql);
		 statement.setString(1, transactionNumber);
		 result=statement.executeQuery();
		 while(result.next()){
			 tPersonMyClient.setFirstName(result.getString("firstname")); 
			 tPersonMyClient.setLastName(result.getString("lastname"));
			 tPersonMyClient.setBirthdate(new XMLCalendarToDate().getConvertXmlDate(result.getDate("birthdate")) );
			 tPersonMyClient.setSsn(result.getString("ssn"));
			 if(!result.getString("nationality1").equals("LK")) {
				 tPersonMyClient.setPassportNumber(result.getString("passportnumber"));
				 tPersonMyClient.setPassportCountry(result.getString("passportcountry"));
				 
			 }
			 tPersonMyClient.setNationality1(result.getString("nationality1"));
			 tPersonMyClient.setResidence(result.getString("residence"));
			 
			 TAddress tAddress = new TAddress();
			 if(result.getString("homeaddress").length()>1) {
				 tAddress.setAddressType("PRVT");
				 tAddress.setAddress(result.getString("homeaddress"));
				 tAddress.setCity(result.getString("homecity"));
				 tAddress.setCountryCode(result.getString("country"));
			 }else if(result.getString("othaddress").length()>1){
				 tAddress.setAddressType("OTH");
				 tAddress.setAddress(result.getString("othaddress"));
				 tAddress.setCity(result.getString("othcity"));
				 tAddress.setCountryCode(result.getString("country"));
			 }
			 List<TAddress> tAddresses = new ArrayList();				
				 tAddresses.add(tAddress);
			     Addresses addresses = new Addresses();
			     addresses.setAddress(tAddresses);
			     
			     tPersonMyClient.setAddresses(addresses);
			     tPersonMyClient.setOccupation(result.getString("occupation"));
		 }
		 
		 
		 
		
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	
	
	
	
	
	
	return tPersonMyClient;
	
}

	
}
