package seed.project.member.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@SessionAttributes("loginMember")
public class NaverLoginController {
	
	@GetMapping("/naverLogin")
	public String naverCallback() {
		return "member/naverCallback";
	}
	
	
	@ResponseBody
	@PostMapping("/naverData")
	public int naverData(
				Map<String, String> map, 
				RedirectAttributes ra,
				Model model) {
		
	
		
		
		
		
		return 0;
	}
	
}	

/*
 
 			INSERT INTO "MEMBER"
		VALUES(SEQ_MEMBER_NO.NEXTVAL, 
			 #{memberId},
			 #{memberPw},
			 #{memberNickname},
			 #{memberEmail},
			 #{memberAddress},
			 #{memberTel},
			 DEFAULT,
			 DEFAULT,
			 DEFAULT
 
 */