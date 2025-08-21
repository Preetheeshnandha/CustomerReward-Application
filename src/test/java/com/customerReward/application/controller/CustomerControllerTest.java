package com.customerReward.application.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.customerReward.application.dto.RewardDetailsDTO;
import com.customerReward.application.entity.Customer;
import com.customerReward.application.service.CustomerServiceImpl;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.*;

/**
 * Unit tests for {@link CustomerController} using Spring's MockMvc.
 * 
 * <p>
 * This class tests the REST endpoints exposed by the CustomerController by mocking
 * the service layer and verifying HTTP responses and status codes.
 * </p>
 * 
 * <p>
 * {@code @WebMvcTest} is used to initialize only the controller-related components.
 * </p>
 */
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerServiceImpl customerService;

	/**
     * Test for GET /customers endpoint.
     * <p>
     * Verifies that a list of customers is returned and contains expected content.
     * </p>
     */
	@Test
	public void getAllCustomers() throws Exception {
		List<Customer> customers = new ArrayList<>();
		Customer customer = new Customer();
		customer.setCustomerId(1);
		customer.setCustomerName("Sridhar");
		customer.setCustomerEmail("sridhar@gmail.com");
		customers.add(customer);
		when(customerService.getAllCustomer()).thenReturn(customers);
		mockMvc.perform(get("/customers").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().string(containsString("Sridhar")));
	}

	/**
     * Test for GET /customers/{id} when customer is found.
     * <p>
     * Verifies that the correct customer details are returned.
     * </p>
     */
	@Test
	public void testGetByCustomerId_Found() throws Exception {
		Customer customer = new Customer();
		customer.setCustomerId(1);
		customer.setCustomerName("Sridhar");
		customer.setCustomerEmail("sridhar@gmail.com");
		when(customerService.getByCustomerId((long) 1)).thenReturn(Optional.of(customer));
		mockMvc.perform(get("/customers/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().string(containsString("Sridhar")));
	}

	/**
     * Test for GET /customers/{id} when customer is not found.
     * <p>
     * Verifies that the endpoint returns 404 NOT FOUND.
     * </p>
     */
	@Test
	public void testGetByCustomerId_NotFound() throws Exception {
		when(customerService.getByCustomerId((long) 10)).thenReturn(Optional.empty());
		mockMvc.perform(get("/customers/10").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}

	/**
     * Test for GET /customers/reward with default lastNMonths=3.
     * <p>
     * Verifies that rewards are returned for the default time range.
     * </p>
     */
	@Test
	public void testGetAllCustomerRewardDetails_WithDefaultParam() throws Exception {
		List<RewardDetailsDTO> rewards = new ArrayList<>();
		RewardDetailsDTO dto = new RewardDetailsDTO();
		dto.setCustomerId(1);
		dto.setTotalRewardPoints(150);
		rewards.add(dto);
		when(customerService.getAllCustomerRewardDetails(3)).thenReturn(rewards);
		mockMvc.perform(get("/customers/reward").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().string(containsString("150")));
	}

	/**
     * Test for GET /customers/reward with a custom lastNMonths parameter.
     * <p>
     * Verifies that rewards are returned correctly for the specified time range.
     * </p>
     */
	@Test
	public void testGetAllCustomerRewardDetails_WithCustomParam() throws Exception {
		List<RewardDetailsDTO> rewards = new ArrayList<>();
		RewardDetailsDTO dto = new RewardDetailsDTO();
		dto.setCustomerId(1);
		dto.setTotalRewardPoints(200);
		rewards.add(dto);
		when(customerService.getAllCustomerRewardDetails(6)).thenReturn(rewards);
		mockMvc.perform(get("/customers/reward").param("lastNMonths", "6").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(containsString("200")));
	}
}
