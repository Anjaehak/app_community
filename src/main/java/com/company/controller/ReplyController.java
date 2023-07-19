package com.company.controller;

import org.apache.ibatis.annotations.Delete;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.exception.NotExistPostException;
import com.company.exception.NotExistReplyException;
import com.company.exception.NotExistUserException;
import com.company.model.dto.reply.request.ReplyCreateRequest;
import com.company.model.dto.reply.request.ReplyDeleteRequest;
import com.company.service.ReplyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/app_community/v1/reply")
@RequiredArgsConstructor
@CrossOrigin
public class ReplyController {
	private final ReplyService replyService;

	// 댓글,대댓글작성
	@Operation(summary = "댓글,대댓글생성", description = "댓글,대댓글생성")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "리플 생성 성공", content = @Content(schema = @Schema(implementation = Void.class))),
			@ApiResponse(responseCode = "400", description = "유저가인증되지않은경우", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
	@PostMapping("/create")
	public ResponseEntity<Void> createReplyHandle(String principal, ReplyCreateRequest req)
			throws NotExistPostException, NotExistUserException {
		replyService.specificPostReplyCreate(principal, req);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	@Operation(summary = "댓글,대댓글삭제", description = "댓글,대댓글삭제")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "댓글,대댓글 삭제", content = @Content(schema = @Schema(implementation = Void.class))),
			@ApiResponse(responseCode = "400", description = "유저가인증되지않거나,작성자가아닌경우", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
	@Delete("/delete")
	public ResponseEntity<Void> deleteReplyHandle(String pricipal, ReplyDeleteRequest req)
			throws NotExistReplyException {
		replyService.specificPostReplyDelete(pricipal, req);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
