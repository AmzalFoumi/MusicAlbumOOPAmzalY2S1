package com.amzal.exceptions;

public class InvalidRegionException extends Exception {

	public InvalidRegionException(String message) {
		super("InvalidRegionException: " + message);
	}
}

