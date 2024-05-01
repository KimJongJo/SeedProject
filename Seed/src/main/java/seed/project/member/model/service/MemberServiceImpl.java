package seed.project.member.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.internet.MimeMessage;
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
	
	private final SpringTemplateEngine templateEngine;
	
	private final JavaMailSender mailSender;
	
	
	
	// 로그인 서비스
	@Override
	public Member login(Member inputMember) {
		
		// 아이디가 일치하면서 회원탈퇴하지 않은 회원 조회
		Member loginMember = mapper.login(inputMember.getMemberId());
		
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


	// 이메일 인증번호 발급
	@Override
	public String sendEmail(String html, String memberEmail) {
		
		// 랜덤 인증문자 생성
		String randomString = randomString();
		
//		log.debug("randomString : " + randomString);
		
		try {
			MimeMessage mail = mailSender.createMimeMessage();
			
			MimeMessageHelper helper = new MimeMessageHelper(mail, true, "UTF-8");
			
			helper.setTo(memberEmail); // 받는 사람 이메일
			helper.setSubject("[언더 더 씨] 회원 가입 인증번호"); // 이메일 제목
			helper.setText( loadHtml(randomString, html), true);
			
			helper.addInline("logo", new ClassPathResource("static/images/씨앗.png"));
			
			mailSender.send(mail);
			
			
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
//		디버그 나와라
//		log.debug("randomString : " + randomString);
		
		Map<String, String> map = new HashMap<>();
		map.put("randomString", randomString);
		map.put("email", memberEmail);
		
		int result = mapper.updateAuthKey(map);
		
		if(result == 0) {
			result = mapper.insertAuthkey(map);
		}
		
		if(result == 0) return null;
		
		return randomString;
	}
	
	
	/** 인증번호 생성기
	 * @return randomString
	 */
	   public String randomString() {
		   String randomString = "";
		   for(int i=0 ; i< 6 ; i++) {
		          
		       int sel1 = (int)(Math.random() * 3); // 0:숫자 / 1,2:영어
		      
		       if(sel1 == 0) {
		          
		           int num = (int)(Math.random() * 10); // 0~9
		           randomString += num;
		          
		       }else {
		          
		           char ch = (char)(Math.random() * 26 + 65); // A~Z
		          
		           int sel2 = (int)(Math.random() * 2); // 0:소문자 / 1:대문자
		          
		           if(sel2 == 0) {
		               ch = (char)(ch + ('a' - 'A')); // 대문자로 변경
		           }
		          
		           randomString += ch;
		       }
		          
	       }
	       return randomString;
	   }
	
	
	// HTML 파일을 읽어와 String 으로 변환
	private String loadHtml(String randomString, String html) {
		
		Context context = new Context();
		
		context.setVariable("randomString", randomString);
		
		return templateEngine.process("email/" + html, context);
	}

	// 이메일 인증번호 확인
	@Override
	public int authCheck(String authString) {
		
		return mapper.authCheck(authString);
	}

  
	// 회원가입 - 아이디 중복 체크
	@Override
	public int checkId(String memberId) {
		
		return mapper.checkId(memberId);
	}

	// 존재하는 회원인지 조회(아이디, 이메일)
	@Override
	public int findResult(Member member) {
		
		return mapper.findResult(member);
	}

	// 비밀번호 찾기 -> 변경
	@Override
	public int findPwCh(Map<String, String> map) {

		
		String bc = bcrypt.encode(map.get("memberPw"));
		
//		기존과 같은 비밀번호인지 확인
		String beforeBc = mapper.beforeBc(map.get("memberId"));
		
		if(bcrypt.matches(map.get("memberPw"), beforeBc)) {
			return -1;
		};
		
		Map<String, String> bcMap = new HashMap<>();
		bcMap.put("memberId", map.get("memberId"));
		bcMap.put("memberPw", bc);

		int result = mapper.findPwCh(bcMap);
		
		return result;
	}

	// 아이디 찾기
	@Override
	public int findId(Map<String, String> map) {
		
		String result = mapper.findId(map);
		
		// 회원 정보와 일치하는 아이디가 없을 때
		if(result == null) {
			return 0;
		}
		
		// 있을때 -> 이메일로 회원 아이디 전송
		try {
			MimeMessage mail = mailSender.createMimeMessage();
			
			MimeMessageHelper helper = new MimeMessageHelper(mail, true, "UTF-8");
			
			helper.setTo(map.get("memberEmail")); // 받는 사람 이메일
			helper.setSubject("[언더 더 씨] 아이디 찾기 요청 응답"); // 이메일 제목
			helper.setText( loadHtml(result, "findId"), true);
			
			helper.addInline("logo", new ClassPathResource("static/images/씨앗.png"));
			
			mailSender.send(mail);
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}

		return 1;
	}

}
