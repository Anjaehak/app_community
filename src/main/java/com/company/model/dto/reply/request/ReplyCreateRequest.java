package com.company.model.dto.reply.request;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ReplyCreateRequest {
	
	private Integer postId;
	private Integer parentId;
	private String replyContent;
	private LocalDateTime replyDate;
	private Integer userId;
	
	

}
