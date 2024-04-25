package seed.project.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import seed.project.member.model.dto.Member;
import seed.project.member.model.service.MemberService;

@Controller
@Slf4j
@RequiredArgsConstructor
@SessionAttributes("loginMember")
@RequestMapping("member")
public class MemberController {

	private final MemberService service;
	
	
	/** 로그인 페이지 이동
	 * @return
	 */
	@GetMapping("login")
	public String login() {
		
		return "/member/login";
	}
	
	
	
	
	
	
	/** 로그인
	 * @param inputMember : 커맨드 객체 memberId, memberPw 세팅된 상태
	 * @param ra : 리다이렉트 시 request scope로 데이터를 전달하는 객체
	 * @param model : 데이터 전달용 객체(기본 request scope)
	 * @param saveId : 아이디저장 체크박스 체크 됬을 때 : "on", 체크 안됐을 때 : null
	 * @param resp : 아이디 저장 버튼 쿠키로 가져올려고 씀
	 * @return "redirect:/"
	 */
	@PostMapping("login")
	public String login(@ModelAttribute Member inputMember,
						RedirectAttributes ra,
						Model model,
						@RequestParam(value="saveId", required = false) String saveId,
						HttpServletResponse resp
						) {
		
		// 로그인 서비스 호출
		Member loginMember = service.login(inputMember);
		
		if(loginMember == null) {
			ra.addFlashAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다.");
		}
		
		// 로그인 성공 시
		if(loginMember != null) {
			
			// Session scope 에 loginMember 추가
			model.addAttribute("loginMember", loginMember);
			
			Cookie cookie = new Cookie("savedId", loginMember.getMemberId());
			
			cookie.setPath("/");
			
			// 쿠키 만료 기간 지정
			if(saveId != null) { // 아이디 저장 체크시
				cookie.setMaxAge(60 * 60 * 24 * 30); // 30일
			} else { // 미체크 시
				cookie.setMaxAge(0);
			}
			
			// 클라이언트에 쿠키 전달
			resp.addCookie(cookie);
			
		}
		return "redirect:/"; // 메인페이지 재요청
	}
	
	@GetMapping("signup")
	public String signup() {
		
		return "/member/signup";
	}
		
	
}
