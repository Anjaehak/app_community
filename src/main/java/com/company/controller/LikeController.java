package com.company.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.exception.NotExistPostException;
import com.company.exception.NotExistUserException;
import com.company.model.dto.post.request.PostLikeRequest;
import com.company.repository.PostRepository;
import com.company.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/app_comunity/v1/like")
@RequiredArgsConstructor
@CrossOrigin
public class LikeController {

	private final PostService postService;

	// 게시글 좋아요
	@PostMapping("/like")
	public ResponseEntity<?> postLikeHandle(String principal, PostLikeRequest req)
			throws NotExistUserException, NotExistPostException {

		postService.recommendPost(principal, req.getPostId());

		return new ResponseEntity<>(HttpStatus.OK);
	}

}
