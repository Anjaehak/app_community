package com.company.model.dto.post.request;

import lombok.Data;

@Data
public class LikeRequest {

	private Integer postId;
	private Integer replyId;
}
