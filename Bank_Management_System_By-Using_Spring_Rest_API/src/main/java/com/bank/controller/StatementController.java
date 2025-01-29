package com.bank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bank.entity.BankUserDetails;
import com.bank.entity.GlobalValues;
import com.bank.entity.Statement;
import com.bank.service.ResponseStructure;
import com.bank.service.StatementService;
@Controller
@CrossOrigin("*")
public class StatementController {
	@Autowired
	StatementService service;
	
	
	 
}
