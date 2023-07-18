package com.company.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.model.entity.Reply;
import com.company.model.entity.ReplyRecommend;
import com.company.model.entity.User;

public interface ReplyRecommendRepository extends JpaRepository<ReplyRecommend, Integer> {

	Optional<ReplyRecommend> findByRepliesIdAndUsersId(Reply reply, User user);

	List<ReplyRecommend> findByRepliesId(Reply reply);

	void deleteAllByRepliesId(Reply reply);

}
