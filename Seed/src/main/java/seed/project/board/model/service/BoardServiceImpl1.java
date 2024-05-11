package seed.project.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import seed.project.board.model.dto.Board;
import seed.project.board.model.dto.Comment;
import seed.project.board.model.dto.Pagination;
import seed.project.board.model.mapper.BoardMapper1;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl1 implements BoardService1{

	private final BoardMapper1 mapper;
	
	
	// [공통] 게시판 종류 조회
	@Override
	public List<Map<String, Object>> selectBoardTypeList() {
		return mapper.selectBoardTypeList();
	}

	// 자유 게시판의 지정된 페이지 목록 조회
	@Override
	public Map<String, Object> selectBoardList1(int boardCode, int cp) {
		
		// 게시글 수 조회
		int listCount = mapper.getListCount(boardCode);
		
			
		// listCount + cp
		Pagination pagination = new Pagination(cp, listCount);
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		
		List<Board> boardList = mapper.selectBoardList1(boardCode, rowBounds);
		
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("boardList", boardList);
		
		return map;
	}


		
	

	// [1] 자유 게시판 검색 서비스
	@Override
	public Map<String, Object> searchList1(Map<String, Object> paramMap, int cp) {
		
		
		// 1. 자유 게시판에서 삭제되지 않은 게시글 수 조회
		int listCount = mapper.getSearchCount1(paramMap);
		// 검색된 게시글이 몇개인지 알아야 Pagination을 만들 수 있음
		
		// 2. listCount + cp (전체게시글수, 현제페이지번호)
		Pagination pagination = new Pagination(cp, listCount);
		
		// 3. 자유게시판의 지정된 페이지 목록 조회
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		
		List<Board> boardList = mapper.selectSearchList1(paramMap, rowBounds);
		
		// 목록 조회 결과 + Pagination 을 map으로 묶음
		Map<String, Object> map = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("boardList", boardList);
		
		
		return map;
	}
	
	

	
	// [1] 자유 게시판 - 게시글 상세조회
	@Override
	public Board selectOne1(Map<String, Object> map) {
		return mapper.selectOne1(map);
	}




	// [1] 게시글 수정하기
	@Override
	public int board1Update(Map<String, Object> board) {
		return mapper.board1Update(board);
	}

	// [1] 게시글 삭제하기
	@Override
	public int board1Delete(int boardNo) {
		return mapper.board1Delete(boardNo);
	}

	@Override
	public Map<String, Object> selectBoardSearchList1(Map<String, Object> paramMap, int cp) {
		// TODO Auto-generated method stub
		return null;
	}

	// [1] 댓글 조회
	@Override
	public List<Comment> commentSelect(int boardNo) {
		return mapper.commentSelect(boardNo);
		
	}

	// 댓글 작성
	@Override
	public int commentInsert(Comment comment) {
		return mapper.commentInsert(comment);
		
	}


	


}

