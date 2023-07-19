package com.company.config;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.company.service.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	private final JWTService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authorization = request.getHeader("Authorization");

		log.info("Authorization header value : {}", authorization);

		if (authorization == null) {
			filterChain.doFilter(request, response);
			return;

		}
		try {
			String email = jwtService.certifyToken(authorization);

			log.info("email : {}", email);

			Authentication authentication = new UsernamePasswordAuthenticationToken(email, authorization,
					List.of(new SimpleGrantedAuthority("4")));

			SecurityContextHolder.getContext().setAuthentication(authentication);

		} catch (Exception e) {
			throw new BadCredentialsException("Invalid authentication token");
		}
		filterChain.doFilter(request, response);
	}
}
