package com.bank.service;

import com.bank.entity.BankUserDetails;
import com.bank.entity.Statement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseStructure<E> {
	private String responsemsg;
	private int httpstatuscode;
	private E data;
	

}
