package seed.project.board.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import seed.project.board.model.dto.Board;
import seed.project.board.model.dto.Comment;
import seed.project.board.model.service.BoardService;
import seed.project.member.model.dto.Member;

@Controller
@RequestMapping("board")
@Slf4j
@RequiredArgsConstructor
public class BoardController {

	private final BoardService service;
	
	


  
	  /** [2] 문의 게시판 페이지 이동
	 * @param model
	 * @param cp
	 * @return
	 */
	@GetMapping("{boardCode:[2]}")
	  public String board2(Model model,
							@RequestParam(value="cp", required = false, defaultValue="1") int cp,
							@RequestParam(value= "option", required = false) String option,
							@RequestParam(value= "keyWord", required = false) String keyWord
							) {
			
		// 검색 기능을 사용하지 않음
		if(option == null) {
			Map<String, Object> map = service.selectBoard2List(2, cp); 
			
			if(map != null) {
				model.addAttribute("boardList", map.get("boardList"));
				model.addAttribute("pagination", map.get("pagination"));
			}
		}else {	// 검색 기능을 사용함
			
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
			
		}
			
			
			return "board/board2";
	  }
	  
	  
	  /** [2] 문의 게시글 상세정보
	 * @param boardNo
	 * @param model
	 * @return
	 */
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
	  
	  
	  /** [2] 문의 게시글 삭제
	 * @param boardNo
	 * @return
	 */
	@ResponseBody
	@DeleteMapping("{boardCode:[2]}/delete")
	public int board2Delete(@RequestBody int boardNo) {
		  
		return service.board2Delete(boardNo);
	}
	
	/** [2] 문의 게시글 작성 페이지 이동
	 * @return
	 */
	@GetMapping("board2Write")
	public String board2Write() {
		
		return "board/board2Write";
	}
	
	
	
	/** [2] 문의 게시글 작성 결과
	 * @param board
	 * @return
	 */
	@ResponseBody
	@PostMapping("{boardCode:[2]}/board2Write")
	public int board2Write(@RequestBody Map<String, String> board) {
		
		return service.board2Write(board);
	}
	
	
	/** [2] 게시글 수정 페이지 이동
	 * @return
	 */
	@GetMapping("{boardCode:[2]}/board2Update")
	public String board2Update(
					@RequestParam(value = "boardNo") int boardNo,
					Model model
			) {
		
		Board board = service.board2Info(boardNo);
		
		model.addAttribute("board", board);
		
		return "board/board2Update";
	}
	
	
	/** [2] 게시글 수정
	 * @param board
	 * @return
	 */
	@ResponseBody
	@PutMapping("{boardCode:[2]}/board2Update")
	public int board2Update(@RequestBody Map<String, Object> board) {
		
		return service.board2Update(board);
	}
	
	
	/** [2] 댓글 등록
	 * @param commentMap
	 * @return
	 */
	@ResponseBody
	@PostMapping("{boardCode:[2]}/comment")
	public int board2Comment(@RequestBody Map<String, Object> commentMap) {
		
		return service.board2Comment(commentMap);
	}
	
	
	/** [2] 댓글 목록 조회
	 * @param boardNo
	 * @return
	 */
	@ResponseBody
	@GetMapping("{boardCode:[2]}/commentList2")
	public List<Comment> commentList(@RequestParam("boardNo") int boardNo){
		
		return service.board2CommentList(boardNo);
	}
  
	
	/** [2] 댓글 삭제
	 * @param commentNo
	 * @return
	 */
	@ResponseBody
	@DeleteMapping("{boardCode:[2]}/commentDelete")
	public int commentDelete(@RequestBody int commentNo) {
		
		return service.board2CommentDelete(commentNo);
	}
	
	/** [2] 댓글 수정
	 * @param commentMap
	 * @return
	 */
	@ResponseBody
	@PutMapping("{boardCode:[2]}/updateComment")
	public int commentUpdate(@RequestBody Map<String, Object> commentMap) {
		
		return service.board2CommentUpdate(commentMap);
	}
	
	
  
  
  
	/** [3] 팁과 노하우 게시글 목록 조회 + 검색 서비스
	 * @param boardCode
	 * @param cp
	 * @param model
	 * @param paramMap
	 * @return
	 */
	@GetMapping("{boardCode:[3]}")
	public String selectBoard3(@PathVariable("boardCode") int boardCode,
								@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
								Model model,
								@RequestParam Map<String, Object> paramMap) {
		
		Map<String, Object> map = null;
		
		if(paramMap.get("key") == null) {
			
			map = service.selectBoard3(boardCode, cp);
			
		} 
		
		else { // 검색인 경우 추후 구성
			
			paramMap.put("boardCode", boardCode); 
			
			// 검색 서비스 호출
			map = service.searchList3(paramMap, cp);
		}
		
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("boardList", map.get("boardList"));
		
		
		
		return "board/board3"; // board3.html로 forward
	    
	}
	
	
	/** [3] 팁과 노하우 게시글 상세 조회
	 * @return
	 */
	@GetMapping("{boardCode:[3]}/{boardNo:[0-9]+}")
	public String boardDetail3(
							@PathVariable("boardCode") int boardCode,
							@PathVariable("boardNo") int boardNo,
							Model model,
							RedirectAttributes ra,
							@SessionAttribute(value="loginMember", required=false) Member loginMember,
							HttpServletRequest req, // 요청에 담긴 쿠키 얻어오기
							HttpServletResponse resp // 새로운 쿠키 만들어서 응답하기
							) {
		
		// 1. 게시글 상세 조회
		Map<String, Integer> map = new HashMap<>();
		
		map.put("boardCode", boardCode);
		map.put("boardNo", boardNo);
		
		// 로그인 상태 (좋아요)
		if(loginMember != null) {
			map.put("memberNo", loginMember.getMemberNo());
		}
		
		// 상세 조회 서비스 호출
		Board board = service.selectOne3(map);
			
		
		String path = null;
		
		// 조회 결과 X
		if(board == null) {
			path = "redirect:/board/" + boardCode; // /board/3
			ra.addFlashAttribute("message", "게시글이 존재하지 않습니다.");
		
		// 조회 결과 O
		} else {
			
			// ------------ 조회수 증가(쿠키) --------------
			
			// 1. 비회원 or 글쓴이 X
			if(loginMember == null || loginMember.getMemberNo() != board.getMemberNo()) {
				
				Cookie[] cookies = req.getCookies();
				
				Cookie c = null;
				
				if(cookies != null) {
					for(Cookie ck : cookies) {
						
						if(ck.getName().equals("readBoardNo")) {
							c = ck;
							break;
						}
					}
				}
				
				
				int result = 0; // 결과값 변수
				
				String boardNoArr = "[" + boardNo + "]";
				
				if(c == null) {
					
					// "readBoardNo" 없을 경우 새 쿠키 생성 [] 
					c = new Cookie("readBoardNo", boardNoArr);
					result = service.readCount3(boardNo);
					
				} else {
					// "readBoardNo" 쿠키에 존재 O
					
					// 처음 읽은 경우(getValue는 특정 쿠키 값을 가져오는 용도로 사용됨)
					if(c.getValue().indexOf(boardNoArr) == -1) {
						
						// 현재 쿠키 + 해당 게시글 번호
						c.setValue(c.getValue() + boardNoArr);
						
						result = service.readCount3(boardNo);
					}
				}
				
				// 조회수 증가
				if(result > 0) {
					
					board.setReadCount(result);
					
					c.setPath("/");
					
					
					// ----- 쿠키 수명 ------
					LocalDateTime now = LocalDateTime.now(); // 현재
					LocalDateTime nextDayMidnight 
								= now.plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0); // 다음날 자정
					
					// 다음날 자정까지 남은 시간(초)
					long untilTime = Duration.between(now, nextDayMidnight).getSeconds();
					
					// 쿠키 수명 설정
					c.setMaxAge((int)untilTime);
					
					resp.addCookie(c);
					
				}
			}
			
			/* ************************ 쿠키를 이용한 조회수 증가 (끝) ************************ */
			
			// 이전글
			int beforePage = service.beforePage(map);
			
			// 다음글
			int afterPage = service.afterPage(map);


			model.addAttribute("beforePage", beforePage);
			model.addAttribute("afterPage", afterPage);
			

			path = "board/board3Detail"; // boardDetail3.html 포워드
			
			model.addAttribute("board", board); // 보드 실어서 전달
			
			// 이미지 추후 구성
			
			// --------------------
			
		}
	
		
		return path;
	}
	

	
}
