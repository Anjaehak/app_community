package com.company.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.exception.NotExistPostException;
import com.company.exception.NotExistUserException;
import com.company.exception.UnequalUserException;
import com.company.model.dto.PostWrapper;
import com.company.model.dto.post.request.CreatePostRequest;
import com.company.model.dto.post.request.ReadPostRequest;
import com.company.model.dto.post.request.UpdatePostRequest;
import com.company.model.dto.post.response.AllPostsResponse;
import com.company.service.PostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/app_community/v1/post")
@RequiredArgsConstructor
@CrossOrigin
public class PostController {

	private final PostService postService;

	// 전체글 불러오기
	@Operation(summary = "전체글 불러오기", description = "유저들이 작성한 글을 페이징처리와 글 작성 시간을 기준으로 정렬해서 모두 불러옵니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "전체글 불러오기 성공", content = @Content(schema = @Schema(implementation = AllPostsResponse.class))) })
	@GetMapping("/list")
	public ResponseEntity<AllPostsResponse> readAllPostHandle(Integer page) {

		AllPostsResponse datas = postService.allPosts(page);

		return new ResponseEntity<AllPostsResponse>(datas, HttpStatus.OK);
	}

	// 신규글 등록
	@Operation(summary = "새로운 글 등록", description = "제목과 사진을 포함한 내용이 담긴 게시글을 작성합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "글 작성 성공", content = @Content(schema = @Schema(implementation = Void.class))),
			@ApiResponse(responseCode = "400", description = "존재하지 않은 유저가 게시글을 작성 할 때", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
	@PostMapping("/register")
	public ResponseEntity<Void> createNewPostHandle(@AuthenticationPrincipal String principal, CreatePostRequest req)
			throws NotExistUserException, IllegalStateException, IOException {

		postService.save(principal, req);

		return new ResponseEntity<>(HttpStatus.CREATED);

	}

	// 게시글 수정
	@Operation(summary = "게시글 수정", description = "제목과 사진을 포함한 내용이 담긴 게시글을 수정합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "글 수정 성공", content = @Content(schema = @Schema(implementation = Void.class))),
			@ApiResponse(responseCode = "400", description = "존재하지 않은 유저가 게시글을 작성 할 때 or 해당 게시글 작성자와 수정할려는 사용자가 다를 때", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
	@PatchMapping("/modify")
	public ResponseEntity<Void> modifyPostHandle(@AuthenticationPrincipal String principal, UpdatePostRequest req)
			throws NotExistUserException, NotExistPostException, UnequalUserException {
		postService.update(principal, req);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	// 특정게시글 불러오기
	@Operation(summary = "특정 게시글 불러오기", description = "제목과 사진을 포함한 내용과 댓글 좋아요들이 담긴 게시글을 불러옵니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "글 수정 성공", content = @Content(schema = @Schema(implementation = PostWrapper.class))),
			@ApiResponse(responseCode = "400", description = "해당 게시글이 존재하지 않을 때", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
	@GetMapping("/specific-post")
	public ResponseEntity<PostWrapper> specificReadPostHandle(ReadPostRequest req) throws NotExistPostException {

		PostWrapper postResponse = postService.getSpecificPost(req.getId());

		return new ResponseEntity<PostWrapper>(postResponse, HttpStatus.OK);

	}
}
