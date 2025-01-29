package com.bank.repository;

import java.util.Optional;



import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.entity.BankUserDetails;

public interface BankUserRepository extends JpaRepository<BankUserDetails, Integer>{

	BankUserDetails findById(int id);
	int deleteByEmailid(String emailid);
	
	
	
	
	 boolean existsByEmailidAndPin(String emailid,int pin);
	
	BankUserDetails findByAccountnumber(long accountNumber);
	Optional<BankUserDetails> searchById(int id);
	BankUserDetails  findByPin(int pin);
	BankUserDetails findByMobilenumber(long mobilenumber);
}
