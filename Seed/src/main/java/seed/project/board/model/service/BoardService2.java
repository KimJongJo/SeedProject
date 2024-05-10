package seed.project.board.model.service;

import java.util.List;
import java.util.Map;
import seed.project.board.model.dto.Board;
import seed.project.board.model.dto.Comment;

public interface BoardService2 {

	/** 게시판 종류 조회
	 * @return boardTypeList
	 */
	List<Map<String, Object>> selectBoardTypeList();

  
	/**[2] 문의 게시판 게시글 조회
	 * @param cp 
	 * @param boardCode 
	 * @return
	 */
	Map<String, Object> selectBoard2List(int boardCode, int cp);

	/**[2] 게시글 정보
	 * @param boardNo
	 * @return
	 */
	Board board2Detail(int boardNo);

	/** [2] 게시글 댓글 정보
	 * @param boardNo
	 * @return
	 */
	List<Comment> board2CommentList(int boardNo);

	


	/** [2] 게시글 삭제(업데이트)
	 * @param boardNo
	 * @return
	 */
	int board2Delete(int boardNo);

	/** [2] 게시글 작성하기
	 * @param board
	 * @return
	 */
	int board2Write(Map<String, String> board);

	/** [2] 게시글 정보 가져오기
	 * @param boardNo
	 * @return
	 */
	Board board2Info(int boardNo);

	/** [2] 게시글 수정하기
	 * @param board
	 * @return
	 */
	int board2Update(Map<String, Object> board);


	/** [2] 문의 게시판 검색
	 * @param paramMap
	 * @param cp
	 * @return
	 */
	Map<String, Object> selectBoard2SearchList(Map<String, Object> paramMap, int cp);


	/** [2] 댓글 달기
	 * @param commentMap
	 * @return
	 */
	int board2Comment(Map<String, Object> commentMap);

	/** [2] 댓글 삭제
	 * @param commentNo
	 * @return
	 */
	int board2CommentDelete(int commentNo);


	/** [2] 댓글 수정하기
	 * @param commentMap
	 * @return
	 */
	int board2CommentUpdate(Map<String, Object> commentMap);

	/** [2] 댓글 좋아요
	 * @param likeMap
	 * @return
	 */
	int board2Like(Map<String, Integer> likeMap);

	/** [2] 로그인을 했을때 좋아요 체크 여부
	 * @param likeMap
	 * @return
	 */
	int board2LikeCheck(Map<String, Integer> likeMap);


}
