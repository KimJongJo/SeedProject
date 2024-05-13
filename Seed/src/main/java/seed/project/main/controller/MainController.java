package seed.project.main.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import seed.project.Manager.model.dto.Seed;
import seed.project.Manager.model.service.SeedService;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	private final SeedService seedService;
	
	@RequestMapping("/")
	public String mainPage(Model model) {
		
		List<Seed> seedList = seedService.seedList();
		
		model.addAttribute("seedList", seedList);
		
		return "common/main";
		
	}
	
	@GetMapping("loginError")
	public String loginError(RedirectAttributes ra) {
		
		ra.addFlashAttribute("message", "로그인 후 이용해 주세요!");
		
		return "redirect:/";
	}
	
	@GetMapping("managerError")
	public String managerError(RedirectAttributes ra) {
		
		ra.addFlashAttribute("message", "관리자 전용 페이지 입니다..");
		
		return "redirect:/";
	}
}
