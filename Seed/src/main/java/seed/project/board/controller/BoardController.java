package seed.project.board.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import seed.project.board.model.service.BoardService;

@Controller
@RequestMapping("board")
@Slf4j
@RequiredArgsConstructor
public class BoardController {

	private final BoardService service;
	
	
	/**
	 * @param cp : 현재 조회 요청한 페이지 (없으면 1)
	 * @param model
	 * @param paramMap : 제출될 파라미터가 모두 저장된 Map
	 * 					(검색 시 keyp, query 담겨있음)
	 * @return
	 */
	@GetMapping("{boardCode:[1]}")
	public String board1(
			@PathVariable("boardCode") int boardCode,
			@RequestParam(value="cp", required=false, defaultValue="1") int cp,
			Model model,
			@RequestParam Map<String, Object> paramMap
			) {
		
		Map<String, Object> map = null;
		
//		if(paramMap.get("key") = null) {
//			
//			// 게시글 목록 조회 서비스 호출
//			map = service.selectBoardList(boardCode, cp);
//		}
		
		
		return "";
	}
	
	
}
