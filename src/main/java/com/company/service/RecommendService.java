package com.company.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.company.exception.NoRecommedException;
import com.company.exception.NotExistPostException;
import com.company.exception.NotExistReplyException;
import com.company.exception.NotExistUserException;
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

	// 게시글에 좋아요 하기
	public void recommendPost(String email, Integer postNumber) throws NotExistUserException, NotExistPostException {

		User user = userRepository.findByEmail(email).orElseThrow(() -> new NotExistUserException());
		Post post = postRepository.findById(postNumber).orElseThrow(() -> new NotExistPostException());

		Recommend recommend = Recommend.builder().usersId(user).postsId(post).build();
		recommendRepository.save(recommend);

	}

	// 게시글에 좋아요 취소
	public void unRecommendPost(String principal, Integer postId) throws NotExistPostException, NotExistUserException {
		User user = userRepository.findByEmail(principal).orElseThrow(() -> new NotExistUserException());
		Post post = postRepository.findById(postId).orElseThrow(() -> new NotExistPostException());
		// 게시글 Id를 넘겨줘서 그 게시글에 좋아요를 찾는다
		List<Recommend> recommends = recommendRepository.findByPostsId(post);
		// 좋아요 중에서 userId를 통해서 같은 유저인지 확인해서 지우고 종료한다
		for (Recommend recommend : recommends) {
			if (recommend.getUsersId().getId().equals(user.getId())) {
				recommendRepository.delete(recommend);
				return;
			} else {
				new NoRecommedException();
			}
		}
	}

	// 댓글 대댓글 좋아요 //어렵구만
	public void recommendReply(String principal, ReplyLikeRequest req)
			throws NotExistPostException, NotExistReplyException, NotExistUserException {
		User user = userRepository.findByEmail(principal).orElseThrow(() -> new NotExistUserException());

		Post post = postRepository.findById(req.getPostId()).orElseThrow(() -> new NotExistPostException());

		Reply reply = replyRepository.findById(req.getReplyId()).orElseThrow(() -> new NotExistReplyException());

		Recommend recommend = Recommend.builder().postsId(post).repliesId(reply).usersId(user).build();

		recommendRepository.save(recommend);

	}

	public void UnRecommendReply(String principal, ReplyLikeRequest req)
			throws NotExistUserException, NotExistPostException, NotExistReplyException {

		User user = userRepository.findByEmail(principal).orElseThrow(() -> new NotExistUserException());

		Post post = postRepository.findById(req.getPostId()).orElseThrow(() -> new NotExistPostException());

		Reply reply = replyRepository.findById(req.getReplyId()).orElseThrow(() -> new NotExistReplyException());

		Recommend recommend = recommendRepository.findByPostsIdAndRepliesId(post, reply);

		if (recommend.getUsersId().getId() == user.getId()) {
			recommendRepository.delete(recommend);
		}else {
			//니가한 좋아요가 아니다
		}

	}

}
