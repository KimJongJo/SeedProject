package seed.project.board.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import seed.project.board.model.service.BoardService;

@Controller
@RequestMapping("board")
@Slf4j
@RequiredArgsConstructor
@SessionAttributes("${loginMember}")
public class BoardController {
	
	private final BoardService service;
	
	
	@GetMapping("{boardCode:[3]}")
	public String selectBoardList(@PathVariable("boardCode") int boardCode,
								@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
								Model model,
								@RequestParam Map<String, Object> paramMap) {
		
		 
		
	
		
		return "board/board3"; // boardList.html로 forward
	} 
	
}
