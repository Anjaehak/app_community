package com.company.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.exception.NotExistUserException;
import com.company.model.dto.ChatWrapper;
import com.company.model.dto.chat.request.CreateChatRequest;
import com.company.service.ChatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/app_comunity/v1/chat")
@RequiredArgsConstructor
@CrossOrigin
public class ChatController {

	private final ChatService chatService;

	// 실시간 채팅 등록
	@PostMapping("/register")
	public ResponseEntity<?> createNewChatHandle(String principal, CreateChatRequest req) throws NotExistUserException {

		chatService.save(principal, req);

		return new ResponseEntity<>(HttpStatus.OK);

	}

	// 실시간 채팅 불러오기
	@GetMapping("/list")
	// 인증한 사람만 실시간 채팅을 이용할수있게
	public ResponseEntity<List<ChatWrapper>> allChatHandle(String principal) throws NotExistUserException {

		List<ChatWrapper> chatWrappers = chatService.allChatRead(principal);

		return new ResponseEntity<List<ChatWrapper>>(chatWrappers,HttpStatus.OK);

	}
}
