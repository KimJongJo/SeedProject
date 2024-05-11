package seed.project.Manager.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
     * @throws IOException 
     */
    @PostMapping("seedAdd")
    public String seedAdd(Seed seed,
                          Model model) throws IOException {
    	
    	

        // 파일이 업로드되었는지 확인
        if (seed.getSeedImg().isEmpty()) {
            model.addAttribute("message", "이미지를 선택해주세요.");
            return "/seed/seedAdd";
        }

        
        int result = service.seedAdd(seed);

        if (result > 0) {
            model.addAttribute("message", "새로운 씨앗을 심었습니다!");
        } else {
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

	
	
	/** 가격 확인
	 * @param seedName
	 * @return
	 */
	@ResponseBody
	@GetMapping("seedPrice")
	public String seedPrice(@RequestParam("seedName") String seedName) {
		
		return service.seedPrice(seedName);
	}
	
	

	
	/** 가격 수정
	 * @return
	 */
	@ResponseBody
	@PutMapping("seedUpdatePrice")
	public int seedUpdatrPrice(@RequestBody Map<String, String> seedMap) {

		return service.seedUpdatrPrice(seedMap);
	}
	
	
	
	
	/** 상품 삭제 페이지 이동
	 * @return
	 */
	@GetMapping("delete")
	public String seedDelete() {
		
		return "/seed/seedDelete";
	}
	
	
	/** 씨앗 품절
	 * @param seedName
	 * @return
	 */
	@ResponseBody
	@DeleteMapping("seedDeleteOn")
	public int seedDeleteOn(@RequestBody String seedName) {
		
		return service.seedDeleteOn(seedName);
	}
	
	/** 씨앗 품절 해제
	 * @param seedName
	 * @return
	 */
	@ResponseBody
	@PutMapping("seedDeleteOff")
	public int seedDeleteOff(@RequestBody String seedName) {
		
		return service.seedDeleteOff(seedName);
	}
	
	
}
