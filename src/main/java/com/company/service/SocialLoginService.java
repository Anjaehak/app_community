package com.company.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.company.exception.NotExistUserException;
import com.company.model.dto.SocialAccount;
import com.company.model.dto.OAuth.request.ValidateNaverRequest;
import com.company.model.dto.OAuth.response.OAuthAccessTokenWrapper;
import com.company.model.entity.User;
import com.company.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SocialLoginService {

	@Value("${kakao.restapi.key}")
	String kakaoKey;

	@Value("${kakao.redirect.uri}")
	String kakaoUri;

	@Value("${naver.restapi.id}")
	String naverId;

	@Value("${naver.restapi.key}")
	String naverKey;

	private final UserRepository userRepository;

	public OAuthAccessTokenWrapper getKakaoAccessToken(String code) {

		String tokenURL = "https://kauth.kakao.com/oauth/token";

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
		body.add("client_id", kakaoKey);
		body.add("redirect_uri", kakaoUri);
		body.add("code", code);

		RequestEntity<MultiValueMap<String, String>> request = new RequestEntity<>(body, headers, HttpMethod.POST,
				URI.create(tokenURL));

		RestTemplate template = new RestTemplate();

		ResponseEntity<OAuthAccessTokenWrapper> response = template.exchange(request, OAuthAccessTokenWrapper.class);

		return response.getBody();
	}

	public SocialAccount getKakaoUserInfo(String accessToken) throws JsonMappingException, JsonProcessingException {

		String url = "https://kapi.kakao.com/v2/user/me";

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", "Bearer " + accessToken);
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		RequestEntity<Void> request = new RequestEntity<>(headers, HttpMethod.GET, URI.create(url));

		RestTemplate template = new RestTemplate();
		ResponseEntity<String> response = template.exchange(request, String.class);

		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(response.getBody());

		String email = node.get("id").asText("") + "@kakao.user.social";
		String nickname = node.get("kakao_account").get("profile").get("nickname").asText();

		return new SocialAccount(email, nickname);
	}

	public void unlinkKakao(String tokenEmailValue) throws NotExistUserException {
		User found = userRepository.findByEmail(tokenEmailValue).orElseThrow(() -> new NotExistUserException());

		String apiAddress = "https://kapi.kakao.com/v1/user/unlink";

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", "Bearer " + found.getSocialToken());

		RestTemplate template = new RestTemplate();

		RequestEntity<Void> request = new RequestEntity<>(headers, HttpMethod.POST, URI.create(apiAddress));
		template.exchange(request, String.class);

	}

	public OAuthAccessTokenWrapper getNaverAccessToken(ValidateNaverRequest data) {

		String tokenURL = "https://nid.naver.com/oauth2.0/token";

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
		body.add("client_id", naverId);
		body.add("client_secret", naverKey);
		body.add("code", data.getCode());
		body.add("state", data.getState());

		RequestEntity<MultiValueMap<String, String>> request = new RequestEntity<>(body, headers, HttpMethod.POST,
				URI.create(tokenURL));

		RestTemplate template = new RestTemplate();

		ResponseEntity<OAuthAccessTokenWrapper> response = template.exchange(request, OAuthAccessTokenWrapper.class);

		return response.getBody();
	}

	public SocialAccount getNaverUserInfo(String accessToken) throws JsonMappingException, JsonProcessingException {

		String url = "https://openapi.naver.com/v1/nid/me";

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", "Bearer " + accessToken);
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		RequestEntity<Void> request = new RequestEntity<>(headers, HttpMethod.GET, URI.create(url));

		RestTemplate template = new RestTemplate();
		ResponseEntity<String> response = template.exchange(request, String.class);

		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(response.getBody());

		String email = node.get("response").get("id").asText("") + "@naver.user.social";
		String nickname = node.get("response").get("nickname").asText();

		return new SocialAccount(email, nickname);
	}

	public void unlinkNaver(String tokenEmailValue) throws NotExistUserException {
		User found = userRepository.findByEmail(tokenEmailValue).orElseThrow(() -> new NotExistUserException());

		String apiAddress = "https://nid.naver.com/oauth2.0/token";

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "delete");
		body.add("client_id", naverId);
		body.add("client_secret", naverKey);
		body.add("access_token", found.getSocialToken());

		RestTemplate template = new RestTemplate();

		RequestEntity<MultiValueMap<String, String>> request = new RequestEntity<>(body, HttpMethod.POST,
				URI.create(apiAddress));

		template.exchange(request, String.class);

	}

}
