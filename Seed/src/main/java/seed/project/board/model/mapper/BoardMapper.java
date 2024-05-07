package seed.project.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import seed.project.board.model.dto.Board;
import seed.project.board.model.dto.Comment;

@Mapper
public interface BoardMapper {

	/** 게시판 종류 조회
	 * @return boardTypeList
	 */
	List<Map<String, Object>> selectBoardTypeList();

	/** 게시글 수 조회
	 * @param boardCode
	 * @return listCount
	 */
	int getListCount(int boardCode);
  
  
  
	/** 문의 게시판 게시글 조회
	 * @param rowBounds 
	 * @param boardCode 
	 * @return
	 */
	List<Board> selectBoard2List(int boardCode, RowBounds rowBounds);

	/** 게시글 정보 가져오기
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
