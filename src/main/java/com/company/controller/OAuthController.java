package com.company.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.model.dto.ChatWrapper;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/app_community/v1/oauth")
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

	// 카카오 소셜 로그인
	@Operation(summary = "카카오 소셜 로그인", description = "카카오 로그인 인가코드 발급 주소를 제공하는 화면을 띄워줍니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "카카오 로그인 화면 불러오기 성공", content = @Content(schema = @Schema(implementation = OAuthSignResponse.class))) })
	@GetMapping("/kakao")
	public ResponseEntity<OAuthSignResponse> kakaoLoginHandle() {

		OAuthSignResponse response = new OAuthSignResponse(200,
				"https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + kakaoKey + "&redirect_uri="
						+ kakaoUri);

		return new ResponseEntity<OAuthSignResponse>(response, HttpStatus.OK);
	}

	// 발급 받은 카카오 로그인 인가코드로 유저 정보 가져와서 user 데이터베이스에 등록
	@Operation(summary = "카카오 로그인 회원 DB에 저장", description = "발급 받은 카카오 로그인 인가코드로 유저 정보 가져와서 user 데이터베이스에 등록합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "카카오 로그인 유저 등록 성공", content = @Content(schema = @Schema(implementation = ValidateUserResponse.class))), })
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

	// 네이버 소셜로그인
	@Operation(summary = "네이버 소셜 로그인", description = "네이버 로그인 인가코드 발급 주소를 제공하는 화면을 띄워줍니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "네이버 로그인 화면 불러오기 성공", content = @Content(schema = @Schema(implementation = OAuthSignResponse.class))) })
	@GetMapping("/naver")
	public ResponseEntity<OAuthSignResponse> naverLoginHandle() {

		OAuthSignResponse response = new OAuthSignResponse(200,
				"https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=" + naverId
						+ "&state=appComunity&redirect_uri=" + naverUri);

		return new ResponseEntity<OAuthSignResponse>(response, HttpStatus.OK);
	}

	// 발급 받은 네이버 로그인 인가코드로 유저 정보를 가져와서 user 데이터베이스에 등록
	@Operation(summary = "카카오 로그인 회원 DB에 저장", description = "발급 받은 네이버 로그인 인가코드로 유저 정보 가져와서 user 데이터베이스에 등록합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "네이버 로그인 유저 등록 성공", content = @Content(schema = @Schema(implementation = OAuthSignResponse.class))) })
	@GetMapping("/naver/token")
	public ResponseEntity<ValidateUserResponse> naverLoginTokenHandle(ValidateNaverRequest req)
			throws JsonMappingException, JsonProcessingException {

		OAuthAccessTokenWrapper wrapper = socialLoginService.getNaverAccessToken(req);
		SocialAccount userInfo = socialLoginService.getNaverUserInfo(wrapper.getAccessToken());

		userService.createSocialUser(userInfo, wrapper.getAccessToken());

		String token = jwtService.createToken(userInfo.getEmail());

		ValidateUserResponse response = new ValidateUserResponse(200, token, userInfo.getEmail());

		return new ResponseEntity<ValidateUserResponse>(response, HttpStatus.OK);
	}
}
