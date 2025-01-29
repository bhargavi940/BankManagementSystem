package com.bank.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bank.entity.Statement;

public interface StatementService {
	ResponseEntity<ResponseStructure<String>> creditAmount(long accountnumber, double amount);

	ResponseEntity<ResponseStructure<String>> debitAmount(long accountnumber, double amount);

	ResponseEntity<ResponseStructure<Double>> balanceAmount(long accountNumber);
	
	ResponseEntity<ResponseStructure<List<Statement>>> getStatementDetails(int pin);

	ResponseEntity<ResponseStructure<String>> mobileToMobileTransaction(long sender, long receiver, double amount);
	}

