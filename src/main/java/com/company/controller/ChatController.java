package com.company.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.exception.NotExistUserException;
import com.company.model.dto.ChatWrapper;
import com.company.model.dto.chat.request.CreateChatRequest;
import com.company.model.dto.chat.response.ChatListResponse;
import com.company.service.ChatService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/app_community/v1/chat")
@RequiredArgsConstructor
@CrossOrigin
public class ChatController {

	private final ChatService chatService;

	// 실시간 채팅 등록
	@Operation(summary = "실시간 채팅 등록", description = "실시간 채팅을 등록합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "실시간 채팅 등록 성공", content = @Content(schema = @Schema(implementation = ChatWrapper.class))),
			@ApiResponse(responseCode = "400", description = "유효하지 않은 유저가 실시간 채팅을 등록할 때", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
	@PostMapping("/register")
	public ResponseEntity<ChatWrapper> createNewChatHandle(@AuthenticationPrincipal String principal,
			CreateChatRequest req) throws NotExistUserException {

		var response = chatService.save(principal, req);

		return new ResponseEntity<ChatWrapper>(response, HttpStatus.OK);

	}

	// 실시간 채팅 불러오기
	@Operation(summary = "실시간 채팅 불러오기", description = "등록된 실시간 채팅들을 최신순으로 정렬합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "실시간 채팅 불러오기 성공", content = @Content(schema = @Schema(implementation = ChatListResponse.class))) })
	@GetMapping("/list")
	public ResponseEntity<ChatListResponse> allChatHandle(Integer page) {

		ChatListResponse chatListResponse = new ChatListResponse(chatService.allChatRead(page));

		return new ResponseEntity<ChatListResponse>(chatListResponse, HttpStatus.OK);

	}
}
