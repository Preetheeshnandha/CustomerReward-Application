package com.customerReward.application.service;

import com.customerReward.application.repository.CustomerRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import com.customerReward.application.dto.RewardDetailsDTO;
import com.customerReward.application.entity.Customer;
import com.customerReward.application.entity.Transaction; 

@Service
public class CustomerServiceImpl implements CustomerService{

	
	
	private CustomerRepository customerRepo;
	
	
	public CustomerServiceImpl(CustomerRepository customerRepo) {
		this.customerRepo = customerRepo;
	}
	
	public List<Customer> getAllCustomer(){
		return customerRepo.findAll();
	}
	
	public Optional<Customer> getByCustomerId(Long customerId){
		return customerRepo.findById(customerId);
	}
	
	
	public List<RewardDetailsDTO> getCustomerRewardDetails(int LastNMonths){
		
		List<String> lastNMonths = getLastNMonths(LastNMonths);
		
		Map<Long,List<Customer>> groupingCustomers = customerRepo.findAll()
				.stream()
				.collect(Collectors.groupingBy(Customer :: getCustomerId));
		
		
		List<RewardDetailsDTO> customerRewardDetails = new ArrayList<>();
		
		groupingCustomers.forEach((groupCustomerId, customerList) -> {
			
					
			customerList.forEach(customerDetails -> {
				
				Map<String,Integer> monthlyTransactionAmount = new HashMap<>();
				Map<String,Integer> monthlyRewards = new HashMap<>();
				
				
				int totalRewardPointsPerCustomer=0;
				
				if(customerDetails.getTransactions() != null) {
					for(Transaction transaction:customerDetails.getTransactions()) {
						
						String month = transaction.getTransactionDate().getMonth().toString();
						
						if(!lastNMonths.contains(month)) continue;
						
						int transactionAmount = (int)transaction.getTransactionAmount();
						
					
						int rewardPoints = calculateRewardPoints(transactionAmount);
						
						
						
						monthlyTransactionAmount.merge(month, transactionAmount, (a,b) -> a+b);
						monthlyRewards.merge(month,rewardPoints,(a,b) -> a+b);
						totalRewardPointsPerCustomer+=rewardPoints;
						
				}		
			}
				
				RewardDetailsDTO rewardDetailsDTO = RewardDetailsDTO.builder()
						.customerId(customerDetails.getCustomerId())
						.customerName(customerDetails.getCustomerName())
						.customerEmail(customerDetails.getCustomerEmail())
						.monthlyTransactionAmount(monthlyTransactionAmount)
						.monthlyRewards(monthlyRewards)
						.totalRewardPoints(totalRewardPointsPerCustomer)
						.build();
				
				
				customerRewardDetails.add(rewardDetailsDTO);
		});		
		});	
		
		 
		return customerRewardDetails;
	}
	
	
	public List<String> getLastNMonths(int n){
		 List<String> monthNames = new ArrayList<>();
		 LocalDate currentMonth = LocalDate.now();
		 
		 for(int i=1; i<=n; i++) {
			 String month = currentMonth.minusMonths(i).getMonth().toString();
			 monthNames.add(month);
		 }
		 
		 return monthNames;
	}
	
	public int calculateRewardPoints(int transactionAmount) {
		if(transactionAmount>100){
			return (transactionAmount-100)*2+50;
		}else if(transactionAmount>50) {
			return transactionAmount-50;
		}else {
			return 0;
		}

	}
	
	

				
}			