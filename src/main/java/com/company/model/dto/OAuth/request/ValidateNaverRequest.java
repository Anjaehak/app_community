package com.company.model.dto.OAuth.request;

import lombok.Data;

@Data
public class ValidateNaverRequest {

	String code;
	String state;
	
}
