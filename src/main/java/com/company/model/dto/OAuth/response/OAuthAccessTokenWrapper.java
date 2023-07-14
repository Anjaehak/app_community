package com.company.model.dto.OAuth.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class OAuthAccessTokenWrapper {

	@JsonProperty("token_type")
	String tokenType;

	@JsonProperty("access_token")
	String accessToken;
}
