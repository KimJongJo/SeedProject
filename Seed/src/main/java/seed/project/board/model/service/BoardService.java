package seed.project.board.model.service;

import java.util.List;
import java.util.Map;

import seed.project.board.model.dto.Board;
import seed.project.board.model.dto.Comment;

public interface BoardService {

	/** 게시판 종류 조회
	 * @return boardTypeList
	 */
	List<Map<String, Object>> selectBoardTypeList();

	/** 특정 게시판의 지정된 페이지 목록 조회
	 * @param boardCode
	 * @param cp
	 * @return map
	 */
	Map<String, Object> selectBoardList(int boardCode, int cp);
  
	/** 문의 게시판 게시글 조회
	 * @param cp 
	 * @param boardCode 
	 * @return
	 */
	Map<String, Object> selectBoard2List(int boardCode, int cp);

	/** 게시글 정보
	 * @param boardNo
	 * @return
	 */
	Board board2Detail(int boardNo);

	/** 게시글 댓글 정보
	 * @param boardNo
	 * @return
	 */
	List<Comment> board2CommentList(int boardNo);
}
