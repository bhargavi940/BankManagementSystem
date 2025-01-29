package com.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.entity.Statement;

public interface StatementRepository extends JpaRepository<Statement, Integer>{
		List<Statement> findByAccountnumber(long accountnumber);
		
}
