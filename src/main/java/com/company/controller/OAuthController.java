package com.company.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.model.dto.SocialAccount;
import com.company.model.dto.OAuth.request.ValidateKakaoRequest;
import com.company.model.dto.OAuth.request.ValidateNaverRequest;
import com.company.model.dto.OAuth.response.OAuthAccessTokenWrapper;
import com.company.model.dto.OAuth.response.OAuthSignResponse;
import com.company.model.dto.user.response.ValidateUserResponse;
import com.company.service.JWTService;
import com.company.service.SocialLoginService;
import com.company.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/app_comunity/v1/oauth")
@RequiredArgsConstructor
@CrossOrigin
public class OAuthController {

	@Value("${kakao.restapi.key}")
	String kakaoKey;

	@Value("${kakao.redirect.uri}")
	String kakaoUri;

	@Value("${naver.restapi.id}")
	String naverId;

	@Value("${naver.restapi.uri}")
	String naverUri;

	private final SocialLoginService socialLoginService;
	private final JWTService jwtService;
	private final UserService userService;

	@GetMapping("/kakao")
	public ResponseEntity<OAuthSignResponse> kakaoLoginHandle() {

		OAuthSignResponse response = new OAuthSignResponse(200,
				"https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + kakaoKey + "&redirect_uri="
						+ kakaoUri);

		return new ResponseEntity<OAuthSignResponse>(response, HttpStatus.OK);
	}

	@GetMapping("/kakao/token")
	public ResponseEntity<ValidateUserResponse> kakaoLoginTokenHandle(ValidateKakaoRequest req)
			throws JsonMappingException, JsonProcessingException {

		OAuthAccessTokenWrapper wrapper = socialLoginService.getKakaoAccessToken(req.getCode());
		SocialAccount userInfo = socialLoginService.getKakaoUserInfo(wrapper.getAccessToken());

		userService.createSocialUser(userInfo, wrapper.getAccessToken());

		String token = jwtService.createToken(userInfo.getEmail());

		ValidateUserResponse response = new ValidateUserResponse(200, token, userInfo.getEmail());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/naver")
	public ResponseEntity<OAuthSignResponse> naverLoginHandle() {

		OAuthSignResponse response = new OAuthSignResponse(200,
				"https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=" + naverId
						+ "&state=appComunity&redirect_uri=" + naverUri);

		return new ResponseEntity<OAuthSignResponse>(response, HttpStatus.OK);
	}

	@GetMapping("/naver/token")
	public ResponseEntity<?> test(ValidateNaverRequest req) throws JsonMappingException, JsonProcessingException {

		OAuthAccessTokenWrapper wrapper = socialLoginService.getNaverAccessToken(req);
		SocialAccount userInfo = socialLoginService.getNaverUserInfo(wrapper.getAccessToken());

		userService.createSocialUser(userInfo, wrapper.getAccessToken());

		String token = jwtService.createToken(userInfo.getEmail());

		ValidateUserResponse response = new ValidateUserResponse(200, token, userInfo.getEmail());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
