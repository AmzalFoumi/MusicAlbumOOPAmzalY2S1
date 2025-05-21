package com.amzal.exceptions;

public class InvalidTitleException extends Exception {

	public InvalidTitleException(String message) {
		super("InvalidTitleException: " + message);
	}
}
