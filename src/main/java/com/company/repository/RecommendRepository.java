package com.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.model.entity.Post;
import com.company.model.entity.Recommend;
import com.company.model.entity.Reply;
import com.company.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface RecommendRepository extends JpaRepository<Recommend, Integer> {

	List<Recommend> findByPostsId(Post postsId);

	List<Recommend> findByRepliesId(Reply repliesId);

	Optional<Recommend> findByRepliesIdAndUsersId(Reply repliesId, User usersId);

	Optional<Recommend> findByPostsIdAndUsersId(Post postId, User usersId);
}
