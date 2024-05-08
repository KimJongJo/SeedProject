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

	/** 자유 게시판의 지정된 페이지 목록 조회
	 * @param boardCode
	 * @param cp
	 * @return map
	 */
	Map<String, Object> selectBoardList1(int boardCode, int cp);
  
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

	/** 자유 게시판 검색 서비스
	 * 
	 * @param paramMap
	 * @param cp
	 * @return
	 */
	Map<String, Object> searchList1(Map<String, Object> paramMap, int cp);
  
  
	/** [3] 팁과 노하우 게시판 - 게시글 목록 조회
	 * @param boardCode
	 * @param cp
	 * @return
	 */
	Map<String, Object> selectBoard3(int boardCode, int cp);

	
  	/** [3] 팁과 노하우 게시판 - 게시글 검색
	 * @param paramMap
	 * @param cp
	 * @return
	 */
	Map<String, Object> searchList3(Map<String, Object> paramMap, int cp);

	/** [3] 팁과 노하우 게시판 - 게시글 상세 조회
	 * @param map
	 * @return
	 */
	Board selectOne3(Map<String, Integer> map);
}
