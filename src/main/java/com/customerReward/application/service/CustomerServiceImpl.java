package com.customerReward.application.service;

import com.customerReward.application.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

import com.customerReward.application.dto.RewardDetailsDTO;
import com.customerReward.application.entity.Customer;
import com.customerReward.application.entity.Transaction;
import com.customerReward.application.exception.InvalidRequestException;
import com.customerReward.application.exception.ResourceNotFoundException;

@Service
public class CustomerServiceImpl implements CustomerService {

	private CustomerRepository customerRepo;

	public CustomerServiceImpl(CustomerRepository customerRepo) {
		this.customerRepo = customerRepo;
	}

	public List<Customer> getAllCustomer() {
		
		if(customerRepo.findAll() == null) {
			throw new ResourceNotFoundException("Customer details not found");
		}
		return customerRepo.findAll();
	}

	public Optional<Customer> getByCustomerId(Long customerId) {
		
		if (customerId == null || customerId <= 0) {
	        throw new InvalidRequestException("customerId must be greater than 0");
	    }
		if(customerRepo.findById(customerId).isEmpty()) {
			throw new ResourceNotFoundException("Customer details not found");
		}
		
		return customerRepo.findById(customerId);
	}

	public RewardDetailsDTO getCustomerRewardDetails(Long customerId, int lastNMonths) {
		
		if(lastNMonths<=0) {
			throw new InvalidRequestException("lastNMonths must be greater than 0");
		}
				
		Optional<Customer> customerObject = getByCustomerId(customerId);
		if (customerObject.isPresent()) {
			List<Customer> customer = Collections.singletonList(customerObject.get());
			List<RewardDetailsDTO> customerRewardDetails = new ArrayList<>();
			populateCustomerRewardDetails(lastNMonths, customer, customerRewardDetails);
			return customerRewardDetails.get(0);
		}
		return null;
	}

	public List<RewardDetailsDTO> getAllCustomerRewardDetails(int lastNMonths) {

		List<Customer> allCustomer = getAllCustomer();
		List<RewardDetailsDTO> customerRewardDetails = new ArrayList<>();

		populateCustomerRewardDetails(lastNMonths, allCustomer, customerRewardDetails);

		return customerRewardDetails;
	}

	private void populateCustomerRewardDetails(int lastNMonths, List<Customer> allCustomer,
			List<RewardDetailsDTO> customerRewardDetails) {

		List<String> lastNMonthList = getLastNMonths(lastNMonths);
		allCustomer.forEach(customerDetails -> {

			Map<String, Long> monthlyTransactionAmount = new HashMap<>();
			Map<String, Long> monthlyRewards = new HashMap<>();

			long totalRewardPointsPerCustomer = 0;

			if (customerDetails.getTransactions() != null) {
				for (Transaction transaction : customerDetails.getTransactions()) {

					String month = transaction.getTransactionDate().getMonth().toString();
					int year = transaction.getTransactionDate().getYear();
					if (!lastNMonthList.contains(month+"-"+year))
						continue;

					Long transactionAmount = transaction.getTransactionAmount();

					Long rewardPoints = calculateRewardPoints(transactionAmount);

					monthlyTransactionAmount.merge(month, transactionAmount, (a, b) -> a + b);
					monthlyRewards.merge(month, rewardPoints, (a, b) -> a + b);
					totalRewardPointsPerCustomer += rewardPoints;

				}
			}

			RewardDetailsDTO rewardDetailsDTO = RewardDetailsDTO.builder().customerId(customerDetails.getCustomerId())
					.customerName(customerDetails.getCustomerName()).customerEmail(customerDetails.getCustomerEmail())
					.monthlyTransactionAmount(monthlyTransactionAmount).monthlyRewards(monthlyRewards)
					.totalRewardPoints(totalRewardPointsPerCustomer).build();

			customerRewardDetails.add(rewardDetailsDTO);
		});
	}

	public List<String> getLastNMonths(int n) {
		List<String> monthNames = new ArrayList<>();
		LocalDate currentMonth = LocalDate.now();
		
		for (int i = 1; i <= n; i++) {
			String month = currentMonth.minusMonths(i).getMonth().toString();
			int year = currentMonth.minusMonths(i).getYear();
			monthNames.add(month+"-"+year);
		}

		return monthNames;
	}

	public long calculateRewardPoints(long transactionAmount) {
		if (transactionAmount > 100) {
			return (transactionAmount - 100) * 2 + 50;
		} else if (transactionAmount > 50) {
			return transactionAmount - 50;
		} else {
			return (long)0;
		}

	}

}