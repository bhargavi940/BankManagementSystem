package com.bank.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.bank.entity.BankUserDetails;
import com.bank.repository.AdminRepository;
import com.bank.repository.BankUserRepository;

@Component
public class BankUserDAOImpl implements BankUserDAO{

	@Autowired
	BankUserRepository userrepository;
	@Override
	public BankUserDetails userRegistration(BankUserDetails bankuserdetails) {
		 return userrepository.save(bankuserdetails);
		
		
	}
	
	@Override
	public List<BankUserDetails> getAllDetails() {
		return userrepository.findAll();
	}

	@Override
	public BankUserDetails getUserDetailsByUsingId(int id) {
		 return userrepository.findById(id);
	
		
	}

	@Override
	public BankUserDetails UpdateUserDetails(BankUserDetails bankUserDetails) {
		return userrepository.save(bankUserDetails);
		
	}
	@Override
	public int deleteUserDetailsByUsingEmailid(String emailid) {
		
		return userrepository.deleteByEmailid(emailid);
	}
	@Override
	public Boolean validateUserDetailsByUsingEmailidAndPassword(String emailid,int pin) {
		return userrepository.existsByEmailidAndPin(emailid, pin);
	}

	@Override
	public BankUserDetails getUserDetailsByUsingAccountNumber(long accountNumber) {
	return userrepository.findByAccountnumber(accountNumber);
	}

	@Override
	public Optional<BankUserDetails> updateAcceptedDetailsById(int id) {
		return userrepository.searchById(id);
	}

	@Override
	public BankUserDetails getDetailsByPin(int pin) {
		return userrepository.findByPin(pin);
	}

	@Override
	public BankUserDetails updatePinByUsingAccountNumber(long accountnumber) {
		return userrepository.findByAccountnumber(accountnumber);
		
	}

	@Override
	public BankUserDetails getUserDetailsByMobileNumber(long mobilenumber) {
		return userrepository.findByMobilenumber(mobilenumber);
	}


}
