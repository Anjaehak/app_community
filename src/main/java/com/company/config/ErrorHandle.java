package com.company.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.company.exception.AlreadyCertifyException;
import com.company.exception.CertifyFailException;
import com.company.exception.UnequalPasswordException;
import com.company.exception.ExistUserException;
import com.company.exception.NoRecommedException;
import com.company.exception.NotExistPostException;
import com.company.exception.NotExistReplyException;
import com.company.exception.NotExistUserException;
import com.company.exception.UnequalUserException;
import com.company.model.response.ErrorResponse;

@ControllerAdvice
public class ErrorHandle {

	@ExceptionHandler(AlreadyCertifyException.class)
	public ResponseEntity<ErrorResponse> exceptionHandle(AlreadyCertifyException ex) {
		var message = new ErrorResponse(400, "이미 인증을 받은 유저입니다.");
		return new ResponseEntity<ErrorResponse>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CertifyFailException.class)
	public ResponseEntity<ErrorResponse> exceptionHandle(CertifyFailException ex) {
		var message = new ErrorResponse(400, "인증코드가 일치하지 않습니다.");
		return new ResponseEntity<ErrorResponse>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UnequalPasswordException.class)
	public ResponseEntity<ErrorResponse> exceptionHandle(UnequalPasswordException ex) {
		var message = new ErrorResponse(400, "비밀번호가 일치하지 않습니다");
		return new ResponseEntity<ErrorResponse>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ExistUserException.class)
	public ResponseEntity<ErrorResponse> exceptionHandle(ExistUserException ex) {
		var message = new ErrorResponse(400, "이미 존재하는 사용자입니다.");
		return new ResponseEntity<ErrorResponse>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoRecommedException.class)
	public ResponseEntity<ErrorResponse> exceptionHandle(NoRecommedException ex) {
		var message = new ErrorResponse(400, "좋아요를 한 게시글 or 댓글이 아닙니다.");
		return new ResponseEntity<ErrorResponse>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotExistPostException.class)
	public ResponseEntity<ErrorResponse> exceptionHandle(NotExistPostException ex) {
		var message = new ErrorResponse(400, "해당 게시글이 존재하지 않습니다.");
		return new ResponseEntity<ErrorResponse>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotExistReplyException.class)
	public ResponseEntity<ErrorResponse> exceptionHandle(NotExistReplyException ex) {
		var message = new ErrorResponse(400, "해당 댓글이 존재하지 않습니다.");
		return new ResponseEntity<ErrorResponse>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotExistUserException.class)
	public ResponseEntity<ErrorResponse> exceptionHandle(NotExistUserException ex) {
		var message = new ErrorResponse(400, "해당 유저가 존재하지 않습니다.");
		return new ResponseEntity<ErrorResponse>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UnequalUserException.class)
	public ResponseEntity<ErrorResponse> exceptionHandle(UnequalUserException ex) {
		var message = new ErrorResponse(400, "해당 게시글을 작성한 유저가 아닙니다.");
		return new ResponseEntity<ErrorResponse>(message, HttpStatus.BAD_REQUEST);
	}
}
