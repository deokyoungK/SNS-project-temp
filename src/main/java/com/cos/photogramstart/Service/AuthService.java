package com.cos.photogramstart.Service;

import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service // IoC, 트랜잭션 관리 해줌
public class AuthService {

	private final UserRepository userRepository;
	
	public void 회원가입(User user) {
		user.setBio("hi");
		userRepository.save(user);
		
	}
	
}
