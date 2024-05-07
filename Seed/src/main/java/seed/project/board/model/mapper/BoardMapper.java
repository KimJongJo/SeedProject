package seed.project.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import seed.project.board.model.dto.Board;

@Mapper
public interface BoardMapper {

	/** 게시판 종류 조회
	 * @return boardTypeList
	 */
	List<Map<String, Object>> selectBoardTypeList();

	/** 자유 게시판 게시글 수 조회
	 * @param boardCode
	 * @return listCount
	 */
	int getListCount1(int boardCode);
  
  
  
	/** 문의 게시판 게시글 조회
	 * @return
	 */
	List<Board> selectBoard2List();

	
	
	/** 자유 게시판 검색 조건이 맞는 게시글 수 조회
	 * @param paramMap
	 * @return count
	 */
	int getSearchCount1(Map<String, Object> paramMap);

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

}
