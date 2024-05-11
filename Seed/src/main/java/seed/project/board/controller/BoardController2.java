package seed.project.board.controller;

import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.SessionAttribute;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import seed.project.board.model.dto.Board;
import seed.project.board.model.dto.Comment;
import seed.project.board.model.service.BoardService3;
import seed.project.board.model.service.BoardService2;
import seed.project.member.model.dto.Member;

@Controller
@RequestMapping("board")
@Slf4j
@RequiredArgsConstructor
public class BoardController2 {

	private final BoardService2 service;
	
	
	
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
				model.addAttribute("cp", cp);
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
			  			@SessionAttribute(value="loginMember", required=false) Member loginMember,
			  			Model model,
			  			@RequestParam(value="cp", required=false, defaultValue = "1") int cp,
			  			HttpServletRequest request,
			  			HttpServletResponse response
			  			) {
		
		// 조회수 증가
		Cookie[] cookies = request.getCookies();
		
		Cookie checkCookie = null;
		
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals("checkCookie")) {
				checkCookie = cookie;
				
				// 현재 게시글 번호가 없는 경우
				if(!checkCookie.getValue().contains("[" + boardNo + "]")) {
					
					// 게시글의 글쓴이 번호 가져오기
					int memberNo = service.boardWriter(boardNo);
					
					if(loginMember == null || loginMember.getMemberNo() != memberNo) {
						// 조회수 증가 시키는 메서드 생성
						service.boardView(boardNo);
						checkCookie.setValue(checkCookie.getValue() + "[" + boardNo + "]");
					}
					
				}
				break;
			}
		}
		
		if(checkCookie == null) {
			service.boardView(boardNo);
			checkCookie = new Cookie("checkCookie", "[" + boardNo + "]");
		}
		
		// 쿠키 유효 기간
		checkCookie.setMaxAge(60 * 60 * 24);
		response.addCookie(checkCookie);
		
		  
		  Board boardInfo = service.board2Detail(boardNo);
		  List<Comment> commentList = service.board2CommentList(boardNo);
		  
		  // 로그인을 했을 경우 좋아요를 눌렀는지 체크 여부
		  if(loginMember != null) {
			  Map<String, Integer> likeMap = new HashMap<>();
			  likeMap.put("boardNo", boardNo);
			  likeMap.put("memberNo", loginMember.getMemberNo());
			  
			  int likeCheck = service.board2LikeCheck(likeMap);
			  
			  if(likeCheck > 0) {
				  model.addAttribute("likeOn", 1);
			  }else {
				  model.addAttribute("likeOn", 0);
			  }
		  }
		  
		  		
		  model.addAttribute("boardInfo", boardInfo);
		  model.addAttribute("commentList", commentList);
		  model.addAttribute("cp", cp);
		  
		  
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
	
	
	/** [2] 게시글 좋아요
	 * @param likeMap
	 * @return
	 */
	@ResponseBody
	@PostMapping("{boardCode:[2]}/board2Like")
	public int boardLike(@RequestBody Map<String, Integer> likeMap) {
		
		return service.board2Like(likeMap);
	}
	
}
