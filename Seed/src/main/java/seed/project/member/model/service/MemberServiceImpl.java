package seed.project.member.model.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import seed.project.member.model.dto.Member;
import seed.project.member.model.mapper.MemberMapper;

@Transactional(rollbackFor=Exception.class)
@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{
	
	private final MemberMapper mapper;
	
	private final BCryptPasswordEncoder bcrypt;
	
	
	
	// 로그인 서비스
	@Override
	public Member login(Member inputMember) {
		
		// 아이디가 일치하면서 회원탈퇴하지 않은 회원 조회
		Member loginMember = mapper.login(inputMember.getMemberId());
		
//		String bc = bcrypt.encode(loginMember.getMemberPw());

//		log.debug("bc : " + bc);
//		log.debug("디버그 나오는지 확인");
//
//		System.out.println("bc : " + bc);
		
		// 일치하는 아이디가 없으면 리턴
		if(loginMember == null) return null;
		
		// 비번이 일치하는지 확인
		if(!bcrypt.matches(inputMember.getMemberPw(), loginMember.getMemberPw())) {
			return null;
		}
		
		// 로그인 결과에서 비번 제거
		loginMember.setMemberPw(null);
		
		return loginMember;
	}

}
