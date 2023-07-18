package com.company.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.exception.NotExistPostException;
import com.company.exception.NotExistUserException;
import com.company.model.dto.PostWrapper;
import com.company.model.dto.post.request.CreatePostRequest;
import com.company.model.dto.post.request.ReadPostRequest;
import com.company.model.dto.post.request.UpdatePostRequest;
import com.company.model.dto.post.response.AllPostsResponse;
import com.company.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/app_comunity/v1/post")
@RequiredArgsConstructor
@CrossOrigin
public class PostController {

	private final PostService postService;

	// 전체글 불러오기
	@GetMapping("/list")
	public ResponseEntity<AllPostsResponse> readAllPostHandle() {

		AllPostsResponse datas = postService.allPosts();

		return new ResponseEntity<AllPostsResponse>(datas, HttpStatus.OK);
	}

	// 신규글 등록
	@PostMapping("/register")
	public ResponseEntity<Void> createNewPostHandle(String principal, CreatePostRequest req)
			throws NotExistUserException, IllegalStateException, IOException {

		postService.save(principal, req);

		return new ResponseEntity<>(HttpStatus.CREATED);

	}

	// 게시글 수정
	@PatchMapping("/modify")
	public ResponseEntity<Void> modifyPostHandle(String principal, UpdatePostRequest req)
			throws NotExistUserException, NotExistPostException {
		postService.update(principal, req);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	// 특정게시글 불러오기
	@GetMapping("/specific-post")
	public ResponseEntity<PostWrapper> specificReadPostHandle(ReadPostRequest req) throws NotExistPostException {

		PostWrapper postResponse = postService.getSpecificPost(req.getId());

		return new ResponseEntity<PostWrapper>(postResponse, HttpStatus.OK);

	}

}
