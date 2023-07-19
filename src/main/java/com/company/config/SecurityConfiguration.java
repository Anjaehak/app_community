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
		http.anonymous(t -> t.disable());
		http.logout(t -> t.disable());

		http.authorizeHttpRequests(t -> t
				.requestMatchers("/app_community/v1/user/**", "/app_community/v1/oauth/**", "/app_community/v1/index", "/swagger-ui/**",
						"/v3/api-docs/**").permitAll().anyRequest().authenticated());
		http.addFilterBefore(authenticationFilter, AuthorizationFilter.class);
		http.cors();

		return http.build();
	}
}
