package com.company.model.dto.chat.response;

import java.util.List;

import com.company.model.dto.ChatWrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatListResponse {

	List<ChatWrapper> chatWrapperLi;
}
