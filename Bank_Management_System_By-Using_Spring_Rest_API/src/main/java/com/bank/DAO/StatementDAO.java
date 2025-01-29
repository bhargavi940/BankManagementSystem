package com.bank.DAO;

import java.util.List;

import com.bank.entity.Statement;

public interface StatementDAO {

//	public List<Statement> getStatementDetailsByAccountnumber(long accountnumber);
	Statement updateStatement(Statement statement);
	public List<Statement> getStatementDetails();
	
	

}
