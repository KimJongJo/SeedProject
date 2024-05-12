package seed.project.board.model.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import seed.project.board.model.dto.Board;
import seed.project.board.model.dto.BoardImg;
import seed.project.board.model.dto.Comment;
import seed.project.board.model.dto.Pagination;
import seed.project.board.model.exception.BoardInsertException;
import seed.project.board.model.exception.ImageDeleteException;
import seed.project.board.model.exception.ImageUpdateException;
import seed.project.board.model.mapper.BoardMapper3;
import seed.project.common.util.Utility;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl3 implements BoardService3{

	private final BoardMapper3 mapper;
	
	// config.properties
	@Value("${my.board.web-path}")
	private String webPath;
	
	@Value("${my.board.folder-path}")
	private String folderPath;
	
	
	
	
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

	
	// [3] 팁과 노하우 게시판 - 게시글 작성
	@Override
	public int board3Write(Board inputBoard, List<MultipartFile> images) throws IllegalStateException, IOException {
		
		int result = mapper.board3Write(inputBoard);
		
		// result = 0 or 1
		
		if(result == 0) return 0;
		
		// 삽입된 게시글 번호 저장 - mapper3.xml selectKey 태그에 keyProperty="boardNo"
		int boardNo = inputBoard.getBoardNo();
		
		// 업로드 이미지 List
		List<BoardImg> uploadList = new ArrayList<>();
		
		for(int i=0; i < images.size(); i++) {
			
			if( !images.get(i).isEmpty()) {
				
				String originalName = images.get(i).getOriginalFilename(); // 원본명
				
				String rename = Utility.fileRename(originalName); // 변경명
				
				
				BoardImg img = BoardImg.builder()
								.boardNo(boardNo)
								.boardImgPath(webPath)
								.boardImgOriginalName(originalName)
								.boardImgRename(rename)
								.boardImgOrder(i)
								.uploadFile(images.get(i))
								.build();
				
				uploadList.add(img);
			}
			
			
		}
		
		
		// 이미지 업로드 안한 경우
		if(uploadList.isEmpty()) {
			return boardNo;
		}
		
		// 이미지 업로드 삽입
		result = mapper.insertUploadList3(uploadList);
		
		// 다중 INSERT  성공 확인 (uploadList에 저장된 값이 모두 정상 삽입 되었나)
		if(result == uploadList.size()) {
			
			// 서버에 파일 저장
			for(BoardImg img : uploadList) {
				
				img.getUploadFile()
					.transferTo(new File(folderPath + img.getBoardImgRename()));
				
			}
			
		} else {
			// 부분적으로 삽입 실패 -> 전체 서비스 실패로판단
			// -> 이전에 삽입된 내용 모두 rollback
			
			// -> rollback 하는 방법
			// == RuntimeException 강제 발생 (@Transactional)
			throw new BoardInsertException("이미지가 정상 삽입되지 않음");
			
		}
		
		return boardNo;
		
	}


	// [3] 팁과 노하우 - 좋아요
	@Override
	public int boardLike3(Map<String, Integer> map) {
		
		int result = 0;
		
		// 좋아요 이미 1 -> delete
		if(map.get("likeCheck") == 1) {
			
			result = mapper.deleteBoardLike3(map);
		
		// 좋아요 0인 상태 -> insert
		} else {
			
			result = mapper.insertBoardLike3(map);
		}
		
		if(result > 0) {
			return mapper.selectLikeCount3(map.get("boardNo"));
		}
		
		return -1;
	}

	
	// [3] 팁과 노하우 - 게시글 수정
	@Override
	public int board3Update(Board inputBoard, List<MultipartFile> images, String deleteOrder) throws IllegalStateException, IOException {
		
		// 게시글 제목/내용 수정
		int result = mapper.board3Update(inputBoard);
		
		if(result == 0) {
			return 0;
		}
		
		// ---------------- 이미지 -----------------
		if(deleteOrder != null && !deleteOrder.equals("")) {
			
			Map<String, Object> map = new HashMap<>();
			map.put("deleteOrder", deleteOrder);
			map.put("boardNo", inputBoard.getBoardNo());
			
			result = mapper.deleteImage3(map);
			
			// 삭제 실패한 경우(부분 실패 포함) -> 롤백
			if(result == 0) {
				throw new ImageDeleteException();
			}
		}
		
		// 이미지 파일 존재 시
		List<BoardImg> uploadList = new ArrayList<>();
		
		for(int i=0; i < images.size(); i++) {
			
			if( !images.get(i).isEmpty()) {
				
				String originalName = images.get(i).getOriginalFilename(); // 원본명
				
				String rename = Utility.fileRename(originalName); // 변경명
				
				
				BoardImg img = BoardImg.builder()
								.boardNo(inputBoard.getBoardNo())
								.boardImgPath(webPath)
								.boardImgOriginalName(originalName)
								.boardImgRename(rename)
								.boardImgOrder(i)
								.uploadFile(images.get(i))
								.build();
				
				uploadList.add(img);
				
				
				result = mapper.updateImage3(img);
				
				
				// 수정 실패 시 기존 이미지가 없었던 것
				if(result == 0) {
					result = mapper.insertImage3(img);
				}
				
			}
			
			if(result == 0) {
				
				throw new ImageUpdateException();
			}
		}
		
		if(uploadList.isEmpty()) {
			return result;
		}
		
		// 수정, 새 이미지 파일을 서버에 저장
		for(BoardImg img : uploadList) {
			img.getUploadFile().transferTo(new File(folderPath + img.getBoardImgRename()));
		}
		
		return result;
		
	}

	// [3] 팁과 노하우 - 게시글 삭제
	@Override
	public int board3Delete(int boardNo) {
		
		
		return mapper.board3Delete(boardNo);
	}
	
	
	// ---------------------------------------------

	// [3] 팁과 노하우 - 목록 댓글 조회
	@Override
	public List<Comment> commentSelect3(int boardNo) {
		// 
		return mapper.commentSelect3(boardNo);
	}

	// [3] 팁과 노하우 - 댓글/답글 등록
	@Override
	public int commentInsert3(Comment comment) {
		return mapper.commentInsert3(comment);
	}

	
	// [3] 팁과 노하우 - 댓글 수정
	@Override
	public int commentUpdate3(Comment comment) {
		return mapper.commentUpdate3(comment);
	}

	// [3] 팁과 노하우 - 댓글 삭제
	@Override
	public int commentDelete3(Comment comment) {
		return mapper.commentDelete3(comment);
	}

}

