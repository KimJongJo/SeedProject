package seed.project.Manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import seed.project.Manager.model.dto.Seed;
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
	
	
	/** 상품 등록
	 * @return
	 */
	@PostMapping("seedAdd")
	public String seedAdd(Seed seed,
					Model model
			) {

		int result = service.seedAdd(seed);
		
		if(result > 0) {
			model.addAttribute("message", "새로운 씨앗을 심었습니다!");
		}else {
			model.addAttribute("message", "씨앗 심지 못했습니다...");
		}
		
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
