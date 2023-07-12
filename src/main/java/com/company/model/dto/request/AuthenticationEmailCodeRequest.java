package com.company.model.dto.request;

import lombok.Data;

@Data
public class AuthenticationEmailCodeRequest {

	String code;
	String email;
}
