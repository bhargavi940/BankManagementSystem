package com.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bank.DAO.AdminDAO;
import com.bank.entity.Admin;
import com.bank.service.AdminService;
import com.bank.service.ResponseStructure;
@Controller
@CrossOrigin("*")
public class AdminController {
//	@Autowired
//	AdminDAO adminDAO;
	@Autowired
	AdminService adminService;
	@GetMapping("/adminlogin/{emailid}/{pin}")
	public ResponseEntity<ResponseStructure<String>> adminLogin(@PathVariable("emailid") String emailid,@PathVariable("pin") String pin)
	{
		return adminService.adminLogin(emailid, pin);
	}
}