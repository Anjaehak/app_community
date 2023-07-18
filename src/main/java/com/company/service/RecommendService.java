package com.company.service;

import org.springframework.stereotype.Service;

import com.company.exception.NoRecommedException;
import com.company.exception.NotExistPostException;
import com.company.exception.NotExistReplyException;
import com.company.exception.NotExistUserException;
import com.company.model.dto.post.request.LikeRequest;
import com.company.model.entity.Post;
import com.company.model.entity.PostRecommend;
import com.company.model.entity.Reply;
import com.company.model.entity.ReplyRecommend;
import com.company.model.entity.User;
import com.company.repository.PostRecommendRepository;
import com.company.repository.PostRepository;
import com.company.repository.ReplyRecommendRepository;
import com.company.repository.ReplyRepository;
import com.company.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecommendService {

	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final ReplyRepository replyRepository;
	private final PostRecommendRepository postRecommendRepository;
	private final ReplyRecommendRepository replyRecommendRepository;

	// 게시글, 댓글 좋아요
	public void createRecommend(String email, LikeRequest req)
			throws NotExistUserException, NotExistPostException, NotExistReplyException {

		User user = userRepository.findByEmail(email).orElseThrow(() -> new NotExistUserException());

		if (req.getReplyId() == null) {
			PostRecommend recommend = new PostRecommend();

			Post post = postRepository.findById(req.getPostId()).orElseThrow(() -> new NotExistPostException());

			recommend = PostRecommend.builder().postsId(post).usersId(user).build();

			postRecommendRepository.save(recommend);

		} else {
			ReplyRecommend recommend = new ReplyRecommend();

			Reply reply = replyRepository.findById(req.getReplyId()).orElseThrow(() -> new NotExistPostException());

			recommend = ReplyRecommend.builder().repliesId(reply).usersId(user).build();

			replyRecommendRepository.save(recommend);

		}

	}

	// 게시글 좋아요 취소
	public void deleteRecommend(String principal, LikeRequest req)
			throws NotExistPostException, NotExistUserException, NoRecommedException, NotExistReplyException {
		User user = userRepository.findByEmail(principal).orElseThrow(() -> new NotExistUserException());

		if (req.getReplyId() == null) {
			Post post = postRepository.findById(req.getPostId()).orElseThrow(() -> new NotExistPostException());

			PostRecommend recommend = postRecommendRepository.findByPostsIdAndUsersId(post, user)
					.orElseThrow(() -> new NoRecommedException());

			postRecommendRepository.delete(recommend);
		} else {
			Reply reply = replyRepository.findById(req.getReplyId()).orElseThrow(() -> new NotExistReplyException());

			ReplyRecommend recommend = replyRecommendRepository.findByRepliesIdAndUsersId(reply, user)
					.orElseThrow(() -> new NoRecommedException());

			replyRecommendRepository.delete(recommend);
		}

	}
}
