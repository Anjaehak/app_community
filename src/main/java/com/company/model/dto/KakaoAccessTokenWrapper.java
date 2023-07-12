package com.company.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class KakaoAccessTokenWrapper {

	@JsonProperty("token_type")
	String tokenType;

	@JsonProperty("access_token")
	String accessToken;
}
