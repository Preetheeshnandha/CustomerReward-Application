package com.customerReward.application.exception;

/**
 * Exception thrown when a requested resource is not found.
 * 
 * <p>This is a custom unchecked exception that extends {@link RuntimeException} and is used
 * to indicate that a requested entity, such as a customer or transaction, does not exist
 * in the system.</p>
 * 
 * @author Preetheeshnandha
 */
public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	/**
     * Constructs a new {@code ResourceNotFoundException} with the specified detail message.
     * 
     * @param message the detail message explaining the reason for the exception
     */
	public ResourceNotFoundException(String message) {
		super(message);
	}
}