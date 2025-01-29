package com.bank.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bank.entity.BankUserDetails;
import com.bank.entity.Statement;
import com.bank.repository.BankUserRepository;
import com.bank.repository.StatementRepository;
@Component
public class StatementDAOImpli implements StatementDAO {
	 @Autowired
	 StatementRepository statementRepository;
	 @Autowired
	 BankUserRepository repostory;
	@Override
	public List<Statement> getStatementDetails() {
		return statementRepository.findAll();
	}
	@Override
	public Statement updateStatement(Statement statement) {
		return statementRepository.save(statement);
		
	}
	
	
	 
	 
	
}
