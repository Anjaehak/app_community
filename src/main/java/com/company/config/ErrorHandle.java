package com.company.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.company.exception.AlreadyCertifyException;
import com.company.exception.CertifyFailException;
import com.company.exception.ErrorPasswordException;
import com.company.exception.ExistUserException;
import com.company.exception.NoRecommedException;
import com.company.exception.NotExistPostException;
import com.company.exception.NotExistReplyException;
import com.company.exception.NotExistUserException;

@ControllerAdvice
public class ErrorHandle {

	@ExceptionHandler(AlreadyCertifyException.class)
	public ResponseEntity<String> exceptionHandle(AlreadyCertifyException ex) {
		String message = ex.getMessage();
		return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CertifyFailException.class)
	public ResponseEntity<String> exceptionHandle(CertifyFailException ex) {
		String message = ex.getMessage();
		return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ErrorPasswordException.class)
	public ResponseEntity<String> exceptionHandle(ErrorPasswordException ex) {
		String message = ex.getMessage();
		return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ExistUserException.class)
	public ResponseEntity<String> exceptionHandle(ExistUserException ex) {
		String message = ex.getMessage();
		return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoRecommedException.class)
	public ResponseEntity<String> exceptionHandle(NoRecommedException ex) {
		String message = ex.getMessage();
		return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotExistPostException.class)
	public ResponseEntity<String> exceptionHandle(NotExistPostException ex) {
		String message = ex.getMessage();
		return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotExistReplyException.class)
	public ResponseEntity<String> exceptionHandle(NotExistReplyException ex) {
		String message = ex.getMessage();
		return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotExistUserException.class)
	public ResponseEntity<String> exceptionHandle(NotExistUserException ex) {
		String message = ex.getMessage();
		return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);
	}
}
