package com.customerReward.application.dto;

import java.util.*;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RewardDetailsDTO {

	private long customerId;
	private String customerName;
	private String customerEmail;
	private Map<String, Integer> monthlyTransactionAmount;
	private Map<String, Integer> monthlyRewards;
	private int totalRewardPoints;

}
