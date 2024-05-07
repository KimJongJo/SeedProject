package seed.project.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import seed.project.board.model.dto.Pagination;
import seed.project.board.model.dto.Board;
import seed.project.board.model.dto.Comment;
import seed.project.board.model.mapper.BoardMapper;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

	private final BoardMapper mapper;
	
	
	// 게시판 종류 조회
	@Override
	public List<Map<String, Object>> selectBoardTypeList() {
		return mapper.selectBoardTypeList();
	}

	// 특정 게시판의 지정된 페이지 목록 조회
	@Override
	public Map<String, Object> selectBoardList(int boardCode, int cp) {
		
		// 게시글 수 조회
		int listCount = mapper.getListCount(boardCode);
		
		// listCount + cp
		Pagination pagination = new Pagination(cp, listCount);
		
		
		return null;
		
	}
	// 문의 게시판 게시글 조회
	@Override
	public Map<String, Object> selectBoard2List(int boardCode, int cp) {
		
		// 삭제 되지 않은 게시판
		int listCount = mapper.getListCount(boardCode);
		
		Pagination pagination = new Pagination(cp, listCount);
		
		
		int offset = (cp - 1) * pagination.getLimit();
		RowBounds rowBounds = new RowBounds(offset, pagination.getLimit());
		
		List<Board> boardList = mapper.selectBoard2List(boardCode, rowBounds);
		
		
		Map<String, Object> map = new HashMap<>();
		map.put("pagination", pagination);
		map.put("boardList", boardList);
		
		
		return map;
	}

	// 게시글 정보 받아오기
	@Override
	public Board board2Detail(int boardNo) {
		
		
		return mapper.board2Detail(boardNo);
	}

	
	
	// 게시글 댓글 정보
	@Override
	public List<Comment> board2CommentList(int boardNo) {
		
		return mapper.board2CommentList(boardNo);
	}
}
