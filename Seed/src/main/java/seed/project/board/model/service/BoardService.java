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
  
	/** [1] 자유 게시판 - 게시글 상세조회
	 * @param map
	 * @return
	 */
	Board selectOne1(Map<String, Object> map);

	
	/** [3] 팁과 노하우 게시판 - 조회수 증가(쿠키)
	 * @param boardNo
	 * @return
	 */
	int readCount3(int boardNo);

	/** [3] 팁과 노하우 게시판 - 이전글
	 * @param map
	 * @return
	 */
	int beforePage(Map<String, Integer> map);

	/** [3] 팁과 노하우 게시판 - 다음글
	 * @param map
	 * @return
	 */
	int afterPage(Map<String, Integer> map);

	


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

	

}
