package seed.project.kakao.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
						Model model,
						RedirectAttributes ra) {
		
//		System.out.println("code ======== " + code);
		
		// 토큰 발급
		String accessToken = service.getKakaoToken(code);
		
//		System.out.println("accessToken ========= " + accessToken);
		
		// 사용자 정보 받아오기
		// 안에는 kakaoId, memberNickname이 들어있음
		HashMap<String, Object> userInfo = service.getUserInfo(accessToken);
		
		// 카카오로 회원 가입 한 회원인지 확인하기 
		// 처음 카카오 로그인 하는 경우 회원가입 시켜주기
		String memberId = String.valueOf(userInfo.get("kakaoId")); 
		String memberNickname = String.valueOf(userInfo.get("memberNickname"));
		
		System.out.println(memberId);
		System.out.println("ID" + memberId);
		
		int result = memberService.findIdForKakao("ID" + memberId);
		
		// 이미 존재하는회원
		if(result > 0) {
			Member loginMember = memberService.login("ID" + memberId);
			
			model.addAttribute("loginMember", loginMember);
			
			model.addAttribute("message", "카카오 로그인 완료!");
			
			System.out.println("카카오 로그인 완료");
			
			ra.addFlashAttribute("message", loginMember.getMemberNickname() + "님 환영합니다.");
			
		}else { // 존재하지 않은 회원일 경우 회원가입 시켜줌
			Member signMember = Member.builder()
								.memberId("ID" + memberId)
								.memberEmail("kakaoEmail")
								.memberPw("PW" + memberId)
								.memberNickname(memberNickname)
								.memberTel("kakaoTel")
								.build();
			
			int result2 = memberService.signForKakao(signMember);
			
			
			if(result2 > 0) {
				model.addAttribute("message", "카카오 회원가입 완료!");
				
				model.addAttribute("loginMember", signMember);
				
				ra.addFlashAttribute("message", signMember.getMemberNickname() + "님의 회원가입을 완료했습니다. 환영합니다.");
				
				System.out.println("카카오 회원가입");
			}else {
				System.out.println("카카오 회원가입 안됨");
			}
				
		}
		
		
		
		return "redirect:/";
	}
}
