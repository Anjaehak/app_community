package com.company.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.company.exception.NotExistPostException;
import com.company.exception.NotExistUserException;
import com.company.model.dto.reply.request.ReplyCreateRequest;
import com.company.model.dto.reply.request.ReplyReadRequest;
import com.company.model.dto.reply.response.SpecificPostReplyResponse;
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

	// 게시글의 id로 reply을 가져온다
	public SpecificPostReplyResponse specificPostReplyRead(ReplyReadRequest req) throws NotExistPostException {
		Post post = postRepository.findById(req.getPostId()).orElseThrow(() -> new NotExistPostException());
		List<Reply> replies =post.getReplies();
		var comments = new SpecificPostReplyResponse(replies);
		return comments;
	}

	// 특정글의 댓글생성
	public void specificPostReplyCreate(String principal, ReplyCreateRequest req)
			throws NotExistPostException, NotExistUserException {
		User user = userRepository.findByEmail(principal).orElseThrow(() -> new NotExistUserException());
		Post post = postRepository.findById(req.getPostId()).orElseThrow(() -> new NotExistPostException());
		Reply reply = Reply.builder().parentId(0).postsId(post).replyContent(req.getReplyContent()).replyWriter(user)
				.build();
		replyRepository.save(reply);

	}

}
