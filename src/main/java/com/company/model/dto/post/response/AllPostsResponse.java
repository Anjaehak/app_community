package com.company.model.dto.post.response;

import java.util.List;

import com.company.model.dto.PostWrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllPostsResponse {

	private Long total;
	
	private List<PostWrapper> postLi;
}
