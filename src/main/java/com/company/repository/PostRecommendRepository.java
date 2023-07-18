package com.company.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.model.entity.Post;
import com.company.model.entity.PostRecommend;
import com.company.model.entity.User;

public interface PostRecommendRepository extends JpaRepository<PostRecommend, Integer> {

	Optional<PostRecommend> findByPostsIdAndUsersId(Post post, User user);

	List<PostRecommend> findByPostsId(Post post);
}
