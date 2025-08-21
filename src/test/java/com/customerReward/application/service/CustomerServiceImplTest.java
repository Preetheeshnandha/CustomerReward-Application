package com.customerReward.application.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.customerReward.application.repository.CustomerRepository;
import com.customerReward.application.entity.Customer;
import com.customerReward.application.exception.ResourceNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.*;

/**
 * Unit tests for {@link CustomerServiceImpl}.
 *
 * <p>
 * This class uses JUnit 5 and Mockito to test the functionality of
 * {@code CustomerServiceImpl}, including customer retrieval and reward point
 * calculation logic.
 * </p>
 *
 * @author Preetheeshnandha
 */
@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

	@Mock
	private CustomerRepository customerRepo;

	@InjectMocks
	private CustomerServiceImpl customerService;

	/**
	 * Test case for retrieving all customers. Verifies that the service returns all
	 * customer records correctly.
	 */
	@Test
	void testGetAllCustomer() {

		List<Customer> mockAllCustomers = List.of(new Customer(1, "Kumar", "kumar@gmail.com", null),
				new Customer(2, "Sankar", "sankar@gmail.com", null));
		when(customerRepo.findAll()).thenReturn(mockAllCustomers);
		List<Customer> customer = customerService.getAllCustomer();
		assertNotNull(customer);
		assertEquals(2, customer.size());
		assertEquals("Kumar", customer.get(0).getCustomerName());
		verify(customerRepo, times(1)).findAll();
	}

	/**
	 * Test case for retrieving a customer by ID when found. Ensures the correct
	 * customer is returned.
	 */
	@Test
	void testGetByCustomerId_Found() {
		Customer customer = new Customer(2, "Sankar", "sankar@gmail.com", null);
		when(customerRepo.findById((long) 2)).thenReturn(Optional.of(customer));
		Optional<Customer> result = customerService.getByCustomerId((long) 2);
		assertTrue(result.isPresent());
		assertEquals("Sankar", result.get().getCustomerName());
		verify(customerRepo, times(1)).findById((long) 2);
	}

	/**
	 * Test case for retrieving a customer by ID when not found. Expects a
	 * {@link ResourceNotFoundException}.
	 */
	@Test
	void testGetByCustomerId_NotFound() {
		when(customerRepo.findById((long) 20)).thenReturn(Optional.empty());
		ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class, () -> {
			customerService.getByCustomerId((long) 20);
		});
		assertEquals("Customer  ID: " + 20 + " not found", resourceNotFoundException.getMessage());
		verify(customerRepo, times(1)).findById((long) 20);
	}

	/**
	 * Test case for calculating reward points when amount = 0.
	 */
	@Test
	void testCalculateRewardPoints0() {
		long rewardPoints = customerService.calculateRewardPoints((long) 0);
		assertEquals(0, rewardPoints);
	}

	/**
	 * Test case for calculating reward points when amount = 50.
	 */
	@Test
	void testCalculateRewardPoints50() {
		long rewardPoints = customerService.calculateRewardPoints(50);
		assertEquals(0, rewardPoints);
	}

	/**
	 * Test case for calculating reward points when amount = 100.
	 */
	@Test
	void testCalculateRewardPoints100() {
		long rewardPoints = customerService.calculateRewardPoints(100);
		assertEquals(50, rewardPoints);
	}

	/**
	 * Test case for calculating reward points when amount is less than 50.
	 */
	@Test
	void testCalculateRewardPoints0To50() {
		long rewardPoints = customerService.calculateRewardPoints(40);
		assertEquals(0, rewardPoints);
	}

	/**
	 * Test case for calculating reward points when amount is between 50 and 100.
	 */
	@Test
	void testCalculateRewardPointsBetween50To100() {
		long rewardPoints = customerService.calculateRewardPoints(60);
		assertEquals(10, rewardPoints);
	}

	/**
	 * Test case for calculating reward points when amount is above 100.
	 */
	@Test
	void testCalculateRewardPointsAbove100() {
		long rewardPoints = customerService.calculateRewardPoints(120);
		assertEquals(90, rewardPoints);
	}

}
