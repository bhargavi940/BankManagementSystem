package com.bank.DAO;

import com.bank.entity.Admin;

public interface AdminDAO  {


	Boolean validateAdminDetailsByUsingEmailidAndPassword(String emailid,String pin);
}
