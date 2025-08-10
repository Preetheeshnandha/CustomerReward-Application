package com.customerReward.application.entity;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="customers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="customer_id")
	@NotNull
	private long customerId;
	
	@Column(name="customer_name")
	@NotBlank(message="Customer name is required")
	private String customerName;
	
	@Column(name="customer_email")
	@Email(message="Invalid Email format")
	@NotBlank(message="Customer email is required")
	private String customerEmail;
	
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<Transaction> transactions = new ArrayList<>();
	
}
