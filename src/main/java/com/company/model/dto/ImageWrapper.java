package com.company.model.dto;

import com.company.model.entity.Image;
import com.company.model.entity.Post;

import lombok.Data;

@Data
public class ImageWrapper {

	private Integer id;

	private PostWrapper postsId;

	private String imageUrl;

	public ImageWrapper(Image entity) {
		this.id = entity.getId();
		this.postsId = new PostWrapper(entity.getPostsId());
		this.imageUrl = entity.getImageUrl();

	}

}
