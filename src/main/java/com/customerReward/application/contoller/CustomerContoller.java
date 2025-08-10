package com.customerReward.application.contoller;

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
public class CustomerContoller {

	
	
	
	private CustomerServiceImpl customerService;
	
	
	
	public CustomerContoller(CustomerServiceImpl customerService) {
		this.customerService=customerService;
	}
	
	
	@GetMapping
	public ResponseEntity<List<Customer>> getAllCustomer(){
		return ResponseEntity.ok(customerService.getAllCustomer());
	}
	
	@GetMapping("/{customerId}")
	public ResponseEntity<Customer> getByCustomerId(@PathVariable Long customerId){
		return customerService.getByCustomerId(customerId)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	
	@GetMapping("/reward")
	public  ResponseEntity<List<RewardDetailsDTO>> getCustomerRewardDetails(@RequestParam(required=false, defaultValue="3") int lastNMonths){
		return ResponseEntity.ok(customerService.getCustomerRewardDetails(lastNMonths));
	}
	
}
