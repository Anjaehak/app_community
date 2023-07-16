package com.company.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.exception.NotExistPostException;
import com.company.exception.NotExistUserException;
import com.company.model.dto.reply.request.ReplyCreateRequest;
import com.company.model.dto.reply.request.ReplyReadRequest;
import com.company.model.entity.Post;
import com.company.repository.PostRepository;
import com.company.service.ReplyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/app_comunity/v1/reply")
@RequiredArgsConstructor
@CrossOrigin
public class ReplyController {
	private final PostRepository postRepository;
	private final ReplyService replyService;

	// 특정게시글의 댓글 전체불러오기
	@GetMapping("/list")
	public ResponseEntity<?> readAllReplyHandle(ReplyReadRequest req)
			throws NotExistPostException {
		System.out.println(req.getPostId());
		Post post = postRepository.findById(req.getPostId()).orElseThrow(() -> new NotExistPostException());
		var comments = replyService.specificPostReplyRead(req);
		System.out.println(comments.toString());
		return new ResponseEntity<>( HttpStatus.OK);
	}

	// 특정게시글의 댓글작성
	@PostMapping("/register")
	public ResponseEntity<?> createReplyHandle(String principal, ReplyCreateRequest req)
			throws NotExistPostException, NotExistUserException {
		replyService.specificPostReplyCreate(principal, req);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
