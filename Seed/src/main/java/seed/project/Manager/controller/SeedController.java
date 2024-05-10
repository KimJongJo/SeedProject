package seed.project.Manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import seed.project.Manager.model.service.SeedService;

@Controller
@RequiredArgsConstructor
@RequestMapping("seed")
public class SeedController {

	private final SeedService service;
	
	
	/** 상품 관리 페이지 이동
	 * @return
	 */
	@GetMapping("menu")
	public String menu() {
		
		return "/seed/menu";
	}
	
}
