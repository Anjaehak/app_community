package com.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.model.entity.Post;
import com.company.model.entity.Recommend;
import com.company.model.entity.Reply;

import java.util.List;

public interface RecommendRepository extends JpaRepository<Recommend, Integer> {

	List<Recommend> findByPostsId(Post postsId);

	List<Recommend> findByRepliesId(Reply repliesId);
}
