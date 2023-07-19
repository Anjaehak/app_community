package com.company.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public class UnequalPasswordException extends Exception {
	public UnequalPasswordException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
