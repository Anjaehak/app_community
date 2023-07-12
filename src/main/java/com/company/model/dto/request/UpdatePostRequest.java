package com.company.model.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UpdatePostRequest {

	private String cate;
	private String postContent;
	private LocalDateTime postDate;
	private String title;
	private String writer;
	private List<MultipartFile> attaches;
	private Integer id;

}
