package com.company.model.dto;

import java.time.LocalDateTime;

import com.company.model.entity.Post;
import com.company.model.entity.Reply;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostWrapper {

	private Integer id;
	private String title;
	private UserWrapper postWriter;
	private String postContent;
	private LocalDateTime postDate;

	public PostWrapper(Post entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.postWriter = new UserWrapper(entity.getPostWriter());
		this.postContent = entity.getPostContent();
		this.postDate = entity.getPostDate();

	}

	public PostWrapper(Reply reply) {
		
	}
}
