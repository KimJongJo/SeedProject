package seed.project.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import seed.project.board.model.dto.Board;
import seed.project.board.model.service.BoardService;

@Controller
@RequiredArgsConstructor
@RequestMapping("board")
@Slf4j
public class BoardController {
	
	private final BoardService service;
	
	@GetMapping("{boardCode:[2]}")
	public String board2(Model model) {
		
		List<Board> boardList = service.selectBoard2List(); 
		
		model.addAttribute("boardList", boardList);
		
		return "board/board2";
	}
	
}
