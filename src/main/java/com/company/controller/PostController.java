package com.company.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.exception.NotExistPostException;
import com.company.exception.NotExistUserException;
import com.company.model.dto.request.CreatePostRequest;
import com.company.model.dto.request.PostLikeRequest;
import com.company.model.dto.request.UpdatePostRequest;
import com.company.model.entity.Post;
import com.company.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	// 전체글 불러오기
	@GetMapping("/list")
	public ResponseEntity<?> readAllPostHandle() {

		List<Post> Items = postService.allPosts();

		return new ResponseEntity<>(HttpStatus.OK);

	}

	// 신규글 등록
	@PostMapping("/register")
	public ResponseEntity<?> createNewPostHandle(@AuthenticationPrincipal String principal, CreatePostRequest req)
			throws NotExistUserException, IllegalStateException, IOException {

		postService.save(principal, req);

		return new ResponseEntity<>(HttpStatus.CREATED);

	}

	// 게시글 수정
	@PatchMapping("/retouch")
	public ResponseEntity<?> postOperationHandle(@AuthenticationPrincipal String principal, UpdatePostRequest req)
			throws NotExistUserException, NotExistPostException {
		postService.update(principal, req);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	// 게시글 좋아요
	@PostMapping("/like")
	public ResponseEntity<?> postLikeHandle(@AuthenticationPrincipal String principal, PostLikeRequest req)
			throws NotExistUserException, NotExistPostException {

		postService.recommendPost(principal, req.getPostId());

		return new ResponseEntity<>(HttpStatus.OK);
	}


}
