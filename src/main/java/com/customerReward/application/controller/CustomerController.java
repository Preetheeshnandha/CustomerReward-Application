package com.customerReward.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.customerReward.application.service.CustomerServiceImpl;
import java.util.*;

import com.customerReward.application.dto.RewardDetailsDTO;
import com.customerReward.application.entity.Customer;

@RestController
@RequestMapping("customers")
public class CustomerController {

	private CustomerServiceImpl customerService;

	public CustomerController(CustomerServiceImpl customerService) {
		this.customerService = customerService;
	}

	@GetMapping
	public ResponseEntity<List<Customer>> getAllCustomer() {
		return ResponseEntity.ok(customerService.getAllCustomer());
	}

	@GetMapping("/{customerId}")
	public ResponseEntity<Customer> getByCustomerId(@PathVariable Long customerId) {
		return customerService.getByCustomerId(customerId).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/reward/{customerId}")
	public ResponseEntity<RewardDetailsDTO> getCustomerRewardDetails(@PathVariable Long customerId,
			@RequestParam(required = false, defaultValue = "3") int lastNMonths) {
		return ResponseEntity.ok(customerService.getCustomerRewardDetails(customerId,lastNMonths));
	}

	@GetMapping("/reward")
	public ResponseEntity<List<RewardDetailsDTO>> getAllCustomerRewardDetails(
			@RequestParam(required = false, defaultValue = "3") int lastNMonths) {
		return ResponseEntity.ok(customerService.getAllCustomerRewardDetails(lastNMonths));
	}

}
