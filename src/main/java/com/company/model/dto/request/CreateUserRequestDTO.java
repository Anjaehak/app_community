package com.company.model.dto.request;

import lombok.Data;

@Data
public class CreateUserRequestDTO {
	private String email;
	private String pass;
	private String nick;
	private String userImage;

}
