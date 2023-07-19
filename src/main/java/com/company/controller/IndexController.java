package com.company.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Controller
public class IndexController {
	// 유저 생성 컨트롤러
	@Operation(summary = "스웨거", description = "스웨거경로로 이동하기 위한 redirectController")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "스웨거 호출 성공", content = @Content(schema = @Schema(implementation = Void.class))) })
	@RequestMapping("/index")
	public String gotoSwaggerUI() {
		return "redirect:/swagger-ui/index.html";
	}
}
