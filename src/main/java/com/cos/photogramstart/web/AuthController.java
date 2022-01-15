package com.cos.photogramstart.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.Service.AuthService;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.web.dto.auth.SignupDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final을 DI 할때 사용
@Controller
public class AuthController {
	
	
	private final AuthService authService;
	
	@GetMapping("/auth/signin")
	public String signinForm() {
		return "auth/signin";
	}
	
	@GetMapping("/auth/signup")
	public String signupForm() {
		return "auth/signup";
	}

	@PostMapping("/auth/signup")
	public String signup(SignupDto signupDto) { 
		User user = signupDto.toEntity();
		authService.회원가입(user);
		return "auth/signin";
	}
	
	
	
	
	
	
	
}
