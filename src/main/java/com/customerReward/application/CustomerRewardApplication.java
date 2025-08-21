package com.customerReward.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Entry point for the Customer Reward Application.
 * 
 * <p>This class bootstraps the Spring Boot application using
 * {@link SpringApplication#run(Class, String...)}. It initializes the application
 * context, auto-configures Spring beans, and starts the embedded server.</p>
 *
 * <p>The application is responsible for tracking customer purchases
 * and calculating reward points based on transaction history.</p>
 *
 * <p>Technologies used:
 * <ul>
 *   <li>Spring Boot</li>
 *   <li>Spring Data JPA</li>
 *   <li>H2 In-Memory Database</li>
 *   <li>RESTful APIs</li>
 *   <li>JUnit 5, Mockito</li>
 * </ul>
 * </p>
 * 
 * @author Preetheeshnandha
 */
@SpringBootApplication
public class CustomerRewardApplication {
	public static void main(String[] args) {
		SpringApplication.run(CustomerRewardApplication.class, args);
	}

}
