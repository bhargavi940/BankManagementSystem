package com.bank.service;

import java.util.List;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.bank.DAO.AdminDAO;
import com.bank.entity.Admin;
import com.bank.entity.BankUserDetails;
import com.bank.exceptions.AdminLoginException;
import com.bank.exceptions.EmailExistExceeption;

@Component
public class AdminServiceImpli implements AdminService {

	@Autowired
	AdminDAO admindao;
	
	@Override
	public ResponseEntity<ResponseStructure<String>> adminLogin(String emailid,String pin)
{
		if(admindao.validateAdminDetailsByUsingEmailidAndPassword(emailid,pin)) {
		ResponseStructure<String> responseStructure=new ResponseStructure<String>();
		responseStructure.setResponsemsg("Admin Login Successfull");
		responseStructure.setHttpstatuscode(HttpStatus.ACCEPTED.value());
		return new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.ACCEPTED);
		}
		else {
		throw new AdminLoginException("Invalid Login Credentials");
		}
		
	}
	

}
