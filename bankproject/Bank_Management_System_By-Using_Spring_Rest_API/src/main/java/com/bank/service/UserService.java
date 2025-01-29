package com.bank.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bank.entity.BankUserDetails;

public interface UserService {

	//Exception validateUser(BankUserDetails bankUserDetails);
	public ResponseEntity<ResponseStructure<BankUserDetails>> validateBankUser(BankUserDetails bankUserDetails);
	ResponseEntity<ResponseStructure<List<BankUserDetails>>> allAcceptedDetails();
	ResponseEntity<ResponseStructure<List<BankUserDetails>>> allPendingDetails();
	ResponseEntity<ResponseStructure<List<BankUserDetails>>> allClosingDetails();
	ResponseEntity<ResponseStructure<List<BankUserDetails>>> acceptPendingRequest(int id);
	ResponseEntity<ResponseStructure<String>> deleteUserDetailsByUsingEmailid(String emailid);
	ResponseEntity<ResponseStructure<String>> userLogin(String emailid,int pin);	
	ResponseEntity<ResponseStructure<BankUserDetails>> updateAcceptedDetails(int id, BankUserDetails updatedUserDetails);
	ResponseEntity<ResponseStructure<String>> updatePinByUsingAccountNumber(long accountnumber,int newPin);
//	ResponseEntity<ResponseStructure<String>> mobileToMobileTransaction(long sender,long receiver,double amount);
	ResponseEntity<ResponseStructure<String>> closeAccountByUsingAccountNumber(long accountnumber);
	}