package com.company.model.entity;

import java.time.LocalDateTime;
import java.util.List;

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

	private int view;

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
}