package com.company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.model.entity.Post;
import com.company.model.entity.Reply;


public interface ReplyRepository extends JpaRepository<Reply, Integer> {

	List<Reply> findByPostsId(Post postId);

}
