package com.company.model.dto.request;

import lombok.Data;

@Data
public class ValidateUserRequest {
	
	private String email;
	private String pass;
}
