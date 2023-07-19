package com.company.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final JWTAuthenticationFilter authenticationFilter;

	@Bean
	SecurityFilterChain appCommunitySecurityChain(HttpSecurity http) throws Exception {

		http.csrf(t -> t.disable());
		http.sessionManagement(t -> t.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.authorizeHttpRequests(t -> t.requestMatchers("/api/user/**").permitAll()
				.requestMatchers("/api/user/delete-user/").authenticated().anyRequest().permitAll());
		http.anonymous(t -> t.disable());
		http.logout(t -> t.disable());
		http.addFilterBefore(authenticationFilter, AuthorizationFilter.class);
		http.cors(t -> t.disable());

		return http.build();
	}
}
