package com.company.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/app_comunity/v1/reply")
@RequiredArgsConstructor
public class ReplyController {

	private final ReplyRepository replyRepository;

	// 특정게시글의 댓글 전체불러오기
	public ResponseEntity<?> readAllReplyHandle() {

		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
