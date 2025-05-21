package com.amzal.exceptions;

public class InvalidPriceException extends Exception {

	public InvalidPriceException(String message) {
		super("InvalidPriceException: " + message);
		
	}

}
