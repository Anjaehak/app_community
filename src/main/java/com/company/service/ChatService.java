package com.company.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.company.exception.NotExistUserException;
import com.company.model.dto.ChatWrapper;
import com.company.model.dto.chat.request.CreateChatRequest;
import com.company.model.entity.Chat;
import com.company.model.entity.User;
import com.company.repository.ChatRepository;
import com.company.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

	private final UserRepository userRepository;
	private final ChatRepository chatRepository;

	public void save(String principal, CreateChatRequest req) throws NotExistUserException {
		User user = userRepository.findByEmail(principal).orElseThrow(() -> new NotExistUserException());

		Chat chat = Chat.builder().usersId(user).chatMessage(req.getChatMessage()).build();
		chatRepository.save(chat);
	}

	public List<ChatWrapper> allChatRead(String principal) throws NotExistUserException {
		// 회원가입한 유저인지 확인
		User user = userRepository.findByEmail(principal).orElseThrow(() -> new NotExistUserException());

		List<Chat> chats = chatRepository.findAll(Sort.by("chat_date").descending());
		List<ChatWrapper> chatWrappers = new ArrayList<>();
		for (Chat chat : chats) {
			var chatWrapper = new ChatWrapper(chat);
			chatWrappers.add(chatWrapper);
		}
		return chatWrappers;

	}

}
