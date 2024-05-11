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
import seed.project.board.model.mapper.BoardMapper2;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl2 implements BoardService2{

	private final BoardMapper2 mapper;
	
	
	// [공통] 게시판 종류 조회
	@Override
	public List<Map<String, Object>> selectBoardTypeList() {
		return mapper.selectBoardTypeList();
	}


	// [2] 문의 게시판 게시글 조회
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

	// [2] 게시글 정보 받아오기
	@Override
	public Board board2Detail(int boardNo) {
		
		
		return mapper.board2Detail(boardNo);
	}

	
	
	// [2] 게시글 댓글 정보
	@Override
	public List<Comment> board2CommentList(int boardNo) {
		
		return mapper.board2CommentList(boardNo);
	}
	
	
	
	
	// [2] 게시글 삭제(업데이트)
	@Override
	public int board2Delete(int boardNo) {
		
		return mapper.board2Delete(boardNo);
	}

	// [2] 게시글 작성하기
	@Override
	public int board2Write(Map<String, String> board) {

		return mapper.board2Write(board);
	}

	
	// [2] 게시글 정보 가져오기
	@Override
	public Board board2Info(int boardNo) {
		
		return mapper.board2Info(boardNo);
	}

	// [2] 게시글 수정하기
	@Override
	public int board2Update(Map<String, Object> board) {
		
		return mapper.board2Update(board);
	}

	// [2] 문의 게시판 검색
	@Override
	public Map<String, Object> selectBoard2SearchList(Map<String, Object> paramMap, int cp) {
		
		// 삭제되지 않은 게시글 검색
		List<Board> boardList = mapper.getSearchCount2(paramMap);
		
		// 게시글이 존재할 때 페이징 객체 생성
		if(boardList.size() > 0) {
			Pagination pagination = new Pagination(cp, boardList.size());
			
			int offset = (cp - 1) * pagination.getLimit();
			RowBounds rowBounds = new RowBounds(offset, pagination.getLimit());

			// 반환되는 boardList2
			List<Board> boardList2 = mapper.getSearchCount2(paramMap, rowBounds);
			
			
			Map<String, Object> map = new HashMap<>();
			map.put("pagination", pagination);
			map.put("boardList", boardList2);
			
			return map;
		}
		
		return null;
	}

	

	// [2] 댓글 달기
	@Override
	public int board2Comment(Map<String, Object> commentMap) {
		
		return mapper.board2Comment(commentMap);
	}

	// [2] 댓글 삭제
	@Override
	public int board2CommentDelete(int commentNo) {
		
		return mapper.board2CommentDelete(commentNo);
	}



	// [2] 댓글 수정하기
	@Override
	public int board2CommentUpdate(Map<String, Object> commentMap) {
		
		return mapper.board2CommentUpdate(commentMap);
	}

	// [2] 댓글 좋아요
	@Override
	public int board2Like(Map<String, Integer> likeMap) {
		
		// 좋아요 여부 1이면 좋아요, 0이면 좋아요 안누름
		int boardLike = mapper.board2Like(likeMap);
		
		// 좋아요 누른 상태 -> 좋아요 취소
		if(boardLike > 0) {
			mapper.board2LikeBack(likeMap);
		// 좋아요 안누른 상태 -> 좋아요 체크
		}else {
			mapper.board2LikePush(likeMap);
		}
		
		return mapper.board2LikeCount(likeMap.get("boardNo"));
		
	}

	// [2] 로그인 했을때 그 계정에 대한 좋아요 체크 여부
	@Override
	public int board2LikeCheck(Map<String, Integer> likeMap) {
		
		return mapper.board2Like(likeMap);
	}


	// [2] 조회수 증가
	@Override
	public void boardView(int boardNo) {
		
		mapper.boardView(boardNo);
	}


	// [2] 게시글 글쓴이 번호
	@Override
	public int boardWriter(int boardNo) {
		
		return mapper.boardWriter(boardNo);
	}


}

