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
	
	
	// 게시판 종류 조회
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

	// 문의 게시판 게시글 조회
	@Override
	public Map<String, Object> selectBoard2List(int boardCode, int cp) {
		
		// 삭제 되지 않은 게시판
		int listCount = mapper.getListCount(boardCode);
		
		
		// 게시글이 존재할때 페이징 객체 생성  
		if(listCount > 0) {
			Pagination pagination = new Pagination(cp, listCount);
			
			int offset = (cp - 1) * pagination.getLimit();
			RowBounds rowBounds = new RowBounds(offset, pagination.getLimit());
			
			List<Board> boardList = mapper.selectBoard2List(boardCode, rowBounds);
			
			
			Map<String, Object> map = new HashMap<>();
			map.put("pagination", pagination);
			map.put("boardList", boardList);
			
			return map;
		}
		
		
		return null;
		
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
	
	
	// [3] 팁과 노하우 - 게시글 목록
	@Override
	public Map<String, Object> selectBoard3(int boardCode, int cp) {
		
		// 1. 지정된 게시판(boardCode)에서
		//    삭제되지 않은 게시글 수를 조회
		int listCount = mapper.getListCount(boardCode);
		
		
		// 2. 1번의 결과 + cp를 이용해서
		//    Pagination 객체를 생성
		// * Pagination 객체 : 게시글 목록 구성에 필요한 값을 저장한 객체
		Pagination pagination = new Pagination(cp, listCount);
		
		
		// 3. 특정 게시판의 지정된 페이지 목록 조회
		/* ROWBOUNDS 객체 (MyBatis 제공 객체)
		 * - 지정된 크기(offset)만큼 건너뛰고 
		 * - 제한된 크기(limit)만큼의 행을 조회하는 객체
		 * 
		 * --> 페이징 처리가 굉장히 간단해짐!
		 */
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		/* Mapper 메서드 호출 시
		 * - 첫 번째 매개변수 -> SQL에 전달할 파라미터
		 * - 두 번째 매개변수 -> RowBounds 객체 전달
		 *
		 */
		List<Board> boardList = mapper.selectBoard3(boardCode, rowBounds);
		
		
		// 4. 목록 조회 결과 + Pagination 객체를 Map으로 묶음
		Map<String, Object> map = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("boardList", boardList);
		
		// 5. 결과 반환
		return map;

	}
	
	
	// [3] 팁과 노하우 게시판 - 게시글 검색
	@Override
	public Map<String, Object> searchList3(Map<String, Object> paramMap, int cp) {
		
		// 1. 지정된 게시판(boardCode)에서
		//	  검색 조건에 맞으면서
		//    삭제되지 않은 게시글 수를 조회
		int listCount = mapper.getSearchCount3(paramMap);
		
		
		// 2. 1번의 결과 + cp를 이용해서
		//    Pagination 객체를 생성
		// * Pagination 객체 : 게시글 목록 구성에 필요한 값을 저장한 객체
		Pagination pagination = new Pagination(cp, listCount);
		
		
		// 3. 특정 게시판의 지정된 페이지 목록 조회
		/* ROWBOUNDS 객체 (MyBatis 제공 객체)
		 * - 지정된 크기(offset)만큼 건너뛰고 
		 * - 제한된 크기(limit)만큼의 행을 조회하는 객체
		 * 
		 * --> 페이징 처리가 굉장히 간단해짐!
		 */
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		/* Mapper 메서드 호출 시
		 * - 첫 번째 매개변수 -> SQL에 전달할 파라미터
		 * - 두 번째 매개변수 -> RowBounds 객체 전달
		 *
		 */
		List<Board> boardList = mapper.selectSearchList3(paramMap, rowBounds);
		
		
		// 4. 목록 조회 결과 + Pagination 객체를 Map으로 묶음
		Map<String, Object> map = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("boardList", boardList);
		
		// 5. 결과 반환
		return map;
	}

	// 자유 게시판 검색 서비스
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

	
	


}

