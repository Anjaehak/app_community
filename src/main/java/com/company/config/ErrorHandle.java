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
import com.company.model.response.ErrorResponse;

@ControllerAdvice
public class ErrorHandle {

	@ExceptionHandler(AlreadyCertifyException.class)
	public ResponseEntity<ErrorResponse> exceptionHandle(AlreadyCertifyException ex) {
		var message = new ErrorResponse(400, ex.getMessage());
		return new ResponseEntity<ErrorResponse>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CertifyFailException.class)
	public ResponseEntity<ErrorResponse> exceptionHandle(CertifyFailException ex) {
		var message = new ErrorResponse(400, ex.getMessage());
		return new ResponseEntity<ErrorResponse>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ErrorPasswordException.class)
	public ResponseEntity<ErrorResponse> exceptionHandle(ErrorPasswordException ex) {
		var message = new ErrorResponse(400, ex.getMessage());
		return new ResponseEntity<ErrorResponse>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ExistUserException.class)
	public ResponseEntity<ErrorResponse> exceptionHandle(ExistUserException ex) {
		var message = new ErrorResponse(400, ex.getMessage());
		return new ResponseEntity<ErrorResponse>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoRecommedException.class)
	public ResponseEntity<ErrorResponse> exceptionHandle(NoRecommedException ex) {
		var message = new ErrorResponse(400, ex.getMessage());
		return new ResponseEntity<ErrorResponse>(message,HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotExistPostException.class)
	public ResponseEntity<ErrorResponse> exceptionHandle(NotExistPostException ex) {
		var message = new ErrorResponse(400, ex.getMessage());
		return new ResponseEntity<ErrorResponse>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotExistReplyException.class)
	public ResponseEntity<ErrorResponse> exceptionHandle(NotExistReplyException ex) {
		var message = new ErrorResponse(400, ex.getMessage());
		return new ResponseEntity<ErrorResponse>(message,HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotExistUserException.class)
	public ResponseEntity<ErrorResponse> exceptionHandle(NotExistUserException ex) {
		var message = new ErrorResponse(400, ex.getMessage());
		return new ResponseEntity<ErrorResponse>(message,HttpStatus.BAD_REQUEST);
	}
}
