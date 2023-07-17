package com.company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.model.entity.Image;
import com.company.model.entity.Post;

public interface ImageRepository extends JpaRepository<Image, Integer> {

	List<Image> findByPostsId(Post postId);

}
