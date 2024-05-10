package seed.project.board.model.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;
import seed.project.board.model.dto.Board;
import seed.project.board.model.dto.Comment;

@Mapper
public interface BoardMapper {

	/** [공통] 게시판 종류 조회
	 * @return boardTypeList
	 */
	List<Map<String, Object>> selectBoardTypeList();

	
	/** [공통] 게시판 게시글 수 조회
	 * @param boardCode
	 * @return listCount
	 */
	int getListCount(int boardCode);
  
  
	
	/** 게시글 정보 가져오기
	 * @param boardNo
	 * @return
	 */
	Board board2Detail(int boardNo);

	
	
	/** [3] 팁과 노하우 게시글 목록 조회
	 * @param boardCode
	 * @param rowBounds
	 * @return
	 */
	List<Board> selectBoard3(int boardCode, RowBounds rowBounds);

	
	
	/** [3] 팁과 노하우 검색 조건이 맞는 게시글 수 조회
	 * @param paramMap
	 * @return
	 */
	int getSearchCount3(Map<String, Object> paramMap);

	
	/** [3] 팁과 노하우 검색 결과 목록 조회
	 * @param paramMap
	 * @param rowBounds
	 * @return
	 */
	List<Board> selectSearchList3(Map<String, Object> paramMap, RowBounds rowBounds);


	
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


	

	/** [3] 팁과 노하우 게시판 - 조회수 1 증가
	 * @param boardNo
	 * @return
	 */
	int readCount3(int boardNo);


	/** [3] 팁과 노하우 게시판 - 조회수 조회
	 * @param boardNo
	 * @return
	 */
	int selectReadCount3(int boardNo);


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
