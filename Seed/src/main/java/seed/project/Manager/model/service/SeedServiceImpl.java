package seed.project.Manager.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import seed.project.Manager.mapper.SeedMapper;
import seed.project.Manager.model.dto.Seed;
import seed.project.common.util.Utility;

@Service
@RequiredArgsConstructor
public class SeedServiceImpl implements SeedService{
	
	@Value("${my.profile.web-path}")
	private String profileWebPath;	// /myPage/profile/
	
	@Value("${my.profile.folder-path}")
	private String profileFolderPath;	// C:/uploadFiles/profile/

	private final SeedMapper mapper;

	@Override
	public int seedAdd(Seed seed) {
		
		MultipartFile seedImg = seed.getSeedImg();
		
		// 씨앗 이미지 경로
		String seedImgPath = null;
		
		// 이미지 이름
		String seedImgName = null;
		
		// 1. 파일 명 변경
		seedImgName = Utility.fileRename(seedImg.getOriginalFilename());
		
		// 2. 변경된 파일명
		seedImgPath = profileWebPath + seedImgName;
		
		seed.setSeedImgPath(seedImgPath);
		
		// insert 수행
		int result = mapper.addSeed(seed);
		
		
		return result;
	}

	
	// 씨앗 리스트
	@Override
	public List<Seed> seedList() {
		
		return mapper.seedList();
	}
	
}
