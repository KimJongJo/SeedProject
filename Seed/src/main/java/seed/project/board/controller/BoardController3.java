package seed.project.board.controller;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import seed.project.board.model.dto.Board;
import seed.project.board.model.dto.BoardImg;
import seed.project.board.model.dto.Comment;
import seed.project.board.model.service.BoardService3;
import seed.project.member.model.dto.Member;

@Controller
@RequestMapping("board")
@Slf4j
@RequiredArgsConstructor
public class BoardController3 {

	private final BoardService3 service;
	
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
			
			model.addAttribute("imageList", board.getImageList());
			// 이미지 추후 구성
			
			
			
			// 조회된 이미지 목록(imageList)가 있을 경우
			if( !board.getImageList().isEmpty() ) {
				
				BoardImg thumbnail = null;
				
				// imageList의 0번 인덱스 == 가장 빠른 순서(imgOrder)
				
				// 이미지 목록의 첫번째 행이 순서 0 == 썸네일인 경우
				if(board.getImageList().get(0).getBoardImgOrder() == 0) {
					
					thumbnail = board.getImageList().get(0);
					
				}
				
				model.addAttribute("thumbnail", thumbnail);
				
	
				
			} else {
				
				model.addAttribute("thumbnail", null);
			}
			
		}
	
		
		return path;
	}
	
	
	/** [3] 팁과 노하우 - 게시글 작성 페이지로 이동
	 * @param boardCode
	 * @return
	 */
	@GetMapping("{boardCode:[3]}/write")
	public String board3Write(@PathVariable("boardCode") int boardCode) {
		
		return "board/board3Write";
	}
	
	
	/** [3] 팁과 노하우 - 게시글 작성
	 * @param boardCode
	 * @param inputBoard
	 * @param loginMember
	 * @param images
	 * @param ra
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@PostMapping("{boardCode:[3]}/write")
	public String board3Write(
					@PathVariable("boardCode") int boardCode,
					@ModelAttribute Board inputBoard,
					@SessionAttribute("loginMember") Member loginMember,
					@RequestParam("images") List<MultipartFile> images,
					RedirectAttributes ra) throws IllegalStateException, IOException {
		
		
		// 1. 게시판 코드, 회원 번호 세팅
		inputBoard.setBoardCode(boardCode);
		inputBoard.setMemberNo(loginMember.getMemberNo());
		
		// 2. 삽입된 게시글 번호 반환 받기
		int boardNo = service.board3Write(inputBoard, images);
		
		// 3. 서비스 결과에 따라 message, 리다이렉트 경로 지정
		String path = null;
		String message = null;
		
		if(boardNo > 0) {
			path = "/board/" + boardCode + "/" + boardNo;
			message = boardNo + "번 게시글이 작성 되었습니다!";
		
		} else {
			path = "write";
			message = "게시글 작성 실패..";
	
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}

	
	/** [3] 팁과 노하우 - 게시글 좋아요
	 * @param boardCode
	 * @param map
	 * @return
	 */
	@ResponseBody
	@PostMapping("{boardCode:[3]}/like")
	public int boardLike3(@PathVariable("boardCode") int boardCode,
							@RequestBody Map<String, Integer> map) {
		
		return service.boardLike3(map);
	} 
	
	
	/** [3] 팁과 노하우 - 게시글 수정 화면 전환
	 * @param boardCode
	 * @param boardNo
	 * @param loginMember
	 * @param model
	 * @param ra
	 * @return
	 */
	@GetMapping("{boardCode:[3]}/{boardNo:[0-9]+}/update")
	public String board3Update(
					@PathVariable("boardCode") int boardCode,
					@PathVariable("boardNo") int boardNo,
					@SessionAttribute("loginMember") Member loginMember,
					Model model, 
					RedirectAttributes ra
					) {
		// 수정 화면에 출력할 기존의 제목/내용/이미지 조회
		// -> 게시글 상세 조회
		Map<String, Integer> map = new HashMap<>();
		map.put("boardCode", boardCode);
		map.put("boardNo", boardNo);
		
		// BoardService.selectOne(map) 호출
		Board board = service.selectOne3(map);

		String message = null;
		String path = null;
		 
		if(board == null) {
			message = "해당 게시글이 존재하지 않습니다.";
			path = "redirect:/"; // 메인 페이지
			
			ra.addFlashAttribute("message", message);
			
		} else if(board.getMemberNo() != loginMember.getMemberNo()) {
			message = "본인이 작성한 글만 수정할 수 있습니다.";
			
			// 해당 글 상세조회 리다이렉트
			path = String.format("redirect:/board/%d/%d", boardCode, boardNo);
			
			ra.addFlashAttribute("message", message);
			
		} else {
			
			path = "board/board3Update"; // templates/board/boardUpdate.html로 forward
			model.addAttribute("board", board);
		}
		
		return path;

	}
	
	/** [3] 팁과 노하우 - 게시글 수정
	 * @param boardCode
	 * @param boardNo
	 * @param inputBoard
	 * @param loginMember
	 * @param images
	 * @param ra
	 * @param deleteOrder
	 * @param querystring
	 * @return
	 */
	@PostMapping("{boardCode:[3]}/{boardNo:[0-9]+}/update")
	public String board3Update(
					@PathVariable("boardCode") int boardCode,
					@PathVariable("boardNo") int boardNo,
					@ModelAttribute Board inputBoard,
					@SessionAttribute("loginMember") Member loginMember,
					@RequestParam("images") List<MultipartFile> images,
					RedirectAttributes ra,
					@RequestParam(value="deleteOrder", required = false) String deleteOrder,
					@RequestParam(value="querystring", required = false, defaultValue = "") String querystring
							
					) throws IllegalStateException, IOException {
		
		// 수정하려는 게시글에 게시글 정보 및 회원 번호 세팅
		inputBoard.setBoardCode(boardCode);
		inputBoard.setBoardNo(boardNo);
		inputBoard.setMemberNo(loginMember.getMemberNo());
		
		// 서비스 호출
		int result = service.board3Update(inputBoard, images, deleteOrder);
		
		String message = null;
		String path = null;
		
		if(result > 0) {
			message = boardNo + "번 게시글이 수정되었습니다.";
			path = String.format("/board/%d/%d%s", boardCode, boardNo, querystring); // /board/1/2000?cp=3
		} else {
			message = "수정 실패..";
			path = "update"; // 수정 화면 전환 리다이렉트하는 상대 경로
			
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
		
	}
	
	/** [3] 팁과 노하우 - 게시글 삭제
	 * @param boardCode
	 * @param boardNo
	 * @return
	 */
	@ResponseBody
	@DeleteMapping("{boardCode:[3]}/delete")
	public int board3Delete(@PathVariable("boardCode") int boardCode,
							@RequestBody int boardNo) {
		
		return service.board3Delete(boardNo);
	}
	
	
	// ------------------------------------------------------------------------
	
	/** [3] 팁과 노하우 - 댓글 목록 조회
	 * @param boardNo
	 * @return
	 */
	@ResponseBody
	@GetMapping("{boardCode:[3]}/comment")
	public List<Comment> commentSelect3(@RequestParam("boardNo") int boardNo) {
		
		return service.commentSelect3(boardNo);
	}
	
	
	/** [3] 팁과 노하우 - 댓글 답글 등록
	 * @param comment
	 * @return
	 */
	@ResponseBody
	@PostMapping("{boardCode:[3]}/comment")
	public int commentInsert3(@RequestBody Comment comment) {
		
		return service.commentInsert3(comment);
	}
	
	
	
	/** [3] 팁과 노하우 - 댓글 수정
	 * @param comment
	 * @return
	 */
	@ResponseBody
	@PutMapping("{boardCode:[3]}/comment")
	public int commentUpdate3(@RequestBody Comment comment) {
		
		return service.commentUpdate3(comment);
	}
	
	/** [3] 팁과 노하우 - 댓글 삭제
	 * @param comment
	 * @return
	 */
	@ResponseBody
	@DeleteMapping("{boardCode:[3]}/comment")
	public int commentDelete3(@RequestBody int commentNo) {
		
		return service.commentDelete3(commentNo);
	}
	
	
}
