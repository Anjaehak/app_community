package com.company.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CertifyUserResponse {

	String token;
	String email;
}
