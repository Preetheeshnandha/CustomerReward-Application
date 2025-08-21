package com.customerReward.application.dto;

import java.util.*;

import lombok.*;

/**
 * Data Transfer Object (DTO) representing the reward details for a customer.
 * 
 * <p>This class encapsulates information about a customer's identity,
 * their monthly transaction amounts, monthly reward points, and the total reward points accumulated.</p>
 * 
 * <p>Used primarily for transferring reward-related data between layers of the application.</p>
 * 
 * @author Preetheeshnandha
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RewardDetailsDTO {

	/** Unique identifier of the customer. */
	private long customerId;
	
	/** Name of the customer. */
	private String customerName;
	
	/** Email address of the customer. */
	private String customerEmail;
	
	/**
     * Map of monthly transaction amounts, where the key is a month-year string
     * (e.g., "JUNE-2025") and the value is the total transaction amount for that month.
     */
	private Map<String, Long> monthlyTransactionAmount;
	
	/**
     * Map of monthly reward points, where the key is a month-year string
     * and the value is the reward points earned in that month.
     */
	private Map<String, Long> monthlyRewards;
	
    /** Total reward points accumulated by the customer over the specified period. */
	private long totalRewardPoints;

}
