package com.company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.model.entity.Reply;
import com.company.model.entity.ReplyRecommend;

public interface ReplyRecommendRepository extends JpaRepository<ReplyRecommend, Integer> {

	List<ReplyRecommend> findByRepliesId(Reply replyId);

}
