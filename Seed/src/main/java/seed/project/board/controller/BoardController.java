package seed.project.board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import seed.project.board.model.dto.Board;
import seed.project.board.model.dto.Comment;
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
  
  @GetMapping("{boardCode:[2]}")
  public String board2(Model model,
						@RequestParam(value="cp", required = false, defaultValue="1") int cp
						) {
		
		Map<String, Object> map = service.selectBoard2List(2, cp); 
		
		model.addAttribute("boardList", map.get("boardList"));
		model.addAttribute("pagination", map.get("pagination"));
		
		return "board/board2";
  }
  
  
  @GetMapping("{boardCode:[2]}/detail")
  public String board2Detail(
		  			@RequestParam(value="boardNo") int boardNo,
		  			Model model
		  			) {
	  
	  Board boardInfo = service.board2Detail(boardNo);
	  List<Comment> commentList = service.board2CommentList(boardNo);
	  		
	  model.addAttribute("boardInfo", boardInfo);
	  model.addAttribute("commentList", commentList);
	  
	  return "board/board2Detail";
  }
  
  
  
  
  @GetMapping("{boardCode:[3]}")
	public String selectBoardList(@PathVariable("boardCode") int boardCode,
								@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
								Model model,
								@RequestParam Map<String, Object> paramMap) {
		
		 
		
		return "board/board3"; // boardList.html로 forward
    
  }
}
