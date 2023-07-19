package com.company.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

	public ChatWrapper save(String principal, CreateChatRequest req) throws NotExistUserException {
		User user = userRepository.findByEmail(principal).orElseThrow(() -> new NotExistUserException());

		Chat chat = Chat.builder().usersId(user).chatMessage(req.getChatMessage()).build();
		chatRepository.save(chat);

		var response = new ChatWrapper(chat);
		return response;
	}

	public List<ChatWrapper> allChatRead(Integer page) {

		if (page == null) {
			page = 1;
		}

		Sort sort = Sort.by(Sort.Direction.DESC, "chatDate");

		Pageable pageable = PageRequest.of(page - 1, 10, sort);

		List<Chat> chats = chatRepository.findAll(pageable).getContent();

		List<ChatWrapper> chatWrappers = chats.stream().map(e -> new ChatWrapper(e)).toList();

		return chatWrappers;
	}
}
