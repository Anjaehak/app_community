package com.company.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JWTService {

	@Value("${jwt.secret.key}")
	String key;

	public String createToken(String email) {
		Algorithm algorithm = Algorithm.HMAC256(key);

		return JWT.create().withIssuer("app_community").withIssuedAt(new Date(System.currentTimeMillis()))
				.withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 10)).withClaim("email", email)
				.sign(algorithm);
	}

	public String certifyToken(String token) {
		Algorithm algorithm = Algorithm.HMAC256(key);
		var verifier = JWT.require(algorithm).build();
		DecodedJWT decodedJWT = verifier.verify(token);

		return decodedJWT.getClaim("email").asString();
	}
}
