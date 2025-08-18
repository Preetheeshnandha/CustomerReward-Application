package com.customerReward.application.exception;

public class InvalidRequestException extends RuntimeException{

	public InvalidRequestException(String message) {
		super(message);
	}
}
