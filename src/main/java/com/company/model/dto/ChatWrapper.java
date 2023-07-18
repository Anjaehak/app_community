package com.company.model.dto;

import java.time.LocalDateTime;

import com.company.model.entity.Chat;
import com.company.model.entity.User;

import lombok.Data;

@Data
public class ChatWrapper {

	private Integer id;

	private UserWrapper usersId;

	private String chatMessage;

	private LocalDateTime chatDate;

	public ChatWrapper(Chat entity) {
		this.id = entity.getId();
		this.usersId = new UserWrapper(entity.getUsersId());
		this.chatMessage = entity.getChatMessage();
		this.chatDate = entity.getChatDate();
	}

}
