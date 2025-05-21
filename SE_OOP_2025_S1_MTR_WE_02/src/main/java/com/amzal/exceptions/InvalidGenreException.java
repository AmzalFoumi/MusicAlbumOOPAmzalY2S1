package com.amzal.exceptions;

public class InvalidGenreException extends Exception {
	
	public InvalidGenreException(String message) {
		super("InvalidGenreException: " + message);
		
	}
}
