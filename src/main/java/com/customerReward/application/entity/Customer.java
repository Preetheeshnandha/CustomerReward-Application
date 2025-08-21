package com.customerReward.application.entity;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing a customer in the rewards application.
 *
 * <p>This class maps to the {@code customers} table in the database and contains
 * details about the customer including their name, email, and associated transactions.</p>
 *
 * <p>Validation constraints are applied to ensure data integrity (e.g., non-null values,
 * valid email format). The entity also maintains a bidirectional relationship with
 * the {@link Transaction} entity.</p>
 *
 * <p>Lombok is used to reduce boilerplate code by generating constructors, getters, and setters.</p>
 *
 * @author Preetheeshnandha
 */
@Entity
@Table(name = "customers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

	/**
     * The primary key identifier for the customer.
     * Auto-generated using {@code GenerationType.IDENTITY}.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	@NotNull
	private long customerId;

	/**
     * The name of the customer.
     * Must not be blank.
     */
	@Column(name = "customer_name")
	@NotBlank(message = "Customer name is required")
	private String customerName;

	
	/**
     * The email address of the customer.
     * Must be in valid email format and not blank.
     */
	@Column(name = "customer_email")
	@Email(message = "Invalid Email format")
	@NotBlank(message = "Customer email is required")
	private String customerEmail;

	/**
     * List of transactions associated with this customer.
     *
     * <p>Mapped by the {@code customer} field in the {@link Transaction} entity.
     * Cascade type is set to {@code ALL}, meaning changes to the customer will
     * cascade to transactions. Orphan removal is enabled to automatically delete
     * transactions removed from the list.</p>
     *
     * <p>{@code @JsonManagedReference} is used to handle bidirectional
     * JSON serialization correctly.</p>
     */
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<Transaction> transactions = new ArrayList<>();
}
