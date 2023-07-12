package com.company.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.exception.CertifyCodeException;
import com.company.exception.ExistUserException;
import com.company.exception.UnequalPassException;
import com.company.model.dto.SocialAccount;
import com.company.model.dto.request.CertifyCodeRequest;
import com.company.model.dto.request.CertifyEmailRequestDTO;
import com.company.model.dto.request.CreateUserRequestDTO;
import com.company.model.dto.request.ValidateUserRequest;
import com.company.model.entity.Certify;
import com.company.model.entity.User;
import com.company.repository.CertifyRepository;
import com.company.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	private final CertifyRepository certifyRepository;

	@Transactional
	public void createUser(CreateUserRequestDTO dto) throws ExistUserException, CertifyCodeException {

		if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
			throw new ExistUserException();
		}

		Certify data = certifyRepository.findByEmail(dto.getEmail())
				.orElseThrow(() -> new CertifyCodeException("인증 기록이 존재하지 않습니다"));

		if (data.getStatus().equals("N")) {
			throw new CertifyCodeException("인증에 실패한 유저입니다 재인증을 받아주세요");
		}
		User user = User.builder().email(dto.getEmail()).password(dto.getPass()).nick(dto.getNick())
				.userImage(dto.getUserImage()).authority("4").build();

		userRepository.save(user);
	}

	@Transactional
	public void availableEmail(@Valid CertifyEmailRequestDTO dto) throws ExistUserException {

		if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
			throw new ExistUserException();
		}

	}

	@Transactional
	public void certifySpecificCode(@Valid CertifyCodeRequest req) throws CertifyCodeException {

		Certify data = certifyRepository.findByEmail(req.getEmail())
				.orElseThrow(() -> new CertifyCodeException("인증코드 발급받은 적이 없습니다."));

		if (!data.getCode().equals(req.getCode())) {
			throw new CertifyCodeException("인증코드가 일치하지 않습니다.");
		}

		data.setStatus("Y");

		certifyRepository.save(data);

	}

	public void validateUser(@Valid ValidateUserRequest req) throws ExistUserException, UnequalPassException {
		User user = userRepository.findByEmail(req.getEmail()).orElseThrow(() -> new ExistUserException());

		if (!user.getPassword().equals(req.getPass())) {
			throw new UnequalPassException();
		}

	}

	@Transactional
	public void createSocialUser(SocialAccount account, String accessToken) {
		userRepository.findByEmail(account.getEmail());

	}
}
