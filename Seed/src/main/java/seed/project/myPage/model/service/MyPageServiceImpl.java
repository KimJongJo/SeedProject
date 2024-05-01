package seed.project.myPage.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import seed.project.member.model.dto.Member;
import seed.project.myPage.model.mapper.MyPageMapper;

@Service
@Transactional(rollbackFor=Exception.class)
@RequiredArgsConstructor
@PropertySource("classpath:/config.properties")
public class MyPageServiceImpl implements MyPageService{
	
	private final MyPageMapper mapper;
	
	private final BCryptPasswordEncoder bcrypt;

	// 회원 정보 수정
	@Override
	public int updateInfo(Member inputMember) {
		return mapper.updateInfo(inputMember);
	}

	// 회원 비밀번호 수정
	@Override
	public int updatePw(Map<String, Object> paramMap, int memberNo) {
		
		// 현재 로그인한 회원의 암호화된 비번을 DB에서 검색
		String originPw = mapper.selectPw(memberNo);

		// 현재 비번이 다를 경우
		// nowPw(현재 비번) : updatePw.html 에서 name 값을 가져온 것
		// 입력한 현재비번(nowPw)랑 기존 현재비번(originPw)가 일치하는지 확인
		if(!bcrypt.matches((String)paramMap.get("nowPw"), originPw)) {
			return 0;
		}
		
		// 현재 비번이 같을 경우
		// updatePw(바꿀 비번) : updatePw.html에서 name 값을 가져온 것
		String encPw = bcrypt.encode((String)paramMap.get("updatePw"));
		
		// mapper.xml 에서 수정하기 위해 map에
		// 암호화된 바꿀 비번(encPw), 비번 수정할 회원 번호(memberNo)집어넣기
		paramMap.put("encPw", encPw);
		paramMap.put("memberNo", memberNo);
		
		return mapper.updatePw(paramMap);
	}

	

	// 회원 주소 검색
	@Override
	public List<Map<String, Object>> selectAddressList(int memberNo) {
		
		return mapper.selectAddressList(memberNo);
	}
	

	
	
}
