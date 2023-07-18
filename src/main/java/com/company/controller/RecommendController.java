package com.company.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/app_community/v1/recommend")
@RequiredArgsConstructor
@CrossOrigin
public class RecommendController {

	private final RecommendService recommendService;

	// 게시글 좋아요
	@PostMapping("/like")
	public ResponseEntity<Void> postLikeHandle(String principal, LikeRequest req)
			throws NotExistUserException, NotExistPostException, NotExistReplyException {

		recommendService.createRecommend(principal, req);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	// 게시글 좋아요 취소
	@DeleteMapping("/like")
	public ResponseEntity<Void> postUnLikeHandle(String principal, LikeRequest req)
			throws NotExistPostException, NotExistUserException, NoRecommedException, NotExistReplyException {

		recommendService.deleteRecommend(principal, req);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
