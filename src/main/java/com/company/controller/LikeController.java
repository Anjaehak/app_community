package com.company.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.exception.NotExistPostException;
import com.company.exception.NotExistReplyException;
import com.company.exception.NotExistUserException;
import com.company.model.dto.post.request.PostLikeRequest;
import com.company.model.dto.reply.request.ReplyLikeRequest;
import com.company.service.RecommendService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/app_comunity/v1/recommend")
@RequiredArgsConstructor
@CrossOrigin
public class LikeController {

	private final RecommendService recommendService;

	// 게시글 좋아요
	@PostMapping("/post-like")
	public ResponseEntity<?> postLikeHandle(String principal, PostLikeRequest req)
			throws NotExistUserException, NotExistPostException {

		recommendService.recommendPost(principal, req.getPostId());

		return new ResponseEntity<>(HttpStatus.OK);
	}

	// 게시글 좋아요 취소
	@DeleteMapping("/post-like")
	public ResponseEntity<?> postUnLikeHandle(String principal, PostLikeRequest req)
			throws NotExistPostException, NotExistUserException {

		recommendService.unRecommendPost(principal, req.getPostId());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	// 댓글 대댓글 좋아요
	@PostMapping("/reply-like")
	public ResponseEntity<?> replyLikeHandle(String principal, ReplyLikeRequest req)
			throws NotExistPostException, NotExistReplyException, NotExistUserException {

		recommendService.recommendReply(principal, req);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	// 댓글 대댓글 좋아요 취소
	@DeleteMapping("reply-like")
	public ResponseEntity<?> replyUnLikeHandle(String principal, ReplyLikeRequest req)
			throws NotExistUserException, NotExistPostException, NotExistReplyException {

		recommendService.UnRecommendReply(principal, req);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
