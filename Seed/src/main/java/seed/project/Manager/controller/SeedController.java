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
	
	
	/** 상품 등록 페이지 이동
	 * @return
	 */
	@GetMapping("add")
	public String seedAdd() {
		
		return "/seed/seedAdd";
	}
	
	
	/** 상품 수정 페이지 이동
	 * @return
	 */
	@GetMapping("update")
	public String seedUpdate() {
		
		return "/seed/seedUpdate";
	}
	
	
	/** 상품 삭제 페이지 이동
	 * @return
	 */
	@GetMapping("delete")
	public String seedDelete() {
		
		return "/seed/seedDelete";
	}
}
