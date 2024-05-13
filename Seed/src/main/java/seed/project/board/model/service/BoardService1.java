package seed.project.board.model.service;

import java.util.List;
import java.util.Map;
import seed.project.board.model.dto.Board;
import seed.project.board.model.dto.Comment;

public interface BoardService1 {

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
  



	/** 자유 게시판 검색 서비스
	 * 
	 * @param paramMap
	 * @param cp
	 * @return
	 */
	Map<String, Object> searchList1(Map<String, Object> paramMap, int cp);
  
  

  
	/** [1] 자유 게시판 - 게시글 상세조회
	 * @param map
	 * @return
	 */
	Board selectOne1(Map<String, Object> map);




	/** [1] 게시글 수정하기
	 * @param board
	 * @return
	 */
	int board1Update(Map<String, Object> board);

	/** [1] 게시글 삭제하기
	 * @param boardNo
	 * @return
	 */
	int board1Delete(int boardNo);

	/** [1] 게시판 목록 조회 (검색했을 때)
	 * @param paramMap
	 * @param cp
	 * @return
	 */
	Map<String, Object> selectBoardSearchList1(Map<String, Object> paramMap, int cp);

	/** 댓글 조회
	 * @param boardNo
	 * @return
	 */
	List<Comment> commentSelect(int boardNo);

	/** 댓글 작성
	 * @param comment
	 * @return
	 */
	int commentInsert(Comment comment);

	/** 게시글 작성
	 * @param inputBoard
	 * @return
	 */
	int boardInsert(Board inputBoard);


}
