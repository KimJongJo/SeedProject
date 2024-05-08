package seed.project.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
		
		
		// 검색 안할 때
		if(paramMap.get("key") == null) { 
			
			// 게시글 목록 조회 서비스 호출
			map = service.selectBoardList1(boardCode, cp);
			
		// 검색할 때
		} else {
			
			paramMap.put("boardCode", boardCode);
			// -> paramMap은 {key=t, quer=검색어, boardCode=1}
			
			map = service.searchList1(paramMap, cp);
			
		}
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("boardList", map.get("boardList"));
		
		return "board/board1";
	}
	
	

	/** 자유게시판 상세조회
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
		
		String path = null;
		
	
		model.addAttribute("board", board);
			
			

		return "board/board1Detail";
	}
	
	
	
	
	
	
	
	
	
  
	  @GetMapping("{boardCode:[2]}")
	  public String board2(Model model,
							@RequestParam(value="cp", required = false, defaultValue="1") int cp
							) {
			
			Map<String, Object> map = service.selectBoard2List(2, cp); 
			
			if(map != null) {
				model.addAttribute("boardList", map.get("boardList"));
				model.addAttribute("pagination", map.get("pagination"));
			}
			
			
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
			
			//  쿠키 조회수 추후 구성
			
			/* ************************ 쿠키를 이용한 조회수 증가 (시작) ************************ */
			
			// 1. 비회원 또는 로그인한 회원의 글이 아닌 경우
			//    (글쓴이를 뺀 다른 사람)
			
			/*
			if(loginMember == null || 
					loginMember.getMemberNo() != board.getMemberNo()) {
				
				// 요청에 담겨있는 모든 쿠키 얻어오기
				Cookie[] cookies = req.getCookies();
				
				Cookie c = null;
				
				for(Cookie temp : cookies) {
					
					if(temp.getName().equals("readBoardNo")) {
						c = temp;
						break;
					}
					
				}
				
				int result = 0; // 조회수 증가 결과를 저장할 변수
				
				// "readBoardNo"가 쿠키에 없을 때
				if(c == null) {
					
					// 새 쿠키 생성 ("readBoardNo", [게시글 번호])
					c = new Cookie("readBoardNo", "[" + boardNo + "]");
					result = service.updateReadCount(boardNo);
					
				} else {
					// "readBoardNo"가 쿠키에 있을 때
					// "readBoardNo" : [2][30][400][2000][4000]
					
					// 현재 글을 처음 읽은 경우
					if(c.getValue().indexOf("[" + boardNo + "]") == -1) {
						
						// 해당 글 번호를 쿠키에 누적 + 서비스 호출
						c.setValue(c.getValue() + "[" + boardNo + "]" );
						result = service.updateReadCount(boardNo);
					}
				}
				
				// 조회수 증가 성공 / 조회 성공
				if(result > 0) {
					
					// 먼저 조회된 board의 readCount 값을
					// result 값으로 변환
					board.setReadCount(result);
					
					c.setPath("/"); // "/" 이하 경로 요청 시 쿠키 서버로 전달
					
					// 쿠키 수명 지정
					
					// 현재 시간을 얻어오기
					LocalDateTime now = LocalDateTime.now();
					
					// 다음날 자정
					LocalDateTime nextDayMidnight = now.plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
					
					// 다음날 자정까지 남은 시간 계산(초단위)
					long secondUntilNextDay = Duration.between(now, nextDayMidnight).getSeconds();
					
					// 쿠키 수명 설정
					c.setMaxAge((int)secondUntilNextDay);
					
					resp.addCookie(c); // 응답 객체를 이용해서 클라이언트에게 전달
					
				}
				
				
			} 
			
			*/
			/* ************************ 쿠키를 이용한 조회수 증가 (끝) ************************ */
			
			
			path = "board/board3Detail"; // boardDetail3.html 포워드
			
			model.addAttribute("board", board); // 보드 실어서 전달
			
			// 이미지 추후 구성
			
			// --------------------
			
		}
	
		
		return path;
	}
	
	
}
