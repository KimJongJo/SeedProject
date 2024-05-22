package seed.project.kakao.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import seed.project.kakao.model.service.TestService;
import seed.project.member.model.dto.Member;
import seed.project.member.model.service.MemberService;


@Controller
@RequestMapping("kakao")
@RequiredArgsConstructor
@SessionAttributes("loginMember")
public class TestController {
	
	@Value("${kakao.api.key}")
	private String kakaoApiKey;
	
	@Value("${kakao.redirect.uri}")
	private String kakaoApiUri;
	
	private final TestService service;
	
	private final MemberService memberService;
	

	@GetMapping("login")
	public String kakaoApiTest(@RequestParam("code") String code,
						HttpServletResponse resp,
						Model model) {
		
		System.out.println("code ======== " + code);
		
		String accessToken = service.getKakaoToken(code);
		
		System.out.println("accessToken ========= " + accessToken);
		
		HashMap<String, Object> userInfo = service.getUserInfo(accessToken);
		
		
		// 카카오로 회원 가입 한 회원인지 확인하기 
		// 처음 카카오 로그인 하는 경우 회원가입 시켜주기
		int result = memberService.findIdForKakao((int)userInfo.get("kakaoId"), (String)userInfo.get("memberNickname"));
		
		// 이미 존재하는회원
		if(result > 0) {
			Member loginMember = memberService.login((int)userInfo.get("kakaoId"));
			
			model.addAttribute("loginMember", loginMember);
			
			model.addAttribute("message", "카카오 로그인 완료!");
			
		}else { // 존재하지 않은 회원일 경우 회원가입 시켜줌
			Member signMember = Member.builder()
								.memberId("ID" + (int)userInfo.get("kakaoId"))
								.memberEmail("kakaoEmail")
								.memberPw("PW" + (int)userInfo.get("kakaoId"))
								.memberNickname((String)userInfo.get("memberNickname"))
								.memberAddress("kakaoAddress")
								.memberTel("kakaoTel")
								.build();
			
			int result2 = memberService.signForKakao(signMember);
			
			model.addAttribute("message", "카카오 회원가입 완료!");
								
			
		}
		
		
		
		return "redirect:/";
	}
}
