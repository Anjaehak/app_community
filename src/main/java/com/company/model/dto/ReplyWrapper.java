package com.company.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.company.model.entity.Reply;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReplyWrapper {

	private Integer id;
	private String replyWriter;
	private String replyContent;
	private LocalDateTime replyDate;
	private List<ReReplyWrapper> reReplyLi;
	private int recommendCnt;

	public ReplyWrapper(Reply entity, List<ReReplyWrapper> reReplyLi, int recommendCnt) {
		this.id = entity.getId();
		this.replyWriter = entity.getReplyWriter().getNick();
		this.replyContent = entity.getReplyContent();
		this.replyDate = entity.getReplyDate();
		this.reReplyLi = reReplyLi;
		this.recommendCnt = recommendCnt;
	}
}
