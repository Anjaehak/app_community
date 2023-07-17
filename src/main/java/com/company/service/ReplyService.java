package com.company.service;

import org.springframework.stereotype.Service;

import com.company.exception.NotExistPostException;
import com.company.exception.NotExistUserException;
import com.company.model.dto.reply.request.ReplyCreateRequest;
import com.company.model.entity.Post;
import com.company.model.entity.Reply;
import com.company.model.entity.User;
import com.company.repository.PostRepository;
import com.company.repository.ReplyRepository;
import com.company.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyService {
	private final PostRepository postRepository;
	private final ReplyRepository replyRepository;
	private final UserRepository userRepository;

	// 특정글의 댓글생성
	public void specificPostReplyCreate(String principal, ReplyCreateRequest req)
			throws NotExistPostException, NotExistUserException {
		User user = userRepository.findByEmail(principal).orElseThrow(() -> new NotExistUserException());
		Post post = postRepository.findById(req.getPostId()).orElseThrow(() -> new NotExistPostException());

		if (req.getParentId() == null) {
			Reply reply = Reply.builder().parentId(0).postsId(post).replyContent(req.getReplyContent())
					.replyWriter(user).build();

			replyRepository.save(reply);
		} else {
			Reply reply = Reply.builder().parentId(req.getParentId()).postsId(post).replyContent(req.getReplyContent())
					.replyWriter(user).build();

			replyRepository.save(reply);
		}

	}

}
