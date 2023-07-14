package com.company.model.dto;

import com.company.model.entity.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserWrapper {

	private Integer id;
	private String nick;
	private String email;
	private String userImage;
	
	public UserWrapper(User entity) {
		this.id = entity.getId();
		this.nick = entity.getNick();
		this.email = entity.getEmail();
		this.userImage = entity.getUserImage();
	}
}
