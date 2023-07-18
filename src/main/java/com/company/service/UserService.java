package com.company.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.exception.CertifyFailException;
import com.company.exception.ErrorPasswordException;
import com.company.exception.ExistUserException;
import com.company.exception.NotExistPostException;
import com.company.exception.NotExistReplyException;
import com.company.exception.NotExistUserException;
import com.company.model.dto.SocialAccount;
import com.company.model.dto.post.request.PostDeleteRequest;
import com.company.model.dto.user.request.CertifyEmailRequest;
import com.company.model.dto.user.request.DeleteUserRequest;
import com.company.model.dto.user.request.JoinUserRequest;
import com.company.model.dto.user.request.ValidateUserRequest;
import com.company.model.entity.Certify;
import com.company.model.entity.Post;
import com.company.model.entity.User;
import com.company.repository.CertifyRepository;
import com.company.repository.ChatRepository;
import com.company.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	private final CertifyRepository certifyRepository;

	private final PostService postService;

	private final ChatRepository chatRepository;

	@Transactional
	public void createUser(JoinUserRequest dto) throws ExistUserException, CertifyFailException {

		if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
			throw new ExistUserException();
		}

		Certify data = certifyRepository.findByEmail(dto.getEmail())
				.orElseThrow(() -> new CertifyFailException("인증 기록이 존재하지 않습니다"));

		if (data.getStatus().equals("N")) {
			throw new CertifyFailException("인증에 실패한 유저입니다 재인증을 받아주세요");
		}
		User user = User.builder().email(dto.getEmail()).password(dto.getPassword()).nick(dto.getNick()).authority("4")
				.build();

		userRepository.save(user);
	}

	@Transactional
	public void availableEmail(@Valid CertifyEmailRequest dto) throws ExistUserException {

		if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
			throw new ExistUserException();
		}

	}

	public void validateUser(@Valid ValidateUserRequest req) throws ErrorPasswordException, NotExistUserException {
		User user = userRepository.findByEmail(req.getEmail()).orElseThrow(() -> new NotExistUserException());

		if (!user.getPassword().equals(req.getPass())) {
			throw new ErrorPasswordException("비밀번호가 올바르지않습니다");
		}

	}

	@Transactional
	public void createSocialUser(SocialAccount account, String accessToken) {
		Optional<User> data = userRepository.findByEmail(account.getEmail());
		if (data.isPresent()) {
			User saved = data.get();
			saved.setSocialToken(accessToken);
			userRepository.save(saved);
		} else {
			User user = new User();
			user.setEmail(account.getEmail());
			user.setNick(account.getNick());
			user.setSocialToken(accessToken);
			userRepository.save(user);
		}

	}

	@Transactional
	public void deleteSpecificUser(String principal, DeleteUserRequest req)
			throws NotExistUserException, ErrorPasswordException, NotExistPostException, NotExistReplyException {
		User user = userRepository.findByEmail(principal).orElseThrow(() -> new NotExistUserException());
		if (!user.getPassword().equals(req.getPassword())) {
			throw new ErrorPasswordException();
		}
		List<Post> postDatas = user.getPosts();
		for (Post post : postDatas) {
			postService.deleteSpecificPost(principal, new PostDeleteRequest(post.getId()));
		}
		chatRepository.deleteAllByUsersId(user);

		userRepository.deleteByEmail(principal);
		certifyRepository.deleteByEmail(principal);
	}

	public void deleteSpecificSocialUser(String principal)
			throws NotExistUserException, NotExistPostException, NotExistReplyException {
		User user = userRepository.findByEmail(principal).orElseThrow(() -> new NotExistUserException());
		List<Post> postDatas = user.getPosts();
		for (Post post : postDatas) {
			postService.deleteSpecificPost(principal, new PostDeleteRequest(post.getId()));
		}
		chatRepository.deleteAllByUsersId(user);

		userRepository.delete(user);
	}
}
