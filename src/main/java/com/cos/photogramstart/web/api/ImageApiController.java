package com.cos.photogramstart.web.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.Service.ImageService;
import com.cos.photogramstart.config.auth.PrincipalDetail;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
public class ImageApiController {

	private final ImageService imageService;
	
	@GetMapping("/api/image")
	public ResponseEntity<?> imageStory(@AuthenticationPrincipal PrincipalDetail principalDetail,
			@PageableDefault(size=3) Pageable pageable){
		
		Page<Image> imageList = imageService.이미지불러오기(principalDetail.getUser().getId(), pageable);
		return new ResponseEntity<>(new CMRespDto<>(1,"성공",imageList),HttpStatus.OK);
	}

	
	
	
	
	
	
	
	
	
}
