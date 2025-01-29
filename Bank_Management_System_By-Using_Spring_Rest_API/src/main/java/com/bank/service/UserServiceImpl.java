package com.bank.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.bank.DAO.BankUserDAO;
import com.bank.DAO.StatementDAO;
import com.bank.entity.BankUserDetails;
import com.bank.entity.Statement;
import com.bank.exceptions.AadharExistException;
import com.bank.exceptions.AdminLoginException;
import com.bank.exceptions.BankUserException;
import com.bank.exceptions.EmailExistExceeption;
import com.bank.exceptions.MobileNumberExistException;
import com.bank.exceptions.PANExistException;
import com.bank.exceptions.UserLoginException;
import com.bank.repository.BankUserRepository;

import jakarta.transaction.Transactional;
@Component
public class UserServiceImpl implements UserService{

	@Autowired
	BankUserRepository bankUserRepository;
	@Autowired
	BankUserDAO bankUserDao;
//	@Autowired
//	StatementDAO statementDao;
	Random random=new Random();
	
	@Override
	public ResponseEntity<ResponseStructure<BankUserDetails>> validateBankUser(BankUserDetails bankUserDetails)  {
		List<BankUserDetails> allDetails = bankUserDao.getAllDetails();
		if(allDetails.stream().anyMatch((user)->user.getEmailid().equalsIgnoreCase(bankUserDetails.getEmailid()))) {
			throw new EmailExistExceeption("Email already exist try another one");
		}
		if(allDetails.stream().anyMatch(user->user.getAadharnumber()==bankUserDetails.getAadharnumber())) {
			throw new AadharExistException("Aadhar already exist try another one");
		}
		if(allDetails.stream().anyMatch(user->user.getMobilenumber()==bankUserDetails.getMobilenumber())) {
			
			throw new MobileNumberExistException("Mobile already exist try another one");
		}
		if(allDetails.stream().anyMatch(user->user.getPannumber().equals(bankUserDetails.getPannumber()))) {
			throw new PANExistException("PAN already exist try another one");
		}
		bankUserDetails.setStatus("pending");
		BankUserDetails userRegistration = bankUserDao.userRegistration(bankUserDetails);
		
		if(userRegistration.getId()>0) {
			ResponseStructure<BankUserDetails> responseStructure=new ResponseStructure<BankUserDetails>();
			responseStructure.setResponsemsg("Registration Successfull");
			responseStructure.setHttpstatuscode(HttpStatus.CREATED.value());
			responseStructure.setData(userRegistration);
			return new ResponseEntity<ResponseStructure<BankUserDetails>>(responseStructure,HttpStatus.CREATED);
		}
		throw new BankUserException("Server Error");
	
	}
	@Override
	public ResponseEntity<ResponseStructure<List<BankUserDetails>>> allAcceptedDetails() {
		List<BankUserDetails> alluserdetails=bankUserDao.getAllDetails();
		List<BankUserDetails> allaccepteddetails=alluserdetails.stream()
				.filter(user ->user.getStatus().equalsIgnoreCase("Accepted")).collect(Collectors.toList());
		if(allaccepteddetails.size()!=0) {
			ResponseStructure<List<BankUserDetails>> responseStructure= new  
					ResponseStructure<List<BankUserDetails>>();
			responseStructure.setData(allaccepteddetails);
			responseStructure.setResponsemsg("All Accepted Details");
			responseStructure.setHttpstatuscode(HttpStatus.FOUND.value());
			return new ResponseEntity<ResponseStructure<List<BankUserDetails>>>(responseStructure,HttpStatus.FOUND);				
		}
		else {
			ResponseStructure<List<BankUserDetails>> responseStructure=new ResponseStructure<List<BankUserDetails>>();
			responseStructure.setData(allaccepteddetails);
			responseStructure.setResponsemsg("No Accepted Details Found");
			responseStructure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<ResponseStructure<List<BankUserDetails>>>(responseStructure,HttpStatus.NOT_FOUND);
		}
	}
	@Override
	public ResponseEntity<ResponseStructure<List<BankUserDetails>>> allPendingDetails() {
		List<BankUserDetails> alluserdetails=bankUserDao.getAllDetails();
		List<BankUserDetails> allPendingDetails=alluserdetails.stream().filter(user ->user.getStatus().equalsIgnoreCase("Pending")).collect(Collectors.toList());
		if(allPendingDetails.size()!=0) {
			ResponseStructure<List<BankUserDetails>> responseStructure=new ResponseStructure<List<BankUserDetails>>();
			responseStructure.setData(allPendingDetails);
			responseStructure.setResponsemsg("All Pending Details");
			responseStructure.setHttpstatuscode(HttpStatus.FOUND.value());
			return new ResponseEntity<ResponseStructure<List<BankUserDetails>>>(responseStructure,HttpStatus.FOUND);
			
		}
		else {
		ResponseStructure<List<BankUserDetails>> responseStructure=new ResponseStructure<List<BankUserDetails>>();
		responseStructure.setData(allPendingDetails);
		responseStructure.setResponsemsg("No Pending Details Found");
		responseStructure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ResponseStructure<List<BankUserDetails>>>(responseStructure,HttpStatus.NOT_FOUND);
	}
	}
	
	@Override
	public ResponseEntity<ResponseStructure<List<BankUserDetails>>> allClosingDetails() {
		List<BankUserDetails> alluserdetails=bankUserDao.getAllDetails();
		List<BankUserDetails> allClosingDetails=alluserdetails.stream().filter(user ->user.getStatus().equalsIgnoreCase("Closing")).collect(Collectors.toList());
		if(allClosingDetails.size()!=0) {
			ResponseStructure<List<BankUserDetails>> responseStructure=new ResponseStructure<List<BankUserDetails>>();
			responseStructure.setData(allClosingDetails);
			responseStructure.setResponsemsg("All Closing Details");
			responseStructure.setHttpstatuscode(HttpStatus.FOUND.value());
			return new ResponseEntity<ResponseStructure<List<BankUserDetails>>>(responseStructure,HttpStatus.FOUND);
			
		}
		else {
		ResponseStructure<List<BankUserDetails>> responseStructure=new ResponseStructure<List<BankUserDetails>>();
		responseStructure.setData(allClosingDetails);
		responseStructure.setResponsemsg("No Closing Details Found");
		responseStructure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ResponseStructure<List<BankUserDetails>>>(responseStructure,HttpStatus.NOT_FOUND);
	}
	}
	
	
	
	@Override
	public ResponseEntity<ResponseStructure<List<BankUserDetails>>>acceptPendingRequest(int id) {
		boolean astatus =true;
		List<BankUserDetails> bankUserDetails=bankUserDao.getAllDetails();
		BankUserDetails bankuserdetails=bankUserDao.getUserDetailsByUsingId(id);
		List<BankUserDetails> allaccepteddetails=bankUserDetails.stream().filter((user->user.getStatus().equalsIgnoreCase("Accepted"))).collect(Collectors.toList());
		while(astatus) {
			int taccountnumbers=random.nextInt(10000000);
			
			if(taccountnumbers<=999999)
			{
				taccountnumbers+=1000000;
			}
			int accountnumber=taccountnumbers;
			boolean accountmatch=allaccepteddetails.stream().anyMatch((user->user.getAccountnumber()==accountnumber));
			if(accountmatch)
			{
				
			}
			else
			{
				bankuserdetails.setAccountnumber(accountnumber);
				astatus=false;
			}
		}
		boolean pstatus=true;
		while(pstatus)
		{
			int tpin=random.nextInt(10000);
			if(tpin<=999)
			{
				tpin+=1000;
			}
			int pin=tpin;
			boolean pinmatch=allaccepteddetails.stream().anyMatch((user->user.getPin()==pin));
			if(pinmatch)
			{
				
			}
			else
			{
				bankuserdetails.setPin(pin);
				pstatus=false;
			}
		}
		bankuserdetails.setStatus("Accepted");
		BankUserDetails bankDetails=bankUserDao.UpdateUserDetails(bankuserdetails);
		if(bankDetails!=null)
		{
			System.out.println("accepted");
			ResponseStructure<List<BankUserDetails>> responseStructure=new ResponseStructure<List<BankUserDetails>>();
			responseStructure.setData(allaccepteddetails);
			responseStructure.setResponsemsg("Created");
			responseStructure.setHttpstatuscode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<List<BankUserDetails>>>(responseStructure,HttpStatus.OK);
		}
		else
		{
			ResponseStructure<List<BankUserDetails>> responseStructure=new ResponseStructure<List<BankUserDetails>>();
			responseStructure.setData(allaccepteddetails);
			responseStructure.setResponsemsg("Not Created");
			responseStructure.setHttpstatuscode(HttpStatus.BAD_REQUEST.value());
			return new ResponseEntity<ResponseStructure<List<BankUserDetails>>>(responseStructure,HttpStatus.BAD_REQUEST);
		}
	}
	
	@Transactional
	@Override
	public ResponseEntity<com.bank.service.ResponseStructure<String>> deleteUserDetailsByUsingEmailid(String emailid) {
		if(bankUserDao.deleteUserDetailsByUsingEmailid(emailid)>0)
		{
			ResponseStructure<String> responseStructure=new ResponseStructure<String>();
			responseStructure.setResponsemsg("Deleted Successfully");
			responseStructure.setHttpstatuscode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.OK);
		}
		else
		{
			ResponseStructure<String> responseStructure=new ResponseStructure<String>();
			responseStructure.setResponsemsg("Not Deleted");
			responseStructure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.NOT_FOUND);
		}
}
	@Override
	public ResponseEntity<ResponseStructure<String>> userLogin(String emailid, int pin) {
		BankUserDetails bankUserDetails=new BankUserDetails();
		List<BankUserDetails> alldetails=bankUserDao.getAllDetails();
		if(bankUserDao.validateUserDetailsByUsingEmailidAndPassword(emailid, pin))
		{
			ResponseStructure<String> responseStructure=new ResponseStructure<String>();
			responseStructure.setResponsemsg("Login successfully");
			responseStructure.setHttpstatuscode(HttpStatus.ACCEPTED.value());
			return new ResponseEntity<ResponseStructure<String>>(responseStructure ,HttpStatus.ACCEPTED);
		}
		else
		{
			throw new BankUserException("Invalid Credintials");
		}
	}


	
	
	@Override
	public ResponseEntity<ResponseStructure<BankUserDetails>> updateAcceptedDetails(int id, BankUserDetails updatedUserDetails) {
	    Optional<BankUserDetails> details = bankUserDao.updateAcceptedDetailsById(id);
	    if (details.isPresent()) {
	        BankUserDetails user = details.get();
	        user.setName(updatedUserDetails.getName());
	        user.setEmailid(updatedUserDetails.getEmailid());
	        user.setMobilenumber(updatedUserDetails.getMobilenumber());
	        bankUserRepository.save(user);
	        ResponseStructure<BankUserDetails> responseStructure = new ResponseStructure<>();
	        responseStructure.setData(user);
	        responseStructure.setResponsemsg("Updated Successfully");
	        responseStructure.setHttpstatuscode(HttpStatus.OK.value());

	        return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	    }
	    ResponseStructure<BankUserDetails> responseStructure = new ResponseStructure<>();
	    responseStructure.setResponsemsg("User not found");
	    responseStructure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
	    return new ResponseEntity<>(responseStructure, HttpStatus.NOT_FOUND);
	}
	@Override
	public ResponseEntity<ResponseStructure<String>> updatePinByUsingAccountNumber(long accountnumber,int newPin) {
		BankUserDetails userdetails=bankUserDao.updatePinByUsingAccountNumber(accountnumber);
		List<BankUserDetails> details=bankUserDao.getAllDetails();
		List<BankUserDetails> allaccepteddetails=details.stream()
				.filter((user->user.getStatus().equalsIgnoreCase("Accepted")))
				.collect(Collectors.toList());
	      boolean oldPin=allaccepteddetails.stream().anyMatch((user->user.getPin()==newPin));
		if(oldPin) {
			throw new BankUserException("Pin is Already in Use");
		}
		userdetails.setPin(newPin);
		bankUserDao.UpdateUserDetails(userdetails);
		 ResponseStructure<String> responseStructure = new ResponseStructure<>();
		    responseStructure.setResponsemsg("PIN updated successfully");
		    responseStructure.setHttpstatuscode(HttpStatus.OK.value());
		    return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		}
	
	@Override
	public ResponseEntity<ResponseStructure<String>> closeAccountByUsingAccountNumber(long accountnumber) {
	BankUserDetails userDetails=bankUserDao.getUserDetailsByUsingAccountNumber(accountnumber);
		if(userDetails==null) {
			 ResponseStructure<String> responseStructure = new ResponseStructure<>();
	            responseStructure.setResponsemsg("Account not found");
	            responseStructure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
	            return new ResponseEntity<>(responseStructure, HttpStatus.NOT_FOUND);
		}
		    userDetails.setStatus("Closed");
	        BankUserDetails details= bankUserDao.UpdateUserDetails(userDetails);
	        ResponseStructure<String> responseStructure = new ResponseStructure<>();
	        responseStructure.setResponsemsg("successfull");
	        responseStructure.setHttpstatuscode(HttpStatus.OK.value());
	        responseStructure.setData("Status Changed To Closed of account "+userDetails.getAccountnumber());
	        return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	}
		
}