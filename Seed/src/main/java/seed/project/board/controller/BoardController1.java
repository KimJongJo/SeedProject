package seed.project.board.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import seed.project.board.model.dto.Board;
import seed.project.board.model.service.BoardService1;
import seed.project.member.model.dto.Member;

@Controller
@RequestMapping("board")
@Slf4j
@RequiredArgsConstructor
public class BoardController1 {

	private final BoardService1 service;
	
	
	/** [1] 자유게시판 목록 조회
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
			@RequestParam(value="key", required=false) String key,
			@RequestParam(value="query", required=false) String query
			) {
		
		
		// 검색 안할 때
		if(query == null) { 
			
			// 게시글 목록 조회 서비스 호출
			Map<String, Object> map = service.selectBoardList1(boardCode, cp);
			
			// boardNo, baordTitle, memberNickname, readCount, commentCount,
			// likeCount, baordWriteDate
			model.addAttribute("boardList", map.get("boardList"));
			// 게시글 수 + 현제 페이지
			model.addAttribute("pagination", map.get("pagination"));
			
		// 검색할 때
		} else {
			
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("key", key);
			paramMap.put("query", query);			
			paramMap.put("boardCode", boardCode);
			// -> paramMap은 {key=t, quer=검색어, boardCode=1}
			
			
			Map<String, Object> map = service.selectBoardSearchList1(paramMap, cp);
			
			map = service.searchList1(paramMap, cp);
			
			model.addAttribute("pagination", map.get("pagination"));
			model.addAttribute("boardList", map.get("boardList"));
			
			
			/*
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("option", option);
			paramMap.put("keyWord", keyWord);
			
			Map<String, Object> map = service.selectBoard2SearchList(paramMap, cp);
			
			if(map != null) {
				model.addAttribute("boardList", map.get("boardList"));
				model.addAttribute("pagination", map.get("pagination"));
				model.addAttribute("option", option);
				model.addAttribute("keyWord", keyWord);
			}
			
			*/
		}
		
		return "board/board1";
	}
	
	

	/** [1] 자유게시판 상세조회
	 * @param boardCode : 자유게시판
	 * @param boardNo   : 게시판 번호
	 * @param loginMember
	 * @param req : 쿠키 가져오기
	 * @param resp : 쿠키 보내기
	 * @return
	 */
	@GetMapping("{boardCode:[1]}/{boardNo:[0-9]+}")
	public String board1Detail(
			@PathVariable("boardCode") int boardCode,
			@PathVariable("boardNo") int boardNo,
			@SessionAttribute(value="loginMember", required=false) Member loginMember,
			HttpServletRequest req, // 쿠키 가져오기
			HttpServletResponse resp,
			Model model
			) {
		
		// 전달할 파라미터들을 Map으로 묶기
		Map<String, Object> map = new HashMap<>();
		map.put("boardCode", boardCode);
		map.put("boardNo", boardNo);
		
		// 로그인 했을 때 map에 로그인한 회원번호 넣기
		if(loginMember != null) {
			map.put("memberNo", loginMember.getMemberNo());
		}
		
		// 게시글 상세조회
		Board board = service.selectOne1(map);
	
		model.addAttribute("board", board);
			

		return "board/board1Detail";
	}
	
	
	/** [1] 게시글 수정 페이지로 이동
	 * @param boardCode
	 * @param boardNo
	 * @param model
	 * @return
	 */
	@GetMapping("{boardCode:[1]}/board1Update")
	public String board1Update(
			@PathVariable("boardCode") int boardCode,
			@RequestParam("boardNo") int boardNo,
			Model model
			) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("boardCode", boardCode);
		map.put("boardNo", boardNo);
		
		
		
		Board board = service.selectOne1(map);
//		Board board = service.board1Info(boardNo);
		
		model.addAttribute(board);
		
		return "/board/board1Update";
	}
	
	
	
	
	/** [1] 게시글 수정하기
	 * @param board
	 * @return
	 */
	@ResponseBody
	@PutMapping("{boardCode:[1]}/board1Update")
	public int board1Update(@RequestBody Map<String, Object> board) {
		return service.board1Update(board);
	}
	
	@ResponseBody
	@DeleteMapping("{boardCode:[1]}/board1Delete")
	public int board1Delete(@RequestBody int boardNo) {
		return service.board1Delete(boardNo);
	}
	


	
}
