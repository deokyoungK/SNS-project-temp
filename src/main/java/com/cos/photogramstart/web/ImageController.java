package com.cos.photogramstart.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.photogramstart.config.auth.PrincipalDetail;

@Controller
public class ImageController {

	@GetMapping({"/","/image/story"})
	public String story(@AuthenticationPrincipal PrincipalDetail principalDetail) {
		System.out.println("세션 정보:" + principalDetail.getUser());
		return "image/story";
	}
	
	@GetMapping("/image/popular")
	public String popular() {
		return "image/popular";
	}
	
	@GetMapping("/image/upload")
	public String upload() {
		return "image/upload";
	}
}
