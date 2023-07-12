package com.company.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public class ErrorPasswordException extends Exception {
	public ErrorPasswordException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
