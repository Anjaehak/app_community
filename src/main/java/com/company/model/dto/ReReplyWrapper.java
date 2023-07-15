package com.company.model.dto;

import java.time.LocalDateTime;

import com.company.model.entity.Reply;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReReplyWrapper {

	private Integer id;
	private String reReplyWriter;
	private String reReplyContent;
	private LocalDateTime reReplyDate;
	private int recommendCnt;

	public ReReplyWrapper(Reply entity, int recommendCnt) {
		this.id = entity.getId();
		this.reReplyWriter = entity.getReplyWriter().getNick();
		this.reReplyContent = entity.getReplyContent();
		this.reReplyDate = entity.getReplyDate();
		this.recommendCnt = recommendCnt;
	}

}
