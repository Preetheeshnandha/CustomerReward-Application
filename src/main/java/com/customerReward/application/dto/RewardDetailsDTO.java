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
	private Map<String, Long> monthlyTransactionAmount;
	private Map<String, Long> monthlyRewards;
	private long totalRewardPoints;

}
