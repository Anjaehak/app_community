package com.company.model.dto.reply.request;

import lombok.Data;

@Data
public class ReplyLikeRequest {
	
	private Integer postId;
	private Integer replyId;
	private Integer parentId;
	
	

}
