package com.company.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.company.exception.UnequalPasswordException;
import com.company.exception.NotExistPostException;
import com.company.exception.NotExistReplyException;
import com.company.exception.NotExistUserException;
import com.company.model.dto.post.request.PostDeleteRequest;
import com.company.model.dto.user.request.DeleteUserRequest;
import com.company.service.PostService;
import com.company.service.SocialLoginService;
import com.company.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/app_community/v1/delete")
@RequiredArgsConstructor
@CrossOrigin
public class DeleteController {

	private final UserService userService;

	private final SocialLoginService socialLoginService;

	private final PostService postService;

	@DeleteMapping("/user")
	public ResponseEntity<Void> deleteUserHandle(@AuthenticationPrincipal String principal, DeleteUserRequest req)
			throws NotExistUserException, UnequalPasswordException, NotExistPostException, NotExistReplyException {
		String[] data = principal.split("@");

		if (data[1].endsWith("social")) {
			if (data[1].startsWith("kakao")) {
				socialLoginService.unlinkKakao(principal);
			} else {
				socialLoginService.unlinkNaver(principal);
			}

			userService.deleteSpecificSocialUser(principal);

		} else {
			userService.deleteSpecificUser(principal, req);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@DeleteMapping("/post")
	public ResponseEntity<Void> specificDeletePostHandle(String principal, PostDeleteRequest req)
			throws NotExistPostException, NotExistReplyException {

		postService.deleteSpecificPost(principal, req);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
