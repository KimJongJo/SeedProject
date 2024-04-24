package seed.project.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import seed.project.member.model.service.MemberService;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("member")
public class MemberController {

	private final MemberService service;
	
	
	@GetMapping("login")
	public String login() {
		
		return "member/login";
	}
	
	@GetMapping("signup")
	public String signup() {
		
		return "member/signup";
	}
		
	
}
