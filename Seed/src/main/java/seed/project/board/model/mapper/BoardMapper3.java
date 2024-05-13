package seed.project.board.model.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;
import seed.project.board.model.dto.Board;
import seed.project.board.model.dto.BoardImg;
import seed.project.board.model.dto.Comment;

@Mapper
public interface BoardMapper3 {

	/** [공통] 게시판 종류 조회
	 * @return boardTypeList
	 */
	List<Map<String, Object>> selectBoardTypeList();

	
	/** [공통] 게시판 게시글 수 조회
	 * @param boardCode
	 * @return listCount
	 */
	int getListCount(int boardCode);
  
  
	
	
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



	/** [3] 팁과 노하우 게시판 - 게시글 상세 조회
	 * @param map
	 * @return
	 */
	Board selectOne3(Map<String, Integer> map);


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


	/** [3] 팁과 노하우 게시판 - 게시글 작성
	 * @param inputBoard
	 * @return
	 */
	int board3Write(Board inputBoard);


	/** [3] 팁과 노하우 - 게시글 이미지 모두 삽입
	 * @param uploadList
	 * @return
	 */
	int insertUploadList3(List<BoardImg> uploadList);


	
	/** [3] 팁과 노하우 -  좋아요 해제
	 * @param map
	 * @return
	 */
	int deleteBoardLike3(Map<String, Integer> map);


	/** [3] 팁과 노하우 - 좋아요 체크
	 * @param map
	 * @return
	 */
	int insertBoardLike3(Map<String, Integer> map);


	/** [3] 팁과 노하우 - 좋아요 개수 조회
	 * @param integer
	 * @return
	 */
	int selectLikeCount3(Integer integer);


	/** [3] 팁과 노하우 - 제목/내용 (부분) 수정
	 * @param inputBoard
	 * @return
	 */
	int board3Update(Board inputBoard);


	/** [3] 팁과 노하우 - 게시글 이미지 삭제
	 * @param map
	 * @return
	 */
	int deleteImage3(Map<String, Object> map);


	/** [3] 팁과 노하우 - 게시글 이미지 수정
	 * @param img
	 * @return
	 */
	int updateImage3(BoardImg img);


	/** [3] 팁과 노하우 - 게시글 이미지 삽입
	 * @param img
	 * @return
	 */
	int insertImage3(BoardImg img);


	/** [3] 팁과 노하우 - 게시글 삭제
	 * @param boardNo
	 * @return
	 */
	int board3Delete(int boardNo);


	// -----------------------------------------
	
	/** [3] 팁과 노하우 - 댓글 목록 조회
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
