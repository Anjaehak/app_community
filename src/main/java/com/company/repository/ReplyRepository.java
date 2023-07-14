package com.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.model.entity.Post;
import com.company.model.entity.Reply;
import java.util.List;


public interface ReplyRepository extends JpaRepository<Reply, Integer> {

	List<Reply> findByPostsId(Post postsId);
}
