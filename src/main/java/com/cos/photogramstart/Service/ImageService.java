package com.cos.photogramstart.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.config.auth.PrincipalDetail;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

	private final ImageRepository imageRepository;
	
	@Value("${file.path}")
	private String uploadFolder;
	
	@Transactional
	public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetail principalDetail) {
		UUID uuid = UUID.randomUUID();
		String imageFileName = uuid+"_"+imageUploadDto.getFile().getOriginalFilename();
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);
		
		// 통신, I/O -> 예외가 발생할 수 있다.
		try {
			Files.write(imageFilePath,imageUploadDto.getFile().getBytes());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//image테이블에 저장
		Image image = imageUploadDto.toEntity(imageFileName, principalDetail.getUser());
		imageRepository.save(image);
		
		
	}
	
	
	
	
	
}
