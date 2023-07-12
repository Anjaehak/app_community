package com.company.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.company.exception.CertifyCodeException;
import com.company.exception.ExistUserException;
import com.company.exception.UnequalPassException;
import com.company.model.dto.request.CertifyCodeRequest;
import com.company.model.dto.request.CertifyEmailRequestDTO;
import com.company.model.dto.request.CreateUserRequestDTO;
import com.company.model.dto.request.ValidateUserRequest;
import com.company.model.dto.response.CertifyEmailResponse;
import com.company.model.dto.response.ValidateUserResponse;
import com.company.service.JWTService;
import com.company.service.MailService;
import com.company.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/app_cafe/v1/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

	private final UserService userService;

	private final MailService mailService;

	private final JWTService jwtService;

	@PostMapping("/create")
	public ResponseEntity<Void> createUserHandle(CreateUserRequestDTO dto)
			throws ExistUserException, CertifyCodeException {

		userService.createUser(dto);

		return new ResponseEntity<Void>(HttpStatus.OK);

	}

	@GetMapping("/available")
	public ResponseEntity<Void> availableEmailHandle(CertifyEmailRequestDTO dto) throws ExistUserException {

		userService.availableEmail(dto);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PostMapping("/certify-email")
	public ResponseEntity<CertifyEmailResponse> certifyCodeHandle(CertifyEmailRequestDTO dto) {
		mailService.sendCertifyCode(dto);

		CertifyEmailResponse response = new CertifyEmailResponse("이메일 인증코드가 정상 발급되어있습니다");

		return new ResponseEntity<CertifyEmailResponse>(response, HttpStatus.OK);
	}

	@PatchMapping("/certify-email")
	public ResponseEntity<Void> verifyCodeHandle(@Valid CertifyCodeRequest req) throws CertifyCodeException {

		userService.certifySpecificCode(req);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/validate")
	public ResponseEntity<ValidateUserResponse> validateHandle(@Valid ValidateUserRequest req)
			throws ExistUserException, UnequalPassException {

		userService.validateUser(req);

		String token = jwtService.createToken(req.getEmail());

		var response = new ValidateUserResponse(200, token, "토큰 발급 완료");

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
