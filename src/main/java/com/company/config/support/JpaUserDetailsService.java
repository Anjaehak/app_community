package com.company.config.support;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.company.model.entity.User;
import com.company.repository.UserRepository;

import jakarta.transaction.Transactional;

@Component
public class JpaUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByEmail(username);
		if (user.isPresent()) {
			User got = user.get();
			got.getAuthority();

			return new AppUser(got);
		} else {
			throw new UsernameNotFoundException(username + "이(가) 존재하지 않습니다.");
		}
	}

}
