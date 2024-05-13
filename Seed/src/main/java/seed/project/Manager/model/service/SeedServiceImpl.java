package seed.project.Manager.model.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import lombok.RequiredArgsConstructor;
import seed.project.Manager.mapper.SeedMapper;
import seed.project.Manager.model.dto.Seed;
import seed.project.common.util.Utility;

@Service
@RequiredArgsConstructor
public class SeedServiceImpl implements SeedService{
	
	
	private static String uploadDirectory = "/uploadFiles/seedImg"; // 상품 이미지를 저장할 디렉토리 경로
	
	
	@Value("${seed.img.name-path}")
	private String profileWebPath;	// /myPage/profile/
	
	@Value("${seed.img.forder-path}")
	private String profileFolderPath;	// C:/uploadFiles/seedImg/

	private final SeedMapper mapper;

	@Override
	public int seedAdd(Seed seed) throws IOException{
		
        Path uploadPath = Paths.get(uploadDirectory);

        
        try {
        	// 디렉토리가 존재하지 않으면 생성
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        }catch(Exception e) {
        	e.printStackTrace();
        	System.out.println("디렉토리 생성 오류");
        }
        
        
        
		
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
		
		
		seedImg.transferTo(new File(profileFolderPath + seedImgName));
		
		return result;
	}

	
	// 씨앗 리스트
	@Override
	public List<Seed> seedList() {
		
		return mapper.seedList();
	}


	// 씨앗 가격
	@Override
	public String seedPrice(String seedName) {
		
		return mapper.seedPrice(seedName);
	}


	
	// 씨앗 수정
	@Override
	public int seedUpdatrPrice(Map<String, String> seedMap) {
		
		return mapper.seedUpdatrPrice(seedMap);
	}


	// 씨앗 품절
	@Override
	public int seedDeleteOn(String seedName) {

		// 품절 상태인지 확인 1 -> 품절, 0 -> 품절 x
		int result = mapper.seedDeleteOnCheck(seedName);
		
		if(result > 0) {
			return 0;
		}else {
			return mapper.seedDeleteOn(seedName);
		}
		
	}


	// 씨앗 품절 해제
	@Override
	public int seedDeleteOff(String seedName) {

		// 품절 상태인지 확인 1 -> 품절, 0 -> 품절 x
		int result = mapper.seedDeleteOffCheck(seedName);
		
		if(result > 0) {
			return 0;
		}else {
			return mapper.seedDeleteOff(seedName);
		}
	}


	
	// 씨앗 상세 정보
	@Override
	public Seed seedDetail(int seedNo) {
		
		return mapper.seedDetail(seedNo);
	}


	// 씨앗 정렬
	@Override
	public List<Seed> seedSort(int sortType) {

		// 1 높은 가격순,  2 낮은 가격순, 3 종류별
		switch(sortType) {
		case 1 : return mapper.highList();
		case 2 : return mapper.lowList();
		case 3 : return mapper.typeList();
		default : return mapper.nameList();
		}
		
	}


	// 씨앗 검색
	@Override
	public List<Seed> seedSearch(String key) {
		
		return mapper.seedSearch(key);
	}




}
