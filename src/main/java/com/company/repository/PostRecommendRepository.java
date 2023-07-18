package com.company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.model.entity.Post;
import com.company.model.entity.PostRecommend;

public interface PostRecommendRepository extends JpaRepository<PostRecommend, Integer> {

	List<PostRecommend> findByPostsId(Post postId);

}
