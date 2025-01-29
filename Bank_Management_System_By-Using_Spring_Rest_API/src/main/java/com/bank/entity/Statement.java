package com.bank.entity;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Statement {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String transactiontype;
	private double transactionamount;
	private Date transactiondate;
	private Time transactiontime;
	private long accountnumber;
	private double balanceamount;

}
