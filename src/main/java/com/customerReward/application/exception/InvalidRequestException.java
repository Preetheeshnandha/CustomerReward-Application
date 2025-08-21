package com.customerReward.application.exception;


/**
 * Exception thrown when an invalid request parameter or input is encountered.
 * 
 * <p>This is a custom unchecked exception that extends {@link RuntimeException} and is used
 * to indicate that a client has provided invalid data in the request, such as
 * invalid parameter values.</p>
 * 
 * @author Preetheeshnandha
 */
public class InvalidRequestException extends RuntimeException{

	/**
     * Constructs a new {@code InvalidRequestException} with the specified detail message.
     * 
     * @param message the detail message explaining the reason for the exception
     */
	public InvalidRequestException(String message) {
		super(message);
	}
}
