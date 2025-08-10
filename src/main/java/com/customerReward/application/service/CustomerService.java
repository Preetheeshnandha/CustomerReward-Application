package com.customerReward.application.service;

import java.util.List;
import java.util.Optional;

import com.customerReward.application.dto.RewardDetailsDTO;
import com.customerReward.application.entity.Customer;

public interface CustomerService {
	
	List<Customer> getAllCustomer();
	Optional<Customer> getByCustomerId(Long customerId);
	RewardDetailsDTO getCustomerRewardDetails(Long customerId,int lastNMonths);
	List<RewardDetailsDTO> getAllCustomerRewardDetails(int LastNMonths);
	List<String> getLastNMonths(int n);
	
}
