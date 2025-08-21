package com.customerReward.application.controller;

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

/**
 * REST controller for handling customer-related operations.
 *
 * <p>This controller exposes endpoints to:
 * <ul>
 *     <li>Retrieve all customers</li>
 *     <li>Retrieve a customer by ID</li>
 *     <li>Get reward details for a specific customer</li>
 *     <li>Get reward details for all customers</li>
 * </ul>
 * </p>
 *
 * <p>All responses are returned as {@link ResponseEntity} to provide proper HTTP status codes.</p>
 *
 * <p>Base path: <b>/customers</b></p>
 * 
 * @author Preetheeshnandha
 */
@RestController
@RequestMapping("customers")
public class CustomerController {

	private CustomerServiceImpl customerService;

	/**
     * Constructor for injecting {@link CustomerServiceImpl}.
     *
     * @param customerService the service handling business logic for customers
     */
	public CustomerController(CustomerServiceImpl customerService) {
		this.customerService = customerService;
	}

	/**
     * GET /customers
     *
     * @return a list of all customers
     */
	@GetMapping
	public ResponseEntity<List<Customer>> getAllCustomer() {
		return ResponseEntity.ok(customerService.getAllCustomer());
	}

	/**
     * GET /customers/{customerId}
     *
     * @param customerId the ID of the customer to retrieve
     * @return the customer with the given ID, or 404 if not found
     */
	@GetMapping("/{customerId}")
	public ResponseEntity<Customer> getByCustomerId(@PathVariable Long customerId) {
		return customerService.getByCustomerId(customerId).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	/**
     * GET /customers/reward/{customerId}?lastNMonths=3
     *
     * @param customerId the ID of the customer
     * @param lastNMonths number of months to calculate rewards for (defaults to 3 if not provided)
     * @return reward details for the specified customer over the given time range
     */
	@GetMapping("/reward/{customerId}")
	public ResponseEntity<RewardDetailsDTO> getCustomerRewardDetails(@PathVariable Long customerId,
			@RequestParam(required = false, defaultValue = "3") int lastNMonths) {
		return ResponseEntity.ok(customerService.getCustomerRewardDetails(customerId,lastNMonths));
	}

	/**
     * GET /customers/reward?lastNMonths=3
     *
     * @param lastNMonths number of months to calculate rewards for all customers (defaults to 3 if not provided)
     * @return list of reward details for all customers over the given time range
     */
	@GetMapping("/reward")
	public ResponseEntity<List<RewardDetailsDTO>> getAllCustomerRewardDetails(
			@RequestParam(required = false, defaultValue = "3") int lastNMonths) {
		return ResponseEntity.ok(customerService.getAllCustomerRewardDetails(lastNMonths));
	}

}
