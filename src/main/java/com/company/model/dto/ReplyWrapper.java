package com.company.model.dto;

import java.util.List;

import com.company.model.entity.Reply;

import lombok.Data;

@Data
public class ReplyWrapper {

	Integer id;

	List<ReReplyWrapper> reReply;

	public ReplyWrapper(Reply entity) {
		this.id = entity.getId();
	}
}
