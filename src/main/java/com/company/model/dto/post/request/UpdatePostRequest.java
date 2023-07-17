package com.company.model.dto.post.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UpdatePostRequest {

	private Integer id;
	private String title;
	private String postContent;
	private List<MultipartFile> attaches;

}
