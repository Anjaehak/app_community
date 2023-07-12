package com.company.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.company.model.entity.Recommend;
import com.company.model.entity.Post;
import com.company.model.entity.User;

public interface RecommendRepository extends JpaRepository<Recommend, Integer> {
	@Query("")
	Optional<Recommend> findByUserIdAndPostId(User userId, Post postId);

}
