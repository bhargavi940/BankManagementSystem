package com.bank.service;

import java.sql.Date;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.bank.DAO.BankUserDAO;
import com.bank.DAO.StatementDAO;
import com.bank.entity.BankUserDetails;
import com.bank.entity.Statement;
import com.bank.exceptions.BankUserException;
@Component
public class StatementServiceImpli implements StatementService{
	
	 @Autowired
	   StatementDAO statementDao;
	 @Autowired
	 BankUserDAO bankUserDao;
	 

	 @Override
	 public ResponseEntity<ResponseStructure<String>> creditAmount(long accountnumber, double amount) {
	     BankUserDetails userDetails = bankUserDao.getUserDetailsByUsingAccountNumber(accountnumber);
	     if (userDetails != null) {
	         double balanceAmount = userDetails.getAmount() + amount;
	         userDetails.setAmount(balanceAmount);
	         int userId = userDetails.getId();
	         bankUserDao.UpdateUserDetails(userDetails);
	         Statement statementdetails = new Statement();
	         statementdetails.setAccountnumber(accountnumber);
	         statementdetails.setTransactiontype("CREDIT");
	         statementdetails.setTransactionamount(amount);
	         statementdetails.setTransactiondate(Date.valueOf(LocalDate.now()));
	         statementdetails.setTransactiontime(Time.valueOf(LocalTime.now()));
	         statementdetails.setBalanceamount(balanceAmount);
	         Statement stat=statementDao.updateStatement(statementdetails);
	         System.out.println(stat);
	         ResponseStructure<String> responseStructure = new ResponseStructure<>();
	         responseStructure.setResponsemsg("Amount credited successfully. New balance: " + balanceAmount);
	         responseStructure.setHttpstatuscode(HttpStatus.OK.value());

	         return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	     } else {
	         throw new BankUserException("Failed to update the amount");
	     }
	 }
	 
	 
	 
	 @Override
	 public ResponseEntity<ResponseStructure<String>> debitAmount(long accountnumber, double amount) {
	     BankUserDetails userDetails = bankUserDao.getUserDetailsByUsingAccountNumber(accountnumber);
	     if(userDetails!=null)
			{
		    	double balanceAmount=userDetails.getAmount();
		    	if(amount<=balanceAmount) {
				balanceAmount=balanceAmount-amount;
				userDetails.setAmount(balanceAmount);
				int userId = userDetails.getId();
	         bankUserDao.UpdateUserDetails(userDetails);
	         Statement statementdetails = new Statement();
	         statementdetails.setAccountnumber(accountnumber);
	         statementdetails.setTransactiontype("DEBIT");
	         statementdetails.setTransactionamount(amount);
	         statementdetails.setTransactiondate(Date.valueOf(LocalDate.now()));
	         statementdetails.setTransactiontime(Time.valueOf(LocalTime.now()));
	         statementdetails.setBalanceamount(balanceAmount);
	         Statement stat=statementDao.updateStatement(statementdetails);
	         System.out.println(stat);
	         ResponseStructure<String> responseStructure = new ResponseStructure<>();
	         responseStructure.setResponsemsg("Amount Debited successfully. New balance: " + balanceAmount);
	         responseStructure.setHttpstatuscode(HttpStatus.OK.value());

	         return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		    	}
		    	else {
		    		throw new BankUserException("Insufficient balance");
		    	}
			}
			else
			{
				throw new BankUserException("Failed to update the amount");
			}
		 
		}
	 @Override
		public ResponseEntity<ResponseStructure<Double>> balanceAmount(long accountNumber) {
		    BankUserDetails userDetails = bankUserDao.getUserDetailsByUsingAccountNumber(accountNumber);
		    if (userDetails == null) {
		        throw new BankUserException("Account not found");
		    }
		    double currentBalance = userDetails.getAmount();
		    ResponseStructure<Double> responseStructure = new ResponseStructure<>();
		    responseStructure.setResponsemsg("Balance fetched successfully.");
		    responseStructure.setHttpstatuscode(HttpStatus.OK.value());
		    responseStructure.setData(currentBalance);
		    return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		}



	@Override
	public ResponseEntity<ResponseStructure<List<Statement>>> getStatementDetails(int pin) {
		BankUserDetails detailsByPin = bankUserDao.getDetailsByPin(pin);
		long accountNumber=detailsByPin.getAccountnumber();
		List<Statement> statement=statementDao.getStatementDetails();
		statement=statement.stream().filter(s->s.getAccountnumber()==accountNumber).collect(Collectors.toList());
		if(statement!=null) {
			ResponseStructure<List<Statement>> responseStructure= new  
					ResponseStructure<List<Statement>>();
			responseStructure.setData(statement);
			responseStructure.setResponsemsg("fetched statement");
			responseStructure.setHttpstatuscode(HttpStatus.FOUND.value());
			return new ResponseEntity<ResponseStructure<List<Statement>>>(responseStructure,HttpStatus.FOUND);
		}
		else {
			ResponseStructure<List<Statement>> responseStructure=new ResponseStructure<List<Statement>>();
			responseStructure.setData(statement);
			responseStructure.setResponsemsg("No Details found");
			responseStructure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<ResponseStructure<List<Statement>>>(responseStructure,HttpStatus.NOT_FOUND);
		}
	}
	
	@Override
	public ResponseEntity<ResponseStructure<String>> mobileToMobileTransaction(long sender, long receiver, double amount) {
	    BankUserDetails senderMobile=bankUserDao.getUserDetailsByMobileNumber(sender);
	    BankUserDetails receiverMobile=bankUserDao.getUserDetailsByMobileNumber(receiver);
	    if(senderMobile==null){
	        throw new BankUserException("Sender mobile number not found");
	    }
	    if(receiverMobile==null) {
	        throw new BankUserException("Receiver mobile number not found");
	    }
	    if(senderMobile.getAmount()<amount){
	        throw new BankUserException("Insufficient balance");
	    }
	    senderMobile.setAmount(senderMobile.getAmount() - amount);
	    receiverMobile.setAmount(receiverMobile.getAmount() + amount);
	    bankUserDao.UpdateUserDetails(senderMobile);
	    bankUserDao.UpdateUserDetails(receiverMobile);
	    Statement debitStatement = new Statement();
	    debitStatement.setAccountnumber(senderMobile.getAccountnumber());
	    debitStatement.setTransactiontype("Debit");
	    debitStatement.setTransactionamount(amount);
	    debitStatement.setBalanceamount(senderMobile.getAmount());
	    debitStatement.setTransactiondate(Date.valueOf(LocalDate.now()));
	    debitStatement.setTransactiontime(Time.valueOf(LocalTime.now()));
	    statementDao.updateStatement(debitStatement);

	    Statement creditStatement = new Statement();
	    creditStatement.setAccountnumber(receiverMobile.getAccountnumber());
	    creditStatement.setTransactiontype("Credit");
	    creditStatement.setTransactionamount(amount);
	    creditStatement.setBalanceamount(receiverMobile.getAmount());
	    creditStatement.setTransactiondate(Date.valueOf(LocalDate.now()));
	    creditStatement.setTransactiontime(Time.valueOf(LocalTime.now()));
	    statementDao.updateStatement(creditStatement);

	    ResponseStructure<String> responseStructure = new ResponseStructure<>();
	    responseStructure.setResponsemsg("Amount transferred successfully through mobile number");
	    responseStructure.setHttpstatuscode(HttpStatus.OK.value());
	    return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	}

}
