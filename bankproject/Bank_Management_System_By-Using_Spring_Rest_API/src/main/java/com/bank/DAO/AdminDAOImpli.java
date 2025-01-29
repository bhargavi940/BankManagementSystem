package com.bank.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bank.entity.Admin;
import com.bank.repository.AdminRepository;

@Component
public class AdminDAOImpli implements AdminDAO{
	

		@Autowired
		AdminRepository adminRepository;
		@Override
		public Boolean validateAdminDetailsByUsingEmailidAndPassword(String emailid,String pin) {
			return adminRepository.existsByEmailidAndPin(emailid, pin);
		}
//		@Override
//		public List<Admin> getalladmindetails() {
//			return adminRepository.findAll();
//		}

	
}
