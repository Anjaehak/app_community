package com.company.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.company.exception.AlreadyCertifyException;
import com.company.exception.CertifyFailException;
import com.company.exception.ErrorPasswordException;
import com.company.exception.ExistUserException;
import com.company.exception.NotExistPostException;
import com.company.exception.NotExistReplyException;
import com.company.exception.NotExistUserException;
import com.company.model.dto.user.request.CertifyCodeRequest;
import com.company.model.dto.user.request.CertifyEmailRequest;
import com.company.model.dto.user.request.DeleteUserRequest;
import com.company.model.dto.user.request.JoinUserRequest;
import com.company.model.dto.user.request.ValidateUserRequest;
import com.company.model.dto.user.response.CertifyResponse;
import com.company.model.dto.user.response.ValidateUserResponse;
import com.company.service.CertifyService;
import com.company.service.JWTService;
import com.company.service.SocialLoginService;
import com.company.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/app_community/v1/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

	private final UserService userService;

	private final CertifyService mailService;

	private final JWTService jwtService;

	private final SocialLoginService socialLoginService;

	// 유저 생성 컨트롤러
	@PostMapping("/join")
	public ResponseEntity<Void> createUserHandle(JoinUserRequest dto) throws ExistUserException, CertifyFailException {

		userService.createUser(dto);

		return new ResponseEntity<Void>(HttpStatus.OK);

	}

	// 이메일 중복체크 호출 컨트롤러
	@GetMapping("/available")
	public ResponseEntity<Void> availableEmailHandle(CertifyEmailRequest dto) throws ExistUserException {

		userService.availableEmail(dto);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	// 이메일에 인증코드 전송과 데이터 저장
	@PostMapping("/certify-email")
	public ResponseEntity<CertifyResponse> certifyCodeHandle(CertifyEmailRequest dto) throws AlreadyCertifyException {
		CertifyResponse response = mailService.sendCertifyCode(dto);

		return new ResponseEntity<CertifyResponse>(response, HttpStatus.OK);
	}

	@PatchMapping("/certify-email")
	public ResponseEntity<CertifyResponse> verifyCodeHandle(@Valid CertifyCodeRequest req) throws CertifyFailException {

		CertifyResponse response = mailService.certifySpecificCode(req);

		return new ResponseEntity<CertifyResponse>(response, HttpStatus.OK);
	}

	@PostMapping("/validate")
	public ResponseEntity<ValidateUserResponse> validateHandle(@Valid ValidateUserRequest req)
			throws NotExistUserException, ErrorPasswordException {

		userService.validateUser(req);

		String token = jwtService.createToken(req.getEmail());

		ValidateUserResponse response = new ValidateUserResponse(200, token, "토큰 발급 완료");

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Void> deleteUserHandle(@AuthenticationPrincipal String principal, DeleteUserRequest req)
			throws NotExistUserException, ErrorPasswordException, NotExistPostException, NotExistReplyException {
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

}
