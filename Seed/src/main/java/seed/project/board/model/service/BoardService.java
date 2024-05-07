package seed.project.board.model.service;

import java.util.List;
import java.util.Map;

import seed.project.board.model.dto.Board;

public interface BoardService {

	/** 게시판 종류 조회
	 * @return boardTypeList
	 */
	List<Map<String, Object>> selectBoardTypeList();

	/** 문의 게시판 게시글 조회
	 * @return
	 */
	List<Board> selectBoard2List();

}
