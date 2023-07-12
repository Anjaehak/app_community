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

import com.company.model.dto.KakaoAccessTokenWrapper;
import com.company.model.dto.SocialAccount;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SocialLoginService {

	@Value("${kakao.restapi.key}")
	String kakaoKey;

	@Value("${kakao.redirect.url}")
	String kakaoUrl;

	public KakaoAccessTokenWrapper getKakaoAccessToken(String code) {

		String tokenURL = "https://kauth.kakao.com/oauth/token";

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
		body.add("client_id", kakaoKey);
		body.add("redirect_uri", kakaoUrl);
		body.add("code", code);

		RequestEntity<MultiValueMap<String, String>> request = new RequestEntity<>(body, headers, HttpMethod.POST,
				URI.create(tokenURL));

		RestTemplate template = new RestTemplate();

		ResponseEntity<KakaoAccessTokenWrapper> response = template.exchange(request, KakaoAccessTokenWrapper.class);

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

		String email = node.get("id").asText("") + "@kakao.user";
		String nickname = node.get("kakao_account").get("profile").get("nickname").asText();
		String profileImage = node.get("kakao_account").get("profile").get("profile_image_url").asText();

		return new SocialAccount(email, nickname, profileImage);
	}

}
