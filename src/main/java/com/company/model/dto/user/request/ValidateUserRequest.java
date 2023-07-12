package com.company.model.dto.user.request;

import lombok.Data;

@Data
public class ValidateUserRequest {
	
	private String email;
	private String pass;
}
