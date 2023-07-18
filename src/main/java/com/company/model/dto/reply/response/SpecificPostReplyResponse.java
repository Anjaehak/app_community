package com.company.model.dto.reply.response;

import java.util.List;

import com.company.model.entity.Reply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecificPostReplyResponse {
	List<Reply> replies;
}
