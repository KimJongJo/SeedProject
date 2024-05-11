package seed.project.board.model.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;
import seed.project.board.model.dto.Board;
import seed.project.board.model.dto.Comment;

@Mapper
public interface BoardMapper1 {

	/** [공통] 게시판 종류 조회
	 * @return boardTypeList
	 */
	List<Map<String, Object>> selectBoardTypeList();

	
	/** [공통] 게시판 게시글 수 조회
	 * @param boardCode
	 * @return listCount
	 */
	int getListCount(int boardCode);
  
  
	/** [1] 자유 게시판 검색 조건이 맞는 게시글 수 조회
	 * @param paramMap
	 * @return count
	 */
	int getSearchCount1(Map<String, Object> paramMap);
  




	


	

	/** [1] 자유 게시판 검색한 게시글 목록 조회
	 * @param paramMap
	 * @param rowBounds
	 * @return
	 */
//	List<Board> getSearchCount1(Map<String, Object> paramMap, RowBounds rowBounds);
	
	/** 자유 게시판 목록 조회
	 * @param boardCode
	 * @param rowBounds
	 * @return
	 */
	List<Board> selectBoardList1(int boardCode, RowBounds rowBounds);

	/** 자유게시판 검색 결과 목록 조회
	 * @param paramMap
	 * @param rowBounds
	 * @return
	 */
	List<Board> selectSearchList1(Map<String, Object> paramMap, RowBounds rowBounds);



  
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




	/** [1] 삭제안되고 검색한 게시글 조회
	 * @param paramMap
	 * @return
	 */
	List<Board> selectBoardSearchList1(Map<String, Object> paramMap);


	/** [1] 댓글 조회
	 * @param boardNo
	 * @return commentList
	 */
	List<Comment> commentSelect(int boardNo);


	/** 댓글 작성
	 * @param comment
	 * @return
	 */
	int commentInsert(Comment comment);






}
