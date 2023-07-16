package com.company.model.dto.post.response;

import java.time.LocalDateTime;
import java.util.List;

import com.company.model.entity.Image;
import com.company.model.entity.Reply;
import com.company.model.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
	private Integer id;

	private String title;

	private User postWriter;

	private String postContent;
	
	private LocalDateTime postDate;
	
	private List<Reply> replies;
	
	private List<Image> images;

}
