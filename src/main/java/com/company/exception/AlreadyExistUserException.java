package com.company.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AlreadyExistUserException extends Exception {
	
	String message;

}
