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
import seed.project.board.model.mapper.BoardMapper;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

	private final BoardMapper mapper;
	
	
	// [공통] 게시판 종류 조회
	@Override
	public List<Map<String, Object>> selectBoardTypeList() {
		return mapper.selectBoardTypeList();
	}

	
	// [3] 팁과 노하우 - 게시글 목록
	@Override
	public Map<String, Object> selectBoard3(int boardCode, int cp) {
		

		// 1. 삭제되지 않은 게시글 수를 조회
		int listCount = mapper.getListCount(boardCode);
		
		// Pagination 객체를 생성
		Pagination pagination = new Pagination(cp, listCount);
		
		
		// 3. 특정 게시판의 지정된 페이지 목록 조회
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		/* Mapper 메서드 호출 시
		 * - 첫 번째 매개변수 -> SQL에 전달할 파라미터
		 * - 두 번째 매개변수 -> RowBounds 객체 전달
		 */
		List<Board> boardList = mapper.selectBoard3(boardCode, rowBounds);
		
		
		// 4. 목록 조회 결과 + Pagination 객체를 Map으로 묶음
		Map<String, Object> map = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("boardList", boardList);
		
		return map;


	}
	
	
	// [3] 팁과 노하우 게시판 - 게시글 검색
	@Override
	public Map<String, Object> searchList3(Map<String, Object> paramMap, int cp) {
		
		int listCount = mapper.getSearchCount3(paramMap);
		
			
		Pagination pagination = new Pagination(cp, listCount);
		
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		

		List<Board> boardList = mapper.selectSearchList3(paramMap, rowBounds);
		
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("boardList", boardList);
		
		return map;
			
		

	}

	
	// [3] 팁과 노하우 게시판 - 게시글 상세 조회
	@Override
	public Board selectOne3(Map<String, Integer> map) {
		
		return mapper.selectOne3(map);
	}


	
	// [3] 팁과 노하우 게시판 - 조회수 증가(쿠키)
	@Override
	public int readCount3(int boardNo) {
		
		int result = mapper.readCount3(boardNo);
		
		if(result > 0) {
			
			return mapper.selectReadCount3(boardNo);
		}
		
		return -1;
	}

	
	// [3] 팁과 노하우 게시판 - 이전글
	@Override
	public int beforePage(Map<String, Integer> map) {
		
		return mapper.beforePage(map);
	}

	
	// [3] 팁과 노하우 게시판 - 다음글
	@Override
	public int afterPage(Map<String, Integer> map) {
		

		return mapper.afterPage(map);
	}



}

