package com.company.model.dto.post.request;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Persistent;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class CreatePostRequest {

	private String cate;
	private String postContent;
	private LocalDateTime postDate;
	private String title;
	private String writer;
	private List<MultipartFile> attaches;
}


