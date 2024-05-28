package seed.project.google.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import seed.project.google.model.GoogleService;

@Controller
@RequestMapping("google")
@RequiredArgsConstructor
public class GoogleController {
	
	private final GoogleService service;

	@GetMapping("callback")
	public String googleApiTest(@RequestParam("code") String code) {
		
		System.out.println("google : " + code);
		
		String GoogleToken = service.getGoogleToken(code);
		
		Map<String, Object> userInfo = service.getUserInfo(GoogleToken);
		
		System.out.println("유저 정보 : " + userInfo);
		
		
		return "redirect:/";
	}
	
}
