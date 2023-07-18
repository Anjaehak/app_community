package com.company.controller;

import org.apache.ibatis.annotations.Delete;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.exception.NotExistPostException;
import com.company.exception.NotExistReplyException;
import com.company.exception.NotExistUserException;
import com.company.model.dto.reply.request.ReplyCreateRequest;
import com.company.model.dto.reply.request.ReplyDeleteRequest;
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
	private final ReplyService replyService;

	// 댓글,대댓글작성
	@PostMapping("/create")
	public ResponseEntity<Void> createReplyHandle(String principal, ReplyCreateRequest req)
			throws NotExistPostException, NotExistUserException {
		replyService.specificPostReplyCreate(principal, req);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@Delete("/delete")
	public ResponseEntity<Void> deleteReplyHandle(String pricipal, ReplyDeleteRequest req)
			throws NotExistReplyException {
		replyService.specificPostReplyDelete(pricipal, req);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
