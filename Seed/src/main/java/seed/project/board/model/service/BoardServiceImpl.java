package seed.project.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import seed.project.board.model.dto.Pagination;
import seed.project.board.model.dto.Board;
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
	// 문의 게시판 게시글 조회
	@Override
	public List<Board> selectBoard2List() {
		
		return mapper.selectBoard2List();
	}
}
