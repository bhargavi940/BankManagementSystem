package com.bank.exceptions;

import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bank.service.ResponseStructure;
//https status code, data , response message,header
@ControllerAdvice
public class BankUserExcpetionHandler {

	@ExceptionHandler(AadharExistException.class)
	@ResponseBody
	public ResponseEntity<ResponseStructure<String>> aadharExceptionHandler(AadharExistException exception) {
		
		ResponseStructure<String> responseStructure=new ResponseStructure<String>();
		responseStructure.setResponsemsg(exception.getMessage());
		responseStructure.setHttpstatuscode(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.BAD_REQUEST);
	   //return exception.getMessage();
	}
	
	@ExceptionHandler(EmailExistExceeption.class)
	@ResponseBody
	public ResponseEntity<ResponseStructure<String>> emailExceptionHandler(EmailExistExceeption exception) {
		
		ResponseStructure<String> responseStructure=new ResponseStructure<String>();
		responseStructure.setResponsemsg(exception.getMessage());
		responseStructure.setHttpstatuscode(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(MobileNumberExistException.class)
	@ResponseBody
	public ResponseEntity<ResponseStructure<String>> mobileExceptionHandler(MobileNumberExistException exception) {
		ResponseStructure<String> responseStructure=new ResponseStructure<String>();
		responseStructure.setResponsemsg(exception.getMessage());
		responseStructure.setHttpstatuscode(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(PANExistException.class)
	@ResponseBody
	public ResponseEntity<ResponseStructure<String>> panExceptionHandler(PANExistException exception) {
		ResponseStructure<String> responseStructure=new ResponseStructure<String>();
		responseStructure.setResponsemsg(exception.getMessage());
		responseStructure.setHttpstatuscode(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AdminLoginException.class)
	@ResponseBody
	public ResponseEntity<ResponseStructure<String>> AdminLoginExceptionHandler(AdminLoginException exception) {
		ResponseStructure<String> responseStructure=new ResponseStructure<String>();
		responseStructure.setResponsemsg(exception.getMessage());
		responseStructure.setHttpstatuscode(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.BAD_REQUEST);
	}
	
	/*public String sqlExceptionHandler(SQLException ex) {
		
		return ex.getMes
	}*/
}