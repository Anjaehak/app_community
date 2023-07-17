package com.company.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.company.model.entity.Post;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostWrapper {

	private Integer id;
	private String title;
	private String postWriter;
	private String postContent;
	private LocalDateTime postDate;
	private List<ReplyWrapper> replyLi;
	private int views;
	private int recommendCnt;

	public PostWrapper(Post entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.postWriter = entity.getPostWriter().getNick();
		this.postContent = entity.getPostContent();
		this.postDate = entity.getPostDate();
	}

	public PostWrapper(Post entity, int recommendCnt) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.postWriter = entity.getPostWriter().getNick();
		this.postContent = entity.getPostContent();
		this.postDate = entity.getPostDate();
		this.views = entity.getViews();
		this.recommendCnt = recommendCnt;

	}

	public PostWrapper(Post entity, List<ReplyWrapper> replyLi, int recommendCnt) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.postWriter = entity.getPostWriter().getNick();
		this.postContent = entity.getPostContent();
		this.postDate = entity.getPostDate();
		this.replyLi = replyLi;
		this.views = entity.getViews();
		this.recommendCnt = recommendCnt;
	}

}
