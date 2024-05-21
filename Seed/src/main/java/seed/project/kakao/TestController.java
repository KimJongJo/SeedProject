package seed.project.kakao;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/kakao")
public class TestController {

	@GetMapping("/login")
	public String kakaoApiTest(@RequestParam("code") String code) {
		
		System.out.println("code ======== " + code);
		
		
		return null;
	}
}
