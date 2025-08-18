package com.customerReward.application.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	// Invalid request exception
	@ExceptionHandler(InvalidRequestException.class)
	public ResponseEntity<Map<String,String>> handleInvalidRequestException(InvalidRequestException invalidRequestException){
		Map<String, String> error = new HashMap<>();
        error.put("error", "Invalid Request Parameter");
        error.put("message", invalidRequestException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	
	// Resource not found exception
	@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException resourcesNotFoundException) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Resource Not Found");
        error.put("message", resourcesNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error); 
    }

	
	// Catch any other unhandled exception
	@ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGlobalException(Exception exception) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Internal Server Error");
        error.put("message", "Something went wrong, please check the endpoint.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
	
}
