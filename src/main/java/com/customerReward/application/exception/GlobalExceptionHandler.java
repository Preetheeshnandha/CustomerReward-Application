package com.customerReward.application.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for REST controllers.
 * 
 * <p>
 * This class uses {@code @RestControllerAdvice} to provide centralized
 * exception handling across all controllers in the application.
 * </p>
 * 
 * <p>
 * It defines specific handlers for custom exceptions such as
 * {@link InvalidRequestException} and {@link ResourceNotFoundException}, as
 * well as a generic handler for all other exceptions.
 * </p>
 * 
 * <p>
 * Each handler returns a structured {@link ResponseEntity} with an HTTP status
 * code and a JSON body containing error details.
 * </p>
 * 
 * @author Preetheeshnandha
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Handles {@link InvalidRequestException} thrown when a client provides invalid
	 * request parameters.
	 * 
	 * @param invalidRequestException the exception thrown
	 * @return a {@link ResponseEntity} containing error details with HTTP status
	 *         400 (Bad Request)
	 */
	@ExceptionHandler(InvalidRequestException.class)
	public ResponseEntity<Map<String, String>> handleInvalidRequestException(
			InvalidRequestException invalidRequestException) {
		Map<String, String> error = new HashMap<>();
		error.put("error", "Invalid Request Parameter");
		error.put("message", invalidRequestException.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	/**
	 * Handles {@link ResourceNotFoundException} thrown when a requested resource is
	 * not found in the system.
	 * 
	 * @param resourcesNotFoundException the exception thrown
	 * @return a {@link ResponseEntity} containing error details with HTTP status
	 *         404 (Not Found)
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleResourceNotFoundException(
			ResourceNotFoundException resourcesNotFoundException) {
		Map<String, String> error = new HashMap<>();
		error.put("error", "Resource Not Found");
		error.put("message", resourcesNotFoundException.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	/**
	 * Handles any other exceptions that are not explicitly handled by other
	 * {@code @ExceptionHandler} methods.
	 * 
	 * @param exception the exception thrown
	 * @return a {@link ResponseEntity} containing a generic error message with HTTP
	 *         status 500 (Internal Server Error)
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleGlobalException(Exception exception) {
		Map<String, String> error = new HashMap<>();
		error.put("error", "Internal Server Error");
		error.put("message", "Something went wrong, please check the endpoint.");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}

}
