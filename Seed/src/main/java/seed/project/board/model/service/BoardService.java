package seed.project.board.model.service;

import java.util.List;
import java.util.Map;

import seed.project.board.model.dto.Board;

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
	 * @return
	 */
	List<Board> selectBoard2List();

	
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
}
