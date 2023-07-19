package com.company.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.exception.NoRecommedException;
import com.company.exception.NotExistPostException;
import com.company.exception.NotExistReplyException;
import com.company.exception.NotExistUserException;
import com.company.model.dto.post.request.LikeRequest;
import com.company.service.RecommendService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/app_community/v1/recommend")
@RequiredArgsConstructor
@CrossOrigin
public class RecommendController {

	private final RecommendService recommendService;

	@Operation(summary = "좋아요생성", description = "게시글좋아요,댓글좋아요")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "좋아요 생성 성공", content = @Content(schema = @Schema(implementation = Void.class))),
			@ApiResponse(responseCode = "400", description = "유저가미인증상태또는 게시글이나 댓글이 존재하지않을경우", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
	// 게시글 좋아요
	@PostMapping("/like")
	public ResponseEntity<Void> postLikeHandle(String principal, LikeRequest req)
			throws NotExistUserException, NotExistPostException, NotExistReplyException {

		recommendService.createRecommend(principal, req);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Operation(summary = "좋아요삭제", description = "게시글좋아요 삭제,댓글좋아요 삭제")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "좋아요 삭제 성공", content = @Content(schema = @Schema(implementation = Void.class))),
			@ApiResponse(responseCode = "400", description = "유저가미인증상태 또는 게시글or댓글이 존재하지않거나 작성자가 아닐때", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
	// 게시글 좋아요 취소
	@DeleteMapping("/like")
	public ResponseEntity<Void> postUnLikeHandle(String principal, LikeRequest req)
			throws NotExistPostException, NotExistUserException, NoRecommedException, NotExistReplyException {

		recommendService.deleteRecommend(principal, req);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
