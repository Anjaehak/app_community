package com.company.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.company.model.dto.UserWrapper;
import com.company.model.dto.post.request.CreatePostRequest;
import com.company.model.dto.post.request.UpdatePostRequest;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "posts")
@Data
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String title;

	@ManyToOne
	@JoinColumn(name = "post_writer")
	private User postWriter;

	private String postContent;
	private LocalDateTime postDate;
	private int views;

	// 글의 댓글 모음
	@OneToMany(mappedBy = "postsId")
	private List<Reply> replies;

	// 글의 사진 모음 (??)
	@OneToMany(mappedBy = "postsId")
	private List<Image> images;

	@PrePersist
	public void prePersist() {
		this.postDate = LocalDateTime.now();
	}

	public Post() {
		super();
	}

	public Post(Post post) {
		super();
		this.id = post.getId();
		this.title = post.getTitle();
		this.postWriter = post.getPostWriter();
		this.postContent = post.getPostContent();
		this.postDate = post.getPostDate();
		this.replies = post.getReplies();
		this.views = post.getViews() + 1;
	}

	public Post(CreatePostRequest req, User user) {
		this.title = req.getTitle();
		this.postWriter = user;
		this.postContent = req.getPostContent();
		this.postDate = LocalDateTime.now();
		this.views = 0;

	}

	public Post(Post post, UpdatePostRequest req) {
		this.id = req.getId();
		this.title = req.getTitle();
		this.postWriter = post.getPostWriter();
		this.postContent = req.getPostContent();
		this.postDate = LocalDateTime.now();
		this.views = post.getViews();

	}
}