package com.bank.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bank.DAO.BankUserDAO;
import com.bank.entity.BankUserDetails;
import com.bank.entity.GlobalValues;
import com.bank.entity.Statement;
import com.bank.service.ResponseStructure;
import com.bank.service.StatementService;
import com.bank.service.UserService;

@Controller
@CrossOrigin("*")
public class BankUserController {
	 @Autowired
	BankUserDAO bankuserdao;
	@Autowired
	UserService service;
	@Autowired
	StatementService service2;
	@Autowired
	GlobalValues values;
	
	
	
	/* it is used to specify the insertion operation
	 * it is used to create the endpoint or URL pattern or API
	 * It is used to map the endpoint with the method 
	 * localhost:1919/userregistration*/
	/* @PostMapping("/userregistration")
	@ResponseBody
	public BankUserDetails userRegistration(@RequestBody BankUserDetails bankuserdetails) {
		
		BankUserDetails userdetails= bankuserdao.userRegistration(bankuserdetails);
	    return userdetails;

	}*/
	
	
//	@GetMapping("/userogin/{emailid}/{pin}")
//	public ResponseEntity<List<ResponseStructure<String>>> adminLogin(@PathVariable("emailid") String emailid,@PathVariable("pin") String pin)
//	{
//		return service.userLogin(emailid, pin);
//	}
	
	
	@PostMapping("/userregistration")
	@ResponseBody
	public ResponseEntity<ResponseStructure<BankUserDetails>> userRegistration(@RequestBody BankUserDetails bankuserdetails) {
		
		return service.validateBankUser(bankuserdetails);
		
	}
	@GetMapping("/allaccepteddetails")
	public ResponseEntity<ResponseStructure<List<BankUserDetails>>> getAllAcceptedDetails() {
		return service.allAcceptedDetails();
	}
	
	@GetMapping("/allpendingdetails")
	public ResponseEntity<ResponseStructure<List<BankUserDetails>>> getAllPendingDetails() {
		return service.allPendingDetails();


	}
	@GetMapping("/allclosingdetails")
	public ResponseEntity<ResponseStructure<List<BankUserDetails>>> getAllClosingDetails() {
		return service.allClosingDetails();
	}
	
	
	@PutMapping("/acceptpendingusingbyid/{userid}")
	public ResponseEntity<ResponseStructure<List<BankUserDetails>>> acceptPendingRequestByUsingId(@PathVariable("userid") int userid) {
		return service.acceptPendingRequest(userid);
	}
	
	@DeleteMapping("/deleteuserdetails/{emailid}")
	@ResponseBody
	public ResponseEntity<ResponseStructure<String>> rejectPendingDetails(@PathVariable("emailid") String emailid)
	{
		return service.deleteUserDetailsByUsingEmailid(emailid);
	}
	
	@GetMapping("/userlogin/{emailid}/{pin}")
	public ResponseEntity<ResponseStructure<String>> userLogin(@PathVariable("emailid") String emailid,@PathVariable("pin") int pin)
	{
		values.setPin(pin);
		return service.userLogin(emailid, pin);
	}
//	@PostMapping("/creditamount/{accountnumber}/{amount}")
//	public ResponseEntity<ResponseStructure<String>> updateAmountByUsingAccountNumber(
//			@PathVariable("accountnumber") long accountnumber,
//			@PathVariable("amount") double amount,
//			@RequestParam boolean credit)
//	{
//		return service.creditAmount(accountnumber, amount, credit);
//	}
//	
//	@PostMapping("/debitamount/{accountnumber}/{amount}")
//	public ResponseEntity<ResponseStructure<String>> debitAmount(
//			@PathVariable("accountnumber") long accountnumber,
//			@PathVariable("amount") double amount,
//		
//	@RequestParam boolean debit) {
//	    return service.debitAmount(accountnumber, amount, debit);
//	}
	
	 @PutMapping("/updateaccepteddetails/{id}")
	 public ResponseEntity<ResponseStructure<BankUserDetails>> updateAcceptedUser(@PathVariable int id,
			 @RequestBody BankUserDetails updatedUserDetails){
		 return service.updateAcceptedDetails(id, updatedUserDetails);
	 }
	 
	 @GetMapping("/getstatementdetails")
		public ResponseEntity<ResponseStructure<List<Statement>>> statementDetails() {
		 int pin = values.getPin();
			return service2.getStatementDetails(pin);
		}
	 
	 @PostMapping("/creditstatement/{accountnumber}/{amount}")
		public  ResponseEntity<ResponseStructure<String>> creditAmount(@PathVariable("accountnumber")long accountnumber, @PathVariable("amount") double amount) {
			int pin = values.getPin();
			BankUserDetails detailsByPin = bankuserdao.getDetailsByPin(pin);
			if(detailsByPin.getAccountnumber()!=accountnumber) {
				ResponseStructure<String> structure=new ResponseStructure<String>();
				structure.setResponsemsg("Invalid AccountNumber");
				structure.setHttpstatuscode(HttpStatus.BAD_REQUEST.value());
				return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.BAD_REQUEST);			
				
			}
		 return service2.creditAmount(accountnumber, amount);
		}
		
		@PostMapping("/debitstatement/{accountnumber}/{amount}")
		public  ResponseEntity<ResponseStructure<String>> debitAmount(@PathVariable("accountnumber")long accountnumber, @PathVariable("amount") double amount) {
			int pin = values.getPin();
			BankUserDetails detailsByPin = bankuserdao.getDetailsByPin(pin);
			if(detailsByPin.getAccountnumber()!=accountnumber) {
				ResponseStructure<String> structure=new ResponseStructure<String>();
				structure.setResponsemsg("Invalid AccountNumber");
				structure.setHttpstatuscode(HttpStatus.BAD_REQUEST.value());
				return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.BAD_REQUEST);			
			}
			return service2.debitAmount(accountnumber, amount);
		}
		
		 @GetMapping("/checkbalance/{accountnumber}")
		    public ResponseEntity<ResponseStructure<Double>> getBalance(
		            @PathVariable("accountnumber") int accountNumber) {
				int pin = values.getPin();
			 BankUserDetails detailsByPin = bankuserdao.getDetailsByPin(pin);
				if(detailsByPin.getAccountnumber()!=accountNumber) {
					ResponseStructure<String> structure=new ResponseStructure<String>();
					structure.setResponsemsg("Invalid AccountNumber");
					structure.setHttpstatuscode(HttpStatus.BAD_REQUEST.value());
					return new ResponseEntity<ResponseStructure<Double>>(HttpStatus.BAD_REQUEST);			
				}
		        return service2.balanceAmount(accountNumber);
		    }
		 @PostMapping("/changepin/{accountnumber}/{newpin}")
		 public ResponseEntity<ResponseStructure<String>> changePin(@PathVariable("accountnumber")long accountnumber,@PathVariable("newpin")int pin) {
			return service.updatePinByUsingAccountNumber(accountnumber, pin);
		 }
		 @PostMapping("/mobiletomobile/{sender}/{receiver}/{amount}")
		 public ResponseEntity<ResponseStructure<String>> mobileToMobileTransaction(@PathVariable("sender")
		 long senderMobile,@PathVariable("receiver")
		 long receiverMobile,@PathVariable("amount")double amount) {
			return service2.mobileToMobileTransaction(senderMobile, receiverMobile, amount);
		 }
		 
		 @PutMapping("closeaccount/{accountnumber}")
		 public ResponseEntity<ResponseStructure<String>> closingAccount(@PathVariable("accountnumber") long accountnumber) {
			return service.closeAccountByUsingAccountNumber(accountnumber); 
		 }
}
