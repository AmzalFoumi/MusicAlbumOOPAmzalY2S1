package com.amzal.exceptions;

public class InvalidLanguageException extends Exception {

	public InvalidLanguageException(String message) {
		super("InvalidLanguageException: " + message);
	}
}
