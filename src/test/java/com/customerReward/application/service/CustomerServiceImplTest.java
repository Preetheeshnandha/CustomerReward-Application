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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

	@Mock
	private CustomerRepository customerRepo;

	@InjectMocks
	private CustomerServiceImpl customerService;

	@Test
	void testGetAllCustomer() {

		List<Customer> mockAllCustomers = List.of(new Customer(1, "Kumar", "kumar@gmail.com", null),
				new Customer(2, "Sankar", "sankar@gmail.com", null));

		when(customerRepo.findAll()).thenReturn(mockAllCustomers);

		List<Customer> customer = customerService.getAllCustomer();

		assertNotNull(customer);
		assertEquals(2, customer.size());
		assertEquals("Kumar", customer.get(0).getCustomerName());

		verify(customerRepo, times(2)).findAll();
	}

	@Test
	void testGetByCustomerId_Found() {
		Customer customer = new Customer(2, "Sankar", "sankar@gmail.com", null);

		when(customerRepo.findById((long)2)).thenReturn(Optional.of(customer));

		Optional<Customer> result = customerService.getByCustomerId((long) 2);

		assertTrue(result.isPresent());
		assertEquals("Sankar", result.get().getCustomerName());
		verify(customerRepo,times(2)).findById((long) 2);
	}

	@Test
	void testGetByCustomerId_NotFound() {
		when(customerRepo.findById((long) 20)).thenReturn(Optional.empty());

		ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class, () -> {
			customerService.getByCustomerId((long)20);
		});
		
		assertEquals("Customer details not found", resourceNotFoundException.getMessage());
		
		verify(customerRepo, times(1)).findById((long)20);
	}

	@Test
	void testCalculateRewardPointsAbove100() {

		int rewardPoints = customerService.calculateRewardPoints(120);
		assertEquals(90, rewardPoints);
	}

	@Test
	void testCalculateRewardPointsBetween50To100() {

		int rewardPoints = customerService.calculateRewardPoints(60);
		assertEquals(10, rewardPoints);
	}

	@Test
	void testCalculateRewardPointsBelow50() {

		int rewardPoints = customerService.calculateRewardPoints(40);
		assertEquals(0, rewardPoints);
	}

}
