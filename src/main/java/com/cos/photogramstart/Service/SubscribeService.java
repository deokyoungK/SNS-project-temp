package com.cos.photogramstart.Service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {

	private final SubscribeRepository subscribeRepository;
	
	@Transactional
	public void 구독하기(int fromUserId , int toUserId) {
		try {
			subscribeRepository.mySubscribe(fromUserId, toUserId);
		}catch(Exception e) {
			throw new CustomApiException("이미 구독을 했습니다.");
		}

	}
	
	
	@Transactional
	public void 구독취소(int fromUserId , int toUserId) {
		subscribeRepository.myUnSubscribe(fromUserId, toUserId);
	}

	
	
}
