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
	int board3Write(Board inputBoard, List<MultipartFile> images) throws IllegalStateException, IOException;


	/** [3] 팁과 노하우 - 좋아요
	 * @param map
	 * @return
	 */
	int boardLike3(Map<String, Integer> map);


	/** [3] 팁과 노하우 - 게시글 수정
	 * @param inputBoard
	 * @param images
	 * @param deleteOrder
	 * @return
	 */
	int board3Update(Board inputBoard, List<MultipartFile> images, String deleteOrder) throws IllegalStateException, IOException;


	/** [3] 팁과 노하우 - 게시글 삭제
	 * @param boardNo
	 * @return
	 */
	int board3Delete(int boardNo);


	// ----------------------------------------------
	
	/** [3] 팁과 노하우 - 목록 댓글 조회
	 * @param boardNo
	 * @return
	 */
	List<Comment> commentSelect3(int boardNo);


	/** [3] 팁과 노하우 - 댓글/답글 등록
	 * @param comment
	 * @return
	 */
	int commentInsert3(Comment comment);


	/** [3] 팁과 노하우 - 댓글 수정
	 * @param comment
	 * @return
	 */
	int commentUpdate3(Comment comment);


	/** [3] 팁과 노하우 - 댓글 삭제
	 * @param comment
	 * @return
	 */
	int commentDelete3(int commentNo);


	
	




}
