package com.customerReward.application.service;

import com.customerReward.application.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.customerReward.application.dto.RewardDetailsDTO;
import com.customerReward.application.entity.Customer;
import com.customerReward.application.entity.Transaction;
import com.customerReward.application.exception.InvalidRequestException;
import com.customerReward.application.exception.ResourceNotFoundException;

/**
 * Service class for handling Customer Rewards.
 *
 *
 * <p>
 * This class provides methods to calculate reward points for customers based on
 * their transaction history.
 * </p>
 * 
 * @author Preetheeshnandha
 */
@Service
public class CustomerServiceImpl implements CustomerService {

	private CustomerRepository customerRepo;

	/**
	 * Constructor for CustomerServiceImpl.
	 *
	 * @param customerRepo repository for accessing customer data
	 */
	public CustomerServiceImpl(CustomerRepository customerRepo) {
		this.customerRepo = customerRepo;
	}

	/**
	 * Fetches all customer details.
	 *
	 * @return list of all customers
	 * @throws ResourceNotFoundException if no customers are found
	 */
	public List<Customer> getAllCustomer() {
		List<Customer> customer = customerRepo.findAll();
		if (customer == null || customer.isEmpty()) {
			throw new ResourceNotFoundException("Customer details not found");
		}
		return customer;
	}

	/**
	 * Fetches a customer by ID.
	 *
	 * @param customerId the unique customer ID
	 * @return Optional containing the customer if found
	 * @throws InvalidRequestException   if the ID is null or less than 1
	 * @throws ResourceNotFoundException if no customer is found with the given ID
	 */
	public Optional<Customer> getByCustomerId(Long customerId) {

		if (customerId == null || customerId <= 0) {
			throw new InvalidRequestException("Customer Id must be greater than 0");
		}

		return customerRepo.findById(customerId).or(() -> {
			throw new ResourceNotFoundException("Customer  ID: " + customerId + " not found");
		});
	}

	/**
	 * Fetches reward details for a specific customer.
	 *
	 * @param customerId  the customer ID
	 * @param lastNMonths number of recent months to consider
	 * @return reward details for the customer
	 */
	public RewardDetailsDTO getCustomerRewardDetails(Long customerId, int lastNMonths) {
		Customer customer = getByCustomerId(customerId).get();
		return populateCustomerRewardDetails(lastNMonths, customer);
	}

	/**
	 * Fetches reward details for all customers.
	 *
	 * @param lastNMonths number of recent months to consider
	 * @return list of reward details for all customers
	 */
	public List<RewardDetailsDTO> getAllCustomerRewardDetails(int lastNMonths) {
		return getAllCustomer().stream().map(customer -> populateCustomerRewardDetails(lastNMonths, customer)).toList();
	}

	/**
	 * Builds reward details for a given customer.
	 *
	 * @param lastNMonths number of recent months to consider
	 * @param customer    the customer object
	 * @return reward details DTO containing Customer details,monthly amount,
	 *         monthly reward and total rewards
	 * @throws InvalidRequestException if lastNMonths is less than or equal to 0
	 */
	private RewardDetailsDTO populateCustomerRewardDetails(int lastNMonths, Customer customer) {

		if (lastNMonths <= 0) {
			throw new InvalidRequestException("lastNMonths must be greater than 0");
		}

		List<String> lastNMonthList = getLastNMonths(lastNMonths);

		// Filter the valid transactions
		List<Transaction> validTransactions = Optional.ofNullable(customer.getTransactions())
				.orElse(Collections.emptyList()).stream()
				.filter(transaction -> lastNMonthList.contains(getMonthYear(transaction.getTransactionDate())))
				.toList();

		// Monthly transaction Amount
		Map<String, Long> monthlyTransactionAmount = validTransactions.stream()
				.collect(Collectors.groupingBy(transaction -> getMonthYear(transaction.getTransactionDate()),
						Collectors.summingLong(Transaction::getTransactionAmount)));

		// Monthly Rewards
		Map<String, Long> monthlyRewards = validTransactions.stream().collect(Collectors.groupingBy(
				transaction -> getMonthYear(transaction.getTransactionDate()),
				Collectors.summingLong(transaction -> calculateRewardPoints(transaction.getTransactionAmount()))));

		// Total Reward Points Customer
		long totalRewardPointsPerCustomer = validTransactions.stream()
				.mapToLong(transaction -> calculateRewardPoints(transaction.getTransactionAmount())).sum();

		// Reward DTO
		return RewardDetailsDTO.builder().customerId(customer.getCustomerId()).customerName(customer.getCustomerName())
				.customerEmail(customer.getCustomerEmail()).monthlyTransactionAmount(monthlyTransactionAmount)
				.monthlyRewards(monthlyRewards).totalRewardPoints(totalRewardPointsPerCustomer).build();
	}

	/**
	 * Generates a list of last N months in format MONTH-YEAR.
	 *
	 * @param n number of months
	 * @return list of month-year strings
	 */
	public List<String> getLastNMonths(int n) {

		return IntStream.rangeClosed(1, n).mapToObj(i -> {
			LocalDate date = LocalDate.now().minusMonths(i);
			return getMonthYear(date);
		}).toList();
	}

	/**
	 * Converts a LocalDate into a Month-Year format string.
	 *
	 * @param date the date to format
	 * @return formatted string (e.g., "JANUARY-2025")
	 */
	public String getMonthYear(LocalDate date) {
		return date.getMonth().toString() + "-" + date.getYear();
	}

	/**
	 * Calculates reward points for a given transaction amount.
	 *
	 * @param transactionAmount the amount spent in the transaction
	 * @return the reward points earned
	 */
	public long calculateRewardPoints(long transactionAmount) {
		if (transactionAmount > 100) {
			return (transactionAmount - 100) * 2 + 50;
		} else if (transactionAmount > 50) {
			return transactionAmount - 50;
		} else {
			return (long) 0;
		}

	}
}