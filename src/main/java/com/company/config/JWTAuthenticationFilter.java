package com.company.config;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.company.service.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	private final JWTService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authorization = request.getHeader("Authorization");

		if (authorization == null) {
			filterChain.doFilter(request, response);
			return;

		}
		try {
			// JWT 유효성검사 해서 통과하면
			String email = jwtService.certifyToken(authorization);

			Authentication authentication = new UsernamePasswordAuthenticationToken(email, authorization,
					List.of(new SimpleGrantedAuthority("ROLE_MEMBER")));
		} catch (Exception e) {
			throw new BadCredentialsException("Invalid authentication token");
		}
		filterChain.doFilter(request, response);
	}
}
