package com.company.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

	private int code;
	private String cause;
	private long timeStamp = System.currentTimeMillis();

	public ErrorResponse(int code, String cause) {
		this.code = code;
		this.cause = cause;
	}

}