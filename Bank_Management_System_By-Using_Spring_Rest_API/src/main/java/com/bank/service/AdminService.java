package com.bank.service;

import org.springframework.http.ResponseEntity;

import com.bank.entity.Admin;
import com.bank.entity.BankUserDetails;

public interface AdminService {
	ResponseEntity<ResponseStructure<String>> adminLogin(String emailid,String pin);
}
