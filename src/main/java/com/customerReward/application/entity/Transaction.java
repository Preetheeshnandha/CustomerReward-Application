package com.customerReward.application.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name="transactions")
@Getter
@Setter
@AllArgsConstructor	
@NoArgsConstructor
public class Transaction {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="transaction_id")
	@NotNull
	private Long transactionId;
	
	@NotNull
	@Column(name="transaction_date")
	private LocalDate transactionDate;
	
	@NotNull
	@Column(name="transaction_amount")
	private long transactionAmount;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="customer_id", nullable=false)
	@JsonBackReference
	@NotNull
	private Customer customer;
	
}
