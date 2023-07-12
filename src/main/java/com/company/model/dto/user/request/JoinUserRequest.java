package com.company.model.dto.user.request;

import lombok.Data;

@Data
public class JoinUserRequest {

	private String email;

	private String password;

	private String nick;

}
