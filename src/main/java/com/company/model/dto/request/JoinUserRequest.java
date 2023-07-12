package com.company.model.dto.request;

import lombok.Data;

@Data
public class JoinUserRequest {

	private String email;

	private String password;

	private String nick;

}
