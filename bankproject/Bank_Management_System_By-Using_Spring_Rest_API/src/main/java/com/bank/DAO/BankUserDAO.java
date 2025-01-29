package com.bank.DAO;

import java.util.List;

import java.util.Optional;

import com.bank.entity.BankUserDetails;

public interface BankUserDAO {
	BankUserDetails userRegistration(BankUserDetails bankuserdetails);
	
	
	
	List<BankUserDetails> getAllDetails();
	
	
	BankUserDetails getUserDetailsByUsingId(int id);
	BankUserDetails UpdateUserDetails(BankUserDetails bankUserDetails);
	Boolean validateUserDetailsByUsingEmailidAndPassword(String emailid,int pin);
	int deleteUserDetailsByUsingEmailid(String emailid);
	BankUserDetails getUserDetailsByUsingAccountNumber(long accountNumber);
	Optional<BankUserDetails> updateAcceptedDetailsById(int id);
	BankUserDetails  getDetailsByPin(int pin);
	BankUserDetails updatePinByUsingAccountNumber(long accountnumber);
	BankUserDetails getUserDetailsByMobileNumber(long mobilenumber);
	
}
