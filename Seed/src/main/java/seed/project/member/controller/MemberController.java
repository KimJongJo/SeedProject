package seed.project.member.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
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
	
	@Value("${kakao.api.key}")
	private String kakaoApiKey;
	
	@Value("${kakao.redirect.uri}")
	private String kakaoApiUri;
	
	@Value("${google.api.client.id}")
	private String googleApiClientId;
	
	@Value("${google.redirect.uri}")
	private String googleApiUri;
	
	
	/** 로그인 페이지 이동
	 * @return
	 */
	@GetMapping("login")
	public String login(Model model) {
		
		model.addAttribute("REST_API_KEY", kakaoApiKey);
		model.addAttribute("REDIRECT_URI", kakaoApiUri);	
		
		model.addAttribute("googleApiClientId", googleApiClientId);
		model.addAttribute("googleApiUri", googleApiUri);
		
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
			
			return "redirect:/member/login";
		}
		
		// 로그인 성공 시
		if(loginMember != null) {
			
			// Session scope 에 loginMember 추가
			model.addAttribute("loginMember", loginMember);
			
			Cookie cookie = new Cookie("saveId", loginMember.getMemberId());
			
			cookie.setPath("/member/login");
			
			// 쿠키 만료 기간 지정
			if(saveId != null) { // 아이디 저장 체크시
				cookie.setMaxAge(60 * 60 * 24 * 30); // 30일
			} else { // 미체크 시
				cookie.setMaxAge(0);
			}
			
			// 클라이언트에 쿠키 전달
			resp.addCookie(cookie);
			
			ra.addFlashAttribute("message", loginMember.getMemberNickname() + "님 환영합니다.");
		}
		return "redirect:/"; // 메인페이지 재요청
	}
	
	

//	회원가입 페이지로 이동

	@GetMapping("signup")
	public String signup() {
		
		return "/member/signup";
	}
	
	
//	아이디 찾기
	@GetMapping("searchId")
	public String searchId() {
		
		return "/member/searchId";
	}
	
	
//	비밀번호 찾기
	@GetMapping("searchPw")
	public String searchPw() {
		
		return "/member/searchPw";
	}
	
	

	
	// 로그아웃
	@GetMapping("logout")
	public String logout(SessionStatus status,
						RedirectAttributes ra) {
		status.setComplete();
		ra.addFlashAttribute("message", "로그아웃 되었습니다.");
		
		return "redirect:/";
	}

//	비밀번호 변경
	@GetMapping("updatePw")
	public String changePw() {
		
		return "/member/myPage/updatePw";
	}
		
//	회원정보 변경
	@GetMapping("updateInfo")
	public String changeInfo() {
		
		return "/member/myPage/updateInfo";
	}

	// 이메일 인증번호 발급
	@ResponseBody
	@PostMapping("sendEmail")
	public int sendEmail(@RequestBody String memberEmail){
		
		String randomString = service.sendEmail("findPw", memberEmail);
		
		
		if(randomString != null) {
			return 1;
		}
		
		// 이메일 보내기 실패
		return 0;
	}
	
	// 이메일 인증번호 확인
	@ResponseBody
	@PostMapping("authCheck")
	public int authCheck(@RequestBody String authString) {
		
		int result = service.authCheck(authString);
		
		if( result > 0 ) {
			return 1;
		}
		
		return 0;
		
	}
	
	/** 회원가입 - 아이디 중복 체크
	 * @param memberId
	 * @return
	 */
	@ResponseBody
	@GetMapping("checkId")
	public int checkId(@RequestParam("memberId") String memberId) {
		
		return service.checkId(memberId);
	}
	
	
	
	/** 존재하는 회원인지 조회 (아이디, 이메일)
	 * @return result
	 */
	@ResponseBody
	@PostMapping("findResult")
	public int findResult(@RequestBody Member member) {
		
		return service.findResult(member);
	}
	
	
	
	/** 비밀번호 찾기 -> 변경
	 * @return result
	 */
	@ResponseBody
	@PostMapping("findPwCh")
	public int findPwCh(@RequestBody Map<String, String> map) {

		return service.findPwCh(map);
	}

	
	/** 아이디 찾기
	 * @param map
	 * @return result
	 */
	@ResponseBody
	@PostMapping("findId")
	public int findId(@RequestBody Map<String, String> map) {
		
		return service.findId(map);
	}
	
	/** 회원가입 - 이메일 중복 체크
	 * @param memberEmail
	 * @return
	 */
	@ResponseBody
	@GetMapping("checkEmail")
	public int checkEmail(@RequestParam("memberEmail") String memberEmail) {
		
		return service.checkEmail(memberEmail);
	}
	
	
	/** 회원가입 - 닉네임 중복 체크
	 * @param memberNickname
	 * @return
	 */
	@ResponseBody
	@GetMapping("checkNickname")
	public int checkNickname(@RequestParam("memberNickname") String memberNickname) {
		
		return service.checkNickname(memberNickname);
	}
	

	/** 회원가입(제출)
	 * @param inputMember
	 * @param memberAddress
	 * @param ra
	 * @return
	 */
	@PostMapping("signup")
	public String signup(@ModelAttribute Member inputMember,
						@RequestParam("memberAddress") String[] memberAddress,
						RedirectAttributes ra) {
		
		
		// 회원가입 서비스 호출
		int result = service.signup(inputMember, memberAddress);
		
		String path = null;
		String message = null;
		
		if(result > 0) { // 성공 시
			message = inputMember.getMemberNickname() + "님의 가입을 환영합니다 :)";
			path = "/";
			
		} else { // 실패
			message = "회원가입 실패...";
			path = "signup";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}

	

	
	
	
}
