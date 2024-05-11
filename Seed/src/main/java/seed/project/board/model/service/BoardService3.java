package seed.project.board.model.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import seed.project.board.model.dto.Board;
import seed.project.board.model.dto.Comment;
import seed.project.board.model.exception.BoardInsertException;

public interface BoardService3 {

	/** 게시판 종류 조회
	 * @return boardTypeList
	 */
	List<Map<String, Object>> selectBoardTypeList();

  
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

	/** [3] 팁과 노하우 게시판 - 게시글 작성
	 * @param inputBoard
	 * @param images
	 * @return
	 */
	int boardWrite3(Board inputBoard, List<MultipartFile> images) throws IllegalStateException, IOException;


	
	




}
