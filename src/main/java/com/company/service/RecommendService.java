package com.company.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.company.exception.NoRecommedException;
import com.company.exception.NotExistPostException;
import com.company.exception.NotExistReplyException;
import com.company.exception.NotExistUserException;
import com.company.model.dto.post.request.LikeRequest;
import com.company.model.dto.reply.request.ReplyLikeRequest;
import com.company.model.entity.Post;
import com.company.model.entity.Recommend;
import com.company.model.entity.Reply;
import com.company.model.entity.User;
import com.company.repository.PostRepository;
import com.company.repository.RecommendRepository;
import com.company.repository.ReplyRepository;
import com.company.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecommendService {

	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final ReplyRepository replyRepository;
	private final RecommendRepository recommendRepository;

	// 게시글 좋아요
	public void recommendPost(String email, LikeRequest req)
			throws NotExistUserException, NotExistPostException, NotExistReplyException {

		User user = userRepository.findByEmail(email).orElseThrow(() -> new NotExistUserException());

		Recommend recommend = new Recommend();

		if (req.getReplyId() == null) {
			Post post = postRepository.findById(req.getPostId()).orElseThrow(() -> new NotExistPostException());

			recommend = Recommend.builder().postsId(post).usersId(user).build();
		} else {
			Reply reply = replyRepository.findById(req.getReplyId()).orElseThrow(() -> new NotExistReplyException());

			recommend = Recommend.builder().repliesId(reply).usersId(user).build();
		}

		recommendRepository.save(recommend);

	}

	// 게시글 좋아요 취소
	public void unRecommendPost(String principal, LikeRequest req)
			throws NotExistPostException, NotExistUserException, NoRecommedException, NotExistReplyException {
		User user = userRepository.findByEmail(principal).orElseThrow(() -> new NotExistUserException());

		Recommend recommend = new Recommend();

		if (req.getReplyId() == null) {
			Post post = postRepository.findById(req.getPostId()).orElseThrow(() -> new NotExistPostException());

			recommend = recommendRepository.findByPostsIdAndUsersId(post, user)
					.orElseThrow(() -> new NoRecommedException());
		} else {
			Reply reply = replyRepository.findById(req.getReplyId()).orElseThrow(() -> new NotExistReplyException());

			recommend = recommendRepository.findByRepliesIdAndUsersId(reply, user)
					.orElseThrow(() -> new NoRecommedException());
		}

		recommendRepository.delete(recommend);

	}
}
